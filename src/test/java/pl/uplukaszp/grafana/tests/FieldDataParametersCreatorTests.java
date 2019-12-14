package pl.uplukaszp.grafana.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import pl.uplukaszp.grafana.component.FieldDataParametersCreator;
import pl.uplukaszp.grafana.dto.FieldDataParameters;
import pl.uplukaszp.grafana.dto.QueryDTO;
import pl.uplukaszp.grafana.dto.TargetDTO;

public class FieldDataParametersCreatorTests {

	private static final String TEST_DATA_VALUE = "TestDataValue";
	private static final String TEST_DATA_KEY = "TestDataKey";
	private static final String TEST_REF_ID = "TestRefId";
	private static final String TEST_FIELD_NUMBER = "TestFieldNumber";
	private static final String TEST_TYPE = "TestType";
	private static final String TEST_DATE = "2000-01-01T00:00:00.000Z";
	private static final String EXPECTED_DATE = "2000-01-01 00:00:00.000";

	private static final String TEST_READ_KEY = "TestReadKey";
	private static final String TEST_CHANNEL_ID = "TestChannelId";

	FieldDataParametersCreator creator = new FieldDataParametersCreator();

	@Test
	public void testChannelId() {
		// given
		QueryDTO query = getQuery();
		TargetDTO targetDTO = query.getTargets().get(0);
		// when
		FieldDataParameters fieldDataParameter = creator.getFieldDataParameter(query, targetDTO, TEST_READ_KEY);
		// then
		assertEquals(TEST_CHANNEL_ID, fieldDataParameter.getChannelId());
	}

	@Test
	public void testParams() {
		// given
		QueryDTO query = getQuery();
		TargetDTO targetDTO = query.getTargets().get(0);
		// when
		FieldDataParameters fieldDataParameter = creator.getFieldDataParameter(query, targetDTO, TEST_READ_KEY);
		// then
		assertEquals("&" + TEST_DATA_KEY + "=" + TEST_DATA_VALUE, fieldDataParameter.getParams());
	}
	@Test
	public void testEmptyParams() {
		// given
		QueryDTO query = getQuery();
		TargetDTO targetDTO = query.getTargets().get(0);
		targetDTO.setData(new HashMap<>());
		// when
		FieldDataParameters fieldDataParameter = creator.getFieldDataParameter(query, targetDTO, TEST_READ_KEY);
		// then
		assertEquals("", fieldDataParameter.getParams());
	}
	@Test
	public void testNullParams() {
		// given
		QueryDTO query = getQuery();
		TargetDTO targetDTO = query.getTargets().get(0);
		targetDTO.setData(null);
		// when
		FieldDataParameters fieldDataParameter = creator.getFieldDataParameter(query, targetDTO, TEST_READ_KEY);
		// then
		assertEquals("", fieldDataParameter.getParams());
	}

	@Test
	public void testReadKey() {
		// given
		QueryDTO query = getQuery();
		TargetDTO targetDTO = query.getTargets().get(0);
		// when
		FieldDataParameters fieldDataParameter = creator.getFieldDataParameter(query, targetDTO, TEST_READ_KEY);
		// then
		assertEquals(TEST_READ_KEY, fieldDataParameter.getReadKey());
	}

	@Test
	public void testStartDate() {
		// given
		QueryDTO query = getQuery();
		TargetDTO targetDTO = query.getTargets().get(0);
		// when
		FieldDataParameters fieldDataParameter = creator.getFieldDataParameter(query, targetDTO, TEST_READ_KEY);
		// then
		assertEquals(EXPECTED_DATE, fieldDataParameter.getStart());
	}

	@Test
	public void testEndDate() {
		// given
		QueryDTO query = getQuery();
		TargetDTO targetDTO = query.getTargets().get(0);
		// when
		FieldDataParameters fieldDataParameter = creator.getFieldDataParameter(query, targetDTO, TEST_READ_KEY);
		// then
		assertEquals(EXPECTED_DATE, fieldDataParameter.getEnd());
	}

	@Test
	public void testField() {
		// given
		QueryDTO query = getQuery();
		TargetDTO targetDTO = query.getTargets().get(0);
		// when
		FieldDataParameters fieldDataParameter = creator.getFieldDataParameter(query, targetDTO, TEST_READ_KEY);
		// then
		assertEquals(TEST_FIELD_NUMBER, fieldDataParameter.getField());
	}

	private QueryDTO getQuery() {
		QueryDTO query = new QueryDTO();
		query.setFrom(TEST_DATE);
		query.setTo(TEST_DATE);
		List<TargetDTO> targets = new ArrayList<>();
		targets.add(getTarget());
		query.setTargets(targets);
		return query;
	}

	private TargetDTO getTarget() {
		TargetDTO target = new TargetDTO();
		target.setType(TEST_TYPE);
		target.setTarget(TEST_CHANNEL_ID + "," + TEST_FIELD_NUMBER);
		target.setRefId(TEST_REF_ID);
		target.setData(getData());
		return target;
	}
	private Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();
		data.put(TEST_DATA_KEY, TEST_DATA_VALUE);
		return data;
	}
}
