package main.java.hello;

public class PersonConsumption {

	String name;
	int[] data;
	
	public PersonConsumption(){};
	public PersonConsumption(String name, int[] data){
		this.name = name;
		this.data = data;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
	
	
	
}
