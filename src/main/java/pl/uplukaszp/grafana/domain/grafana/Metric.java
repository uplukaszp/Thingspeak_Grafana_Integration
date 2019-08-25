package pl.uplukaszp.grafana.domain.grafana;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metric {
	private String text;
	private String value;
}
