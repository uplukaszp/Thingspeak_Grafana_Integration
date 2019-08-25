package pl.uplukaszp.grafana.domain.thingspeak;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiKey {

	@JsonProperty("api_key")
	private String apiKey;
	@JsonProperty("write_flag")
	private Boolean writeFlag;
}
