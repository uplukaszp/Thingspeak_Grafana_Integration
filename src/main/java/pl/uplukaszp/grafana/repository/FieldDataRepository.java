package pl.uplukaszp.grafana.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import pl.uplukaszp.grafana.domain.thingspeak.FieldData;
import pl.uplukaszp.grafana.dto.FieldDataParameters;

@Repository
public class FieldDataRepository {

	@Value("${thingspeak.apiKey}")
	private String apiKey;
	private RestTemplate temp = new RestTemplate();

	public FieldData getFieldData(FieldDataParameters parameters) {

		String url = "https://api.thingspeak.com/channels/" + parameters.getChannelId() + "/fields/" + parameters.getField() + ".json?start=" + parameters.getStart()
				+ "&end=" + parameters.getEnd() + "&api_key=" + parameters.getReadKey() + parameters.getParams();
		FieldData feed = temp.getForObject(url, FieldData.class);
		return feed;
	}

	public FieldData getFieldData(String channelId, String field, String start, String end, String params) {

		String url = "https://api.thingspeak.com/channels/" + channelId + "/fields/" + field + ".json?start=" + start
				+ "&end=" + end + params;

		FieldData feed = temp.getForObject(url, FieldData.class);
		return feed;
	}

	public FieldData getFieldData(String channelId, String field, String start, String end) {

		String url = "https://api.thingspeak.com/channels/" + channelId + "/fields/" + field + ".json?start=" + start
				+ "&end=" + end;
		FieldData feed = temp.getForObject(url, FieldData.class);
		return feed;
	}
}
