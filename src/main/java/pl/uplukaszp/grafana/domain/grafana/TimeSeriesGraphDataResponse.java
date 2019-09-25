package pl.uplukaszp.grafana.domain.grafana;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
public class TimeSeriesGraphDataResponse implements GraphDataResponse {
	private String target;

	@Setter(AccessLevel.PRIVATE)
	List<List<Object>> datapoints=new ArrayList<>();

	public void addDataPoint(DataPoint datapoint) {
		List<Object> rawDataPoint = new ArrayList<>();
		rawDataPoint.add(0, datapoint.getMetric());
		rawDataPoint.add(1, datapoint.getTimestamp().getTime());
		datapoints.add(rawDataPoint);
	}
}
