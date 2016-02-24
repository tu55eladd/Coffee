package main.java.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person {

	int id;
	String name;
	
	public Person(){}
	public Person(Integer id, String name){
		this.id = id;
		this.name = name;
	}
	public Person(String name){
		this.name = name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
}

