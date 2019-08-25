package pl.uplukaszp.grafana.domain.thingspeak;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Channel {
	private Integer id;
	private String name;
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;
	private String field6;
	private String field7;
	private String field8;
	
	public List<Field> getFields(){
		List<Field> fields=new ArrayList<>();
		addField(field1,1,fields);
		addField(field2,2,fields);
		addField(field3,3,fields);
		addField(field4,4,fields);
		addField(field5,5,fields);
		addField(field6,6,fields);
		addField(field7,7,fields);
		addField(field8,8,fields);
		return fields;
	}

	private void addField(String field,Integer number, List<Field> fields) {
		if(field!=null) {
			Field f=new Field();
			f.setName(field);
			f.setNumber(number);
			fields.add(f);
		}
	}
}
