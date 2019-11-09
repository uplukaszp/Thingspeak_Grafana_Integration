package pl.uplukaszp.grafana.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.uplukaszp.grafana.component.TableConverter;
import pl.uplukaszp.grafana.component.TimeSeriesConverter;
import pl.uplukaszp.grafana.domain.grafana.GraphDataResponse;
import pl.uplukaszp.grafana.domain.thingspeak.FieldData;
import pl.uplukaszp.grafana.dto.QueryDTO;
import pl.uplukaszp.grafana.dto.TargetDTO;
import pl.uplukaszp.grafana.repository.ChannelRepository;
import pl.uplukaszp.grafana.repository.FieldDataRepository;

@Service
public class QueryService {

	@Autowired
	FieldDataRepository fieldDataRepo;

	@Autowired
	ChannelRepository channelRepo;

	@Autowired
	ReadKeyService readKeyService;

	@Autowired
	TimeSeriesConverter timeSeriesConverter;

	@Autowired
	TableConverter tableConverter;

	public List<GraphDataResponse> getResponse(QueryDTO query) {
		List<GraphDataResponse> response = new ArrayList<>();

		for (TargetDTO targetDTO : query.getTargets()) {
			String type = targetDTO.getType();
			String from = convertDate(query.getFrom());
			String to = convertDate(query.getTo());
			String readKey = readKeyService.getReadKey(targetDTO.getChannelId());
			FieldData fieldData = fieldDataRepo.getFieldData(targetDTO.getChannelId(), targetDTO.getFieldNumber(), from,
					to, targetDTO.getData(), readKey);

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

}
