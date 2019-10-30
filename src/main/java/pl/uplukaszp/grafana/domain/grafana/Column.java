package pl.uplukaszp.grafana.domain.grafana;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Column {
	String text;
	ColumType type;
}
