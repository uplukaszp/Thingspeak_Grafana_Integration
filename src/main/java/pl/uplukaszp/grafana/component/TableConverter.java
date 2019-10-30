package pl.uplukaszp.grafana.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.uplukaszp.grafana.domain.grafana.ColumType;
import pl.uplukaszp.grafana.domain.grafana.Column;
import pl.uplukaszp.grafana.domain.grafana.TableDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.Feed;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;

@Component
public class TableConverter {

	public TableDataResponse convert(FieldData fieldData) {
		TableDataResponse tableDataResponse = new TableDataResponse();
		List<Feed> feeds = fieldData.getFeeds();
		List<Column> columns = new ArrayList<>();
		List<List<Object>> rows = getRows(feeds);

		columns.add(createColumn("Date", ColumType.time));
		columns.add(createColumn("Value", ColumType.number));

		tableDataResponse.setColumns(columns);
		tableDataResponse.setRows(rows);
		return tableDataResponse;
	}

	private List<List<Object>> getRows(List<Feed> feeds) {
		List<List<Object>> rows = new ArrayList<>();
		for (Feed feed : feeds) {
			List<Object> row = new ArrayList<>(2);
			row.add(feed.getCreatedAt());
			row.add(feed.getValue());
			rows.add(row);
		}
		return rows;
	}

	private Column createColumn(String text, ColumType type) {
		Column column = new Column();
		column.setText(text);
		column.setType(type);
		return column;
	}
}
