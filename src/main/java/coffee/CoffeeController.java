package main.java.coffee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import main.java.beans.Consumption;
import main.java.beans.Person;
import main.java.beans.PersonConsumption;


@RestController
public class CoffeeController {

	public CoffeeController(){
		ConsumptionManager.init();
	}
	
	@RequestMapping(value = "/consumption/add", method = RequestMethod.POST) 
	public void addConsumption(@RequestBody Person person){
		if(person.getName()==null || person.getName()==""){
			return;
		}
		ConsumptionManager.addConsumption(person);
	}
	
	@RequestMapping(value = "/person/add", method = RequestMethod.GET) 
	public void getConsumptions(@RequestParam(value = "name")String name){
		if(name==null || name==""){
			return;
		}
		ConsumptionManager.addPerson(new Person(name));
		System.out.println("Added persons");
	}
	
	@RequestMapping(value="/person/all", method = RequestMethod.GET)
	public List<Person> allPersons(){
		List<Person> persons = ConsumptionManager.listPersons();
		return persons;
	}
	
	@RequestMapping(value = "/consumtion/all", method = RequestMethod.GET) 
	public List<Consumption> allConsumptions(){
		List<Consumption> consumptions = ConsumptionManager.listConsumptions();
		return consumptions;
	}
	
	@RequestMapping(value = "/consumtion/daysback", method = RequestMethod.GET)
	public List<PersonConsumption> consumptionsForDaysBack(@RequestParam(value="days") int days){
		return ConsumptionManager.getDayViewData(days);
	}
	
	
	
	
	
	
	public void doTransAction(){
		
	}
}
