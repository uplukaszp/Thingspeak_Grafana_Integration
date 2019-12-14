package pl.uplukaszp.grafana.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import pl.uplukaszp.grafana.component.TimeSeriesConverter;
import pl.uplukaszp.grafana.domain.grafana.TimeSeriesGraphDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.Feed;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;

public class TimeSeriesConverterTargetTests {
	TimeSeriesConverter converter = new TimeSeriesConverter();

	@Test // metoda convert robi za duzo ??
	public void testCreatingTarget() {
		// given
		FieldData fieldData = mock(FieldData.class, Mockito.RETURNS_DEEP_STUBS);
		String channelName = "TestChannel";
		String fieldName = "TestField";
		when(fieldData.getChannel().getName()).thenReturn(channelName);
		when(fieldData.getChannel().getField(Integer.valueOf(0))).thenReturn(fieldName);
		// when
		TimeSeriesGraphDataResponse response = converter.convert(fieldData, "0");
		// then
		assertEquals("Bad target name", channelName + "-" + fieldName, response.getTarget());
	}

	@Test
	public void testCreatingNoDataPoints() {
		// given
		FieldData fieldData = mock(FieldData.class, Mockito.RETURNS_DEEP_STUBS);
		// when
		TimeSeriesGraphDataResponse response = converter.convert(fieldData, "0");
		// then
		assertEquals("Wrong size of data points list", 0, response.getDataPoints().size());
	}

	@Test
	public void testCreatingDataPointsFromEmptyDataPoint() {
		// given
		FieldData fieldData = mock(FieldData.class, Mockito.RETURNS_DEEP_STUBS);
		when(fieldData.getFeeds()).thenReturn(feedsWithEmptyValue());
		// when
		TimeSeriesGraphDataResponse response = converter.convert(fieldData, "0");
		// then
		assertEquals("Wrong size of data points list", 0, response.getDataPoints().size());
	}

	@Test
	public void testCreatingDataPoints() {
		// given
		FieldData fieldData = mock(FieldData.class, Mockito.RETURNS_DEEP_STUBS);
		when(fieldData.getFeeds()).thenReturn(feedsWithValue());
		// when
		TimeSeriesGraphDataResponse response = converter.convert(fieldData, "0");
		// then
		assertEquals("Wrong size of data points list", 1, response.getDataPoints().size());
	}

	@Test
	public void testDataPointsValues() {
		// given
		FieldData fieldData = mock(FieldData.class, Mockito.RETURNS_DEEP_STUBS);
		float expectedMetric = 0.0f;
		Date exptectedDate = Date.from(Instant.EPOCH);
		when(fieldData.getFeeds()).thenReturn(feedsWithValue());
		// when
		TimeSeriesGraphDataResponse response = converter.convert(fieldData, "0");
		// then
		float resultMetric = (float) response.getDataPoints().get(0).get(0);
		Date resultTimeStamp = new Date((Long) response.getDataPoints().get(0).get(1));

		assertEquals("Wrong metric", expectedMetric, resultMetric, 0.0f);
		assertEquals("Wrong time stamp", exptectedDate, resultTimeStamp);
	}

	private List<Feed> feedsWithEmptyValue() {
		List<Feed> feeds = new ArrayList<Feed>();
		feeds.add(new Feed());
		return feeds;
	}

	private List<Feed> feedsWithValue() {
		List<Feed> feeds = new ArrayList<Feed>();
		Feed feed = new Feed();
		feed.setCreatedAt(Date.from(Instant.EPOCH));
		feed.setValue(0.0f);
		feeds.add(feed);
		return feeds;
	}
}
