package pl.uplukaszp.grafana.domain.grafana;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDataResponse implements GraphDataResponse {
	List<Column> columns;
	List<List<Object>> rows;
}
