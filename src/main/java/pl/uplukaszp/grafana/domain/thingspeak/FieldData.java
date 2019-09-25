package pl.uplukaszp.grafana.domain.thingspeak;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldData {

	Channel channel;
	List<Feed> feeds;
}
