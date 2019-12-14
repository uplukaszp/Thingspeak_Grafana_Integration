package pl.uplukaszp.grafana.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import pl.uplukaszp.grafana.component.TableConverter;
import pl.uplukaszp.grafana.domain.grafana.ColumType;
import pl.uplukaszp.grafana.domain.grafana.TableDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;

@RunWith(MockitoJUnitRunner.class)
public class TableConverterColumnTests {

	@Mock
	private FieldData fieldData;

	private TableConverter converter = new TableConverter();

	@Test
	public void testCreateTwoColumns() {
		TableDataResponse response = converter.convert(fieldData);
		assertEquals("Wrong number of columns ", 2, response.getColumns().size());
		assertEquals("Wrong column type", ColumType.time, response.getColumns().get(0).getType());
		assertEquals("Wrong column text", "Date", response.getColumns().get(0).getText());
		assertEquals("Wrong column type", ColumType.number, response.getColumns().get(1).getType());
		assertEquals("Wrong column text", "Value", response.getColumns().get(1).getText());
	}

	@Test
	public void testCreateFirtsColumnWithTimeType() {
		TableDataResponse response = converter.convert(fieldData);
		assertEquals("Wrong column type", ColumType.time, response.getColumns().get(0).getType());
	}

	@Test
	public void testCreateFirtsColumnWithTextDate() {
		TableDataResponse response = converter.convert(fieldData);
		assertEquals("Wrong column text", "Date", response.getColumns().get(0).getText());
	}

	@Test
	public void testCreateSecondColumnWithNumberType() {
		TableDataResponse response = converter.convert(fieldData);
		assertEquals("Wrong column type", ColumType.number, response.getColumns().get(1).getType());
	}

	@Test
	public void testCreateSecondColumnWithTextValue() {
		TableDataResponse response = converter.convert(fieldData);
		assertEquals("Wrong column text", "Value", response.getColumns().get(1).getText());
	}

}