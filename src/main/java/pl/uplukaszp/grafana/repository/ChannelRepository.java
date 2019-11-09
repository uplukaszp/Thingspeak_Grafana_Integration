package pl.uplukaszp.grafana.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import pl.uplukaszp.grafana.domain.thingspeak.Channel;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelFeed;

@Repository
public class ChannelRepository {

	@Value("${thingspeak.apiKey}")
	private String apiKey;

	private RestTemplate temp = new RestTemplate();

	@PostConstruct
	public List<ChannelDescription> getChannelDescriptions() {
		ResponseEntity<List<ChannelDescription>> response = temp.exchange(
				"https://api.thingspeak.com/channels.json?api_key=" + apiKey, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ChannelDescription>>() {
				});
		List<ChannelDescription> chanells = response.getBody();
		return chanells;
	}

	public Channel getChannelFeed(String id, String readKey) {
		String url = "https://api.thingspeak.com/channels/" + id + "/feeds.json?results=0" + "&api_key=" + readKey;
		ChannelFeed feed = temp.getForObject(url, ChannelFeed.class);
		return feed.getChannel();
	}

}
