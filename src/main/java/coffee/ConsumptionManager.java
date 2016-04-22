package main.java.coffee;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import main.java.beans.Consumption;
import main.java.beans.Person;
import main.java.beans.PersonConsumption;
import main.java.util.DataFormatter;

public class ConsumptionManager {

	private static SessionFactory factory;
	
	public ConsumptionManager(){
		init();
	}
	
	public static void init(){
		factory = SessionFactoryHolder.getSessionFactory();
	}
	
	
	public static Integer addConsumption(Person person){
		Session session = factory.openSession();
		Transaction tx = null;
		Integer consumptionID = null;
		try {
			tx = session.beginTransaction();
			Consumption consumption = new Consumption(person);
			consumptionID = (Integer) session.save(consumption);
			tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close();
		}
		return consumptionID;
	}
	
	public static List<Consumption> listConsumptions(){
		Session session = factory.openSession();
	    Transaction tx = null;
	    List<Consumption> consumptions = null;
	      try{
	         tx = session.beginTransaction();
	         consumptions = session.createQuery("FROM Consumption").list(); 
//	         System.out.println("BEFORE ACCSESSING PERSON INSIDE CONSUMPTION");
	         for(Consumption c : consumptions ){
//	        	 c.getPerson();
	        	 System.out.println(c.getPerson().getName());
	         }
//	         System.out.println("AFTER ACCSESSING PERSON INSIDE CONSUMPTION");
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return consumptions;
	}
	
	public static void deleteConsumption(){
		
	}

	public static List<Consumption> getConsumptionsForDaysBackward(int days){
		Session session = factory.openSession();
		Transaction tx = null;
		Criteria ct = null;
		List<Consumption> consumptions = null;;
		try{
			tx = session.beginTransaction();
			ct = session.createCriteria(Consumption.class);
			
			Timestamp dayBeforeStartingDay = Timestamp.valueOf(LocalDateTime.now().minusDays(days-1));
			ct.add(Restrictions.gt("timestamp",dayBeforeStartingDay));
			
			consumptions = ct.list();
			
			tx.commit();
		}catch(HibernateException e){
			if(tx!=null) tx.rollback();
		}finally{
			session.close();
		}
		if(consumptions == null) return null;
		return consumptions;
	}
	
	public static List<PersonConsumption> getDayViewData(int days){
		if(days<1 || days>14){return new ArrayList<PersonConsumption>();};
		List<Consumption> consumptions = getConsumptionsForDaysBackward(days);
		List<Person> persons = PersonManager.listPersons();
		
		return DataFormatter.sortOnDaysAndPersons(consumptions,days,persons);
	}
	
}
