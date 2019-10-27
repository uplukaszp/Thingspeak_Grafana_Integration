package pl.uplukaszp.grafana.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.uplukaszp.grafana.domain.grafana.DataPoint;
import pl.uplukaszp.grafana.domain.grafana.GraphDataResponse;
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
			if (type.equals("table")) {
				throw new UnsupportedOperationException("table query type not supported");
			} else if (type.equals("timeseries")) {
				TimeSeriesGraphDataResponse timeSeriesGraphDataResponse = new TimeSeriesGraphDataResponse();
				String[] split = targetDTO.getTarget().split(",");
				System.out.println(query.getFrom());
				String from = convertDate(query.getFrom());
				String to = convertDate(query.getTo());
				String channelId = split[0];
				String readKey = channelRepo.getReadKey(channelId);
				String fieldNumber = split[1];
				FieldData fieldData = fieldDataRepo.getFieldData(channelId, fieldNumber, from, to, targetDTO.getData(),
						readKey);
				timeSeriesGraphDataResponse.setTarget(fieldData.getChannel().getName() + "-"
						+ fieldData.getChannel().getField(Integer.valueOf(fieldNumber)));
				List<Feed> feeds = fieldData.getFeeds();
				for (int i = 0; i < feeds.size(); i++) {
					if (feeds.get(i).getValue() != null) {
						DataPoint dataPoint = new DataPoint();
						dataPoint.setTimestamp(feeds.get(i).getCreatedAt());
						dataPoint.setMetric(feeds.get(i).getValue());
						timeSeriesGraphDataResponse.addDataPoint(dataPoint);
					}
				}

				response.add(timeSeriesGraphDataResponse);
			}
		}
		return response;
	}

	private String convertDate(String date) {
		return date.replace("T", " ").replace("Z", "");
	}
}
