package pl.uplukaszp.grafana.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.uplukaszp.grafana.component.FieldDataParametersCreator;
import pl.uplukaszp.grafana.component.TableConverter;
import pl.uplukaszp.grafana.component.TimeSeriesConverter;
import pl.uplukaszp.grafana.domain.grafana.GraphDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;
import pl.uplukaszp.grafana.dto.FieldDataParameters;
import pl.uplukaszp.grafana.dto.QueryDTO;
import pl.uplukaszp.grafana.dto.TargetDTO;
import pl.uplukaszp.grafana.repository.FieldDataRepository;

@Service
@AllArgsConstructor
public class QueryService {

	private FieldDataRepository fieldDataRepo;
	private ReadKeyService readKeyService;
	private TimeSeriesConverter timeSeriesConverter;
	private TableConverter tableConverter;
	FieldDataParametersCreator creator;

	public List<GraphDataResponse> getResponse(QueryDTO query) {
		List<GraphDataResponse> response = new ArrayList<>();

		for (TargetDTO targetDTO : query.getTargets()) {
			String type = targetDTO.getType();
			String readKey = readKeyService.getReadKey(targetDTO.getChannelId());
			FieldDataParameters parameters = creator.getFieldDataParameter(query, targetDTO, readKey);
			FieldData fieldData = fieldDataRepo.getFieldData(parameters);

			if (type.equals("table")) {
				response.add(tableConverter.convert(fieldData));
			} else if (type.equals("timeseries")) {
				response.add(timeSeriesConverter.convert(fieldData, targetDTO.getFieldNumber()));
			}else {
				throw new IllegalArgumentException("not supported query type: "+type);
			}
		}
		return response;
	}

}
