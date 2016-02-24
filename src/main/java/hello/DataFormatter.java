package main.java.hello;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFormatter {

	private static Map<String,int[]> personConsumtions;
	private static Map<Integer,String> personNames;
	private static List<Consumption> consumptions;
	
	public static List<PersonConsumption> sortOnDaysAndPersons(List<Consumption> consumptions,int days, List<Person> persons){
		/* highchart format : { name: personName, data: [1,1,1,1,1] } */
		personConsumtions = new HashMap<>(); 
		personNames = new HashMap<>();
		
		// Create person-arrays, using name because that is correct highchart format
		for(Person p: persons){
			personConsumtions.put(p.getName(), new int[days]);
			personNames.put(p.getId(), p.getName());
		}
		
		mapNamesAndConsumptionData(consumptions, days);
		
		return putInList(personConsumtions);
	}
	
	public static List<PersonConsumption> putInList(Map<String,int[]> mapping){
		List<PersonConsumption> consumptions = new ArrayList<PersonConsumption>();
		for(String key: mapping.keySet()){
			consumptions.add(new PersonConsumption(key,mapping.get(key)));
		}
		return consumptions;
	}
	
	public static void mapNamesAndConsumptionData(List<Consumption> consumptions,int days){
		LocalDate today =  LocalDate.now();
		for(Consumption c : consumptions){
			int daysBack = Period.between(c.getTimestamp().toLocalDateTime().toLocalDate(), today).getDays();
//			System.out.println("dayindex:"+daysBack);
			int dayIndex = days - daysBack -1;
			String personName = personNames.get(c.getPerson().getId());
			personConsumtions.get(personName)[dayIndex] += 1;
		}
	}
	
	
	
}
