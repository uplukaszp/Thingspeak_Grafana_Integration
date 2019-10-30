package pl.uplukaszp.grafana.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.uplukaszp.grafana.domain.grafana.ColumType;
import pl.uplukaszp.grafana.domain.grafana.Column;
import pl.uplukaszp.grafana.domain.grafana.DataPoint;
import pl.uplukaszp.grafana.domain.grafana.GraphDataResponse;
import pl.uplukaszp.grafana.domain.grafana.TableDataResponse;
import pl.uplukaszp.grafana.domain.grafana.TimeSeriesGraphDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.Feed;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;
import pl.uplukaszp.grafana.dto.QueryDTO;
import pl.uplukaszp.grafana.dto.TargetDTO;
import pl.uplukaszp.grafana.repository.ChannelRepository;
import pl.uplukaszp.grafana.repository.FieldDataRepository;

@Service
public class QueryService {

	@Autowired
	FieldDataRepository fieldDataRepo;

	@Autowired
	ChannelRepository channelRepo;

	public List<GraphDataResponse> getResponse(QueryDTO query) {
		List<GraphDataResponse> response = new ArrayList<>();

		for (TargetDTO targetDTO : query.getTargets()) {
			String type = targetDTO.getType();
			String[] split = targetDTO.getTarget().split(",");
			String channelId = split[0];
			String fieldNumber = split[1];
			String from = convertDate(query.getFrom());
			String to = convertDate(query.getTo());
			String readKey = channelRepo.getReadKey(channelId);
			FieldData fieldData = fieldDataRepo.getFieldData(channelId, fieldNumber, from, to, targetDTO.getData(),
					readKey);
			List<Feed> feeds = fieldData.getFeeds();
			if (type.equals("table")) {
				TableDataResponse tableDataResponse = new TableDataResponse();
				List<Column> columns = new ArrayList<>();
				tableDataResponse.setColumns(columns);
				columns.add(createColumn("Date", ColumType.time));
				columns.add(createColumn("Value", ColumType.number));
				addRowsFromFeeds(tableDataResponse, feeds);
				response.add(tableDataResponse);
			} else if (type.equals("timeseries")) {
				TimeSeriesGraphDataResponse timeSeriesGraphDataResponse = new TimeSeriesGraphDataResponse();
				timeSeriesGraphDataResponse.setTarget(getTargetString(fieldNumber, fieldData));
				addDataPointsFromFeeds(timeSeriesGraphDataResponse, feeds);
				response.add(timeSeriesGraphDataResponse);
			}
		}
		return response;
	}

	private void addRowsFromFeeds(TableDataResponse tableDataResponse, List<Feed> feeds) {
		List<List<Object>> rows = new ArrayList<>();
		for (Feed feed : feeds) {
			List<Object> row = new ArrayList<>(2);
			row.add(feed.getCreatedAt());
			row.add(feed.getValue());
			rows.add(row);
		}
		tableDataResponse.setRows(rows);
	}

	private Column createColumn(String text, ColumType type) {
		Column column = new Column();
		column.setText(text);
		column.setType(type);
		return column;
	}

	private String getTargetString(String fieldNumber, FieldData fieldData) {
		return fieldData.getChannel().getName() + "-" + fieldData.getChannel().getField(Integer.valueOf(fieldNumber));
	}

	private String convertDate(String date) {
		return date.replace("T", " ").replace("Z", "");
	}

	private void addDataPointsFromFeeds(TimeSeriesGraphDataResponse timeSeriesGraphDataResponse, List<Feed> feeds) {
		for (int i = 0; i < feeds.size(); i++) {
			if (feeds.get(i).getValue() != null) {
				DataPoint dataPoint = new DataPoint();
				dataPoint.setTimestamp(feeds.get(i).getCreatedAt());
				dataPoint.setMetric(feeds.get(i).getValue());
				timeSeriesGraphDataResponse.addDataPoint(dataPoint);
			}
		}
	}
}
