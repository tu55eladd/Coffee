package main.java.hello;

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
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class ConsumptionManager {

	private static SessionFactory factory;
	
	public static void init(){
		try{
	        factory = new Configuration().configure().buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
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
	
	public static Integer addPerson(Person person){
		Session session = factory.openSession();
		Transaction tx = null;
		Integer personID = null;
		try {
			tx = session.beginTransaction();
			personID = (Integer) session.save(person);
			tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close();
		}
		return personID;
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
	
	public static List<Person> listPersons(){
		Session session = factory.openSession();
	      Transaction tx = null;
	      List<Person> persons = null;
	      try{
	         tx = session.beginTransaction();
	         persons = (List<Person>) session.createQuery("FROM Person").list();
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return persons;
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
		List<Person> persons = listPersons();
		
		return DataFormatter.sortOnDaysAndPersons(consumptions,days,persons);
	}
	
}
