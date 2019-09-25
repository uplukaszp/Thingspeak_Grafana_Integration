package pl.uplukaszp.grafana.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelFeed;
import pl.uplukaszp.grafana.domain.thingspeak.Channel;

@Repository
public class ChannelRepository {

	@Value("${thingspeak.apiKey}")
	private String apiKey;

	public List<ChannelDescription> getChannelDescriptions() {
		RestTemplate temp = new RestTemplate();
		ResponseEntity<List<ChannelDescription>> response = temp.exchange(
				"https://api.thingspeak.com/channels.json?api_key=" + apiKey, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ChannelDescription>>() {
				});
		return response.getBody();
	}

	public Channel getChannelFeed(String id, String readKey) {
		RestTemplate temp = new RestTemplate();

		String url = "https://api.thingspeak.com/channels/" + id + "/feeds.json?results=0";
		ChannelFeed feed = temp.getForObject(url, ChannelFeed.class);
		return feed.getChannel();
	}
}
