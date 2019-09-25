package pl.uplukaszp.grafana.dto;

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
	private Object data;
}
