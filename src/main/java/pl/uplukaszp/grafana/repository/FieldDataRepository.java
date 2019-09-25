package pl.uplukaszp.grafana.repository;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import pl.uplukaszp.grafana.domain.thingspeak.FieldData;

@Repository
public class FieldDataRepository {

	@Value("${thingspeak.apiKey}")
	private String apiKey;

	public FieldData getFieldData(String channelId, String field, String start, String end,
			Map<String, String> params) {

		RestTemplate temp = new RestTemplate();
		String url = "https://api.thingspeak.com/channels/" + channelId + "/fields/" + field + ".json?start=" + start
				+ "&end=" + end;
		if (params != null && (!params.isEmpty())) {
			for (Entry<String, String> entry : params.entrySet()) {
				url += "&" + entry.getKey() + "=" + entry.getValue();
			}
		}
		System.out.println(url);
		FieldData feed = temp.getForObject(url, FieldData.class);
		return feed;
	}

}
