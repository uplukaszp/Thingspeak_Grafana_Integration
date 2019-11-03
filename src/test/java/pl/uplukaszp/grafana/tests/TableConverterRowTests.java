package pl.uplukaszp.grafana.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pl.uplukaszp.grafana.component.TableConverter;
import pl.uplukaszp.grafana.domain.grafana.TableDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.Feed;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;

public class TableConverterRowTests {

	private TableConverter converter = new TableConverter();

	@Test
	public void testCreateNoRows() {
		// given
		FieldData fieldData = mock(FieldData.class);
		// when
		TableDataResponse response = converter.convert(fieldData);
		// then
		assertEquals("Wrong number of rows", 0, response.getRows().size());
	}

	@Test
	public void testCreateOneRow() {
		// given
		FieldData fieldData = mock(FieldData.class);
		when(fieldData.getFeeds()).thenReturn(getOneElementList());
		// when
		TableDataResponse response = converter.convert(fieldData);
		// then
		assertEquals("Wrong number of rows", 1, response.getRows().size());
	}

	private List<Feed> getOneElementList() {
		List<Feed> list = new ArrayList<>();
		list.add(new Feed());
		return list;
	}

	@Test
	public void testCreateManyRows() {
		// given
		FieldData fieldData = mock(FieldData.class);
		when(fieldData.getFeeds()).thenReturn(getTenElementsList());
		// when
		TableDataResponse response = converter.convert(fieldData);
		// then
		assertEquals("Wrong number of rows", 10, response.getRows().size());
	}

	private List<Feed> getTenElementsList() {
		List<Feed> list = new ArrayList<>();
		list.add(new Feed());
		list.add(new Feed());
		list.add(new Feed());
		list.add(new Feed());
		list.add(new Feed());
		list.add(new Feed());
		list.add(new Feed());
		list.add(new Feed());
		list.add(new Feed());
		list.add(new Feed());
		return list;
	}
}
