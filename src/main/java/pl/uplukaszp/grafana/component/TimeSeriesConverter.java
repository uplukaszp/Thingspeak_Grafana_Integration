package pl.uplukaszp.grafana.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.uplukaszp.grafana.domain.grafana.DataPoint;
import pl.uplukaszp.grafana.domain.grafana.TimeSeriesGraphDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.Feed;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;

@Component
public class TimeSeriesConverter {

	public TimeSeriesGraphDataResponse convert(FieldData fieldData, String fieldNumber) {

		TimeSeriesGraphDataResponse timeSeriesGraphDataResponse = new TimeSeriesGraphDataResponse();
		List<Feed> feeds = fieldData.getFeeds();
		List<DataPoint> dataPoints = getDataPoints(feeds);

		timeSeriesGraphDataResponse.setTarget(getTargetString(fieldNumber, fieldData));
		timeSeriesGraphDataResponse.setDataPoints(dataPoints);

		return timeSeriesGraphDataResponse;
	}

	private String getTargetString(String fieldNumber, FieldData fieldData) {
		String channelName = fieldData.getChannel().getName();
		String fieldName = fieldData.getChannel().getField(Integer.valueOf(fieldNumber));
		return channelName + "-" + fieldName;
	}

	private List<DataPoint> getDataPoints(List<Feed> feeds) {
		List<DataPoint> dataPoints = new ArrayList<DataPoint>();
		for (Feed feed : feeds) {
			if (feed.getValue() != null) {
				DataPoint dataPoint = new DataPoint();
				dataPoint.setTimestamp(feed.getCreatedAt());
				dataPoint.setMetric(feed.getValue());
				dataPoints.add(dataPoint);
			}
		}
		return dataPoints;
	}
}
