package main.java.beans;

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
	
	@Override
	public boolean equals(Object o){
		if(o.getClass() != Person.class) return false;
		return ((Person)o).getName().equals(this.name);
	}
	
}

