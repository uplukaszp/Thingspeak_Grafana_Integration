package pl.uplukaszp.grafana.domain.grafana;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataPoint {

	private float metric;
	private Date timestamp;
}
