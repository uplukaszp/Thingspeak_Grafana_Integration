package pl.uplukaszp.grafana.domain.thingspeak;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Feed {

	@JsonProperty("created_at")
	Date createdAt;
	@JsonProperty("entry_id")
	String entryID;

	@JsonProperty("field1")
	@JsonAlias({ "field2", "field3", "field4", "field5", "field6", "field7", "field8", })
	Float value;
}
