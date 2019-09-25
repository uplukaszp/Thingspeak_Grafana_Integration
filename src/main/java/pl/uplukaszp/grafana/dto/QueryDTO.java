package pl.uplukaszp.grafana.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class QueryDTO {
	private String panelId;
	@JsonIgnore
	private String from;
	@JsonIgnore
	private String to;
	private Integer intervalMs;
	private Integer maxDataPoints;
	private List<TargetDTO> targets;
	
	@JsonProperty("range")
	public void setRange(Map<String,Object> range) {
		from=(String) range.get("from");
		to=(String) range.get("to");
	}
}
