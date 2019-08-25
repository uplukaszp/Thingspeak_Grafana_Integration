package pl.uplukaszp.grafana.domain.thingspeak;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelDescription {

	private String id;
	private String description;
	
	@JsonProperty("api_keys")
	private List<ApiKey> apiKeys;
}
