package pl.uplukaszp.grafana.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDataParameters {
	private String channelId;
	private String params;
	private String readKey;
	private String start;
	private String end;
	private String field;
}
