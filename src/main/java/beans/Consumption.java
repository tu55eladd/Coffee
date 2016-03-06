package main.java.beans;

//import org.hibernate.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Consumption {

	int id;
	Person person;
	Timestamp timestamp;

	public Consumption(){}
	public Consumption(Person person){
		this.person = person;
		this.timestamp = Timestamp.valueOf(LocalDateTime.now());
	}
	public Consumption(int id, Person person ){
		this.id = id;
		this.person = person;
		this.timestamp = Timestamp.valueOf(LocalDateTime.now());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}
