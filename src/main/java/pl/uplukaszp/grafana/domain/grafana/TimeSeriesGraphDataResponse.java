package pl.uplukaszp.grafana.domain.grafana;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSeriesGraphDataResponse implements GraphDataResponse {
	private String target;
	@JsonProperty("datapoints")
	List<List<Object>> dataPoints = new ArrayList<>();

	public void addDataPoint(DataPoint dataPoint) {
		List<Object> rawDataPoint = new ArrayList<>();
		rawDataPoint.add(0, dataPoint.getMetric());
		rawDataPoint.add(1, dataPoint.getTimestamp().getTime());
		dataPoints.add(rawDataPoint);
	}

	public void setDataPoints(List<DataPoint> dataPoints) {
		this.dataPoints.clear();
		for (DataPoint dataPoint : dataPoints) {
			addDataPoint(dataPoint);
		}
	}
}
