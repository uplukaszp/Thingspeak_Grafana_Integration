package pl.uplukaszp.grafana.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import pl.uplukaszp.grafana.domain.thingspeak.FieldData;

@Repository
public class FieldDataRepository {

	@Value("${thingspeak.apiKey}")
	private String apiKey;
	private RestTemplate temp = new RestTemplate();

	public FieldData getFieldData(String channelId, String field, String start, String end, String params,
			String readKey) {

		String url = "https://api.thingspeak.com/channels/" + channelId + "/fields/" + field + ".json?start=" + start
				+ "&end=" + end + "&api_key=" + readKey + params;
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
