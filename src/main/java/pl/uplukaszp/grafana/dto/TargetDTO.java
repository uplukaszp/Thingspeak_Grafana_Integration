package pl.uplukaszp.grafana.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TargetDTO {
	private String target;
	private String refId;
	private String type;
	private Map<String, String> data;

	public String getFieldNumber() {
		String[] split = this.getTarget().split(",");
		return split[1];
	}

	public String getChannelId() {
		String[] split = this.getTarget().split(",");
		return split[0];
	}
}
