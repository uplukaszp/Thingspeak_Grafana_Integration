package pl.uplukaszp.grafana.component;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import pl.uplukaszp.grafana.dto.FieldDataParameters;
import pl.uplukaszp.grafana.dto.QueryDTO;
import pl.uplukaszp.grafana.dto.TargetDTO;

@Component
public class FieldDataParametersCreator {

	public FieldDataParameters getFieldDataParameter(QueryDTO query, TargetDTO targetDTO, String readKey) {
		String from = convertDate(query.getFrom());
		String to = convertDate(query.getTo());
		Map<String, String> params = targetDTO.getData();
		String paramsString = convertToString(params);
		
		FieldDataParameters fieldDataParameters=new FieldDataParameters();
		fieldDataParameters.setChannelId(targetDTO.getChannelId());
		fieldDataParameters.setStart(from);
		fieldDataParameters.setEnd(to);
		fieldDataParameters.setField(targetDTO.getFieldNumber());
		fieldDataParameters.setParams(paramsString);
		fieldDataParameters.setReadKey(readKey);
		return fieldDataParameters;
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
