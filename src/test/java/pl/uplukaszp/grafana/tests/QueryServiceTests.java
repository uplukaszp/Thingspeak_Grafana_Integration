package pl.uplukaszp.grafana.tests;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import pl.uplukaszp.grafana.component.FieldDataParametersCreator;
import pl.uplukaszp.grafana.component.TableConverter;
import pl.uplukaszp.grafana.component.TimeSeriesConverter;
import pl.uplukaszp.grafana.domain.grafana.GraphDataResponse;
import pl.uplukaszp.grafana.domain.grafana.TableDataResponse;
import pl.uplukaszp.grafana.domain.grafana.TimeSeriesGraphDataResponse;
import pl.uplukaszp.grafana.dto.QueryDTO;
import pl.uplukaszp.grafana.dto.TargetDTO;
import pl.uplukaszp.grafana.repository.FieldDataRepository;
import pl.uplukaszp.grafana.service.QueryService;
import pl.uplukaszp.grafana.service.ReadKeyService;

public class QueryServiceTests {

	@Test
	public void queryTableTypeTest() {
		// given
		FieldDataRepository fieldDataRepo = mock(FieldDataRepository.class);
		ReadKeyService readKeyService = mock(ReadKeyService.class);
		TimeSeriesConverter timeSeriesConverter = mock(TimeSeriesConverter.class);
		TableConverter tableConverter = mock(TableConverter.class);
		FieldDataParametersCreator creator = mock(FieldDataParametersCreator.class);

		QueryDTO query = createTestQuery("table");
		when(tableConverter.convert(any())).thenReturn(new TableDataResponse());
		when(timeSeriesConverter.convert(any(), anyString())).thenReturn(new TimeSeriesGraphDataResponse());
		QueryService service = new QueryService(fieldDataRepo, readKeyService, timeSeriesConverter, tableConverter,
				creator);
		// when
		List<GraphDataResponse> response = service.getResponse(query);
		// then
		assertTrue(response.get(0) instanceof TableDataResponse);
	}

	@Test
	public void queryTimeSeriesTypeTest() {
		// given
		FieldDataRepository fieldDataRepo = mock(FieldDataRepository.class);
		ReadKeyService readKeyService = mock(ReadKeyService.class);
		TimeSeriesConverter timeSeriesConverter = mock(TimeSeriesConverter.class);
		TableConverter tableConverter = mock(TableConverter.class);
		FieldDataParametersCreator creator = mock(FieldDataParametersCreator.class);

		QueryDTO query = createTestQuery("timeseries");
		when(tableConverter.convert(any())).thenReturn(new TableDataResponse());
		when(timeSeriesConverter.convert(any(), anyString())).thenReturn(new TimeSeriesGraphDataResponse());
		QueryService service = new QueryService(fieldDataRepo, readKeyService, timeSeriesConverter, tableConverter,
				creator);
		// when
		List<GraphDataResponse> response = service.getResponse(query);
		// then
		assertTrue(response.get(0) instanceof TimeSeriesGraphDataResponse);
	}

	@Test
	public void queryWrongTypeTest() {
		try {
			// given
			FieldDataRepository fieldDataRepo = mock(FieldDataRepository.class);
			ReadKeyService readKeyService = mock(ReadKeyService.class);
			TimeSeriesConverter timeSeriesConverter = mock(TimeSeriesConverter.class);
			TableConverter tableConverter = mock(TableConverter.class);
			FieldDataParametersCreator creator = mock(FieldDataParametersCreator.class);

			QueryDTO query = createTestQuery("wrongType");
			when(tableConverter.convert(any())).thenReturn(new TableDataResponse());
			when(timeSeriesConverter.convert(any(), anyString())).thenReturn(new TimeSeriesGraphDataResponse());
			QueryService service = new QueryService(fieldDataRepo, readKeyService, timeSeriesConverter, tableConverter,
					creator);
			// when
			service.getResponse(query);
			// then
			Assert.fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	private QueryDTO createTestQuery(String target) {
		QueryDTO query = new QueryDTO();
		List<TargetDTO> targets = new ArrayList<>();
		targets.add(createTarget(target));
		query.setTargets(targets);
		return query;
	}

	private TargetDTO createTarget(String type) {
		TargetDTO targetDTO = new TargetDTO();
		targetDTO.setType(type);
		targetDTO.setTarget("TestChannelID,TestFieldNumber");
		return targetDTO;
	}
}
