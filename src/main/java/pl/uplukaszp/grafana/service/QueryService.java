package pl.uplukaszp.grafana.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.uplukaszp.grafana.component.TableConverter;
import pl.uplukaszp.grafana.component.TimeSeriesConverter;
import pl.uplukaszp.grafana.domain.grafana.GraphDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;
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

	public List<GraphDataResponse> getResponse(QueryDTO query) {
		List<GraphDataResponse> response = new ArrayList<>();

		for (TargetDTO targetDTO : query.getTargets()) {
			String type = targetDTO.getType();
			String from = convertDate(query.getFrom());
			String to = convertDate(query.getTo());
			String readKey = readKeyService.getReadKey(targetDTO.getChannelId());
			Map<String, String> params = targetDTO.getData();
			String paramsString = convertToString(params);
			FieldData fieldData = fieldDataRepo.getFieldData(targetDTO.getChannelId(), targetDTO.getFieldNumber(), from,
					to, paramsString, readKey);

			if (type.equals("table")) {
				response.add(tableConverter.convert(fieldData));
			} else if (type.equals("timeseries")) {
				response.add(timeSeriesConverter.convert(fieldData, targetDTO.getFieldNumber()));
			}
		}
		return response;
	}

	private String convertDate(String date) {
		return date.replace("T", " ").replace("Z", "");
	}

	private String convertToString(Map<String, String> params) {
		String paramString = "";
		if (params != null && (!params.isEmpty())) {
			for (Entry<String, String> entry : params.entrySet()) {
				paramString += "&" + entry.getKey() + "=" + entry.getValue();
			}
		}
		return paramString;
	}
}
