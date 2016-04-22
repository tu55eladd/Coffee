package main.java.coffee;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import main.java.beans.Person;

public class PersonManager {

	private static SessionFactory factory;
	
	public static void init(){
		factory = SessionFactoryHolder.getSessionFactory();
	}
	
	public static Integer addPerson(Person person){
		if(personInDatabase(person)) return null;
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
	
	public static boolean personInDatabase(Person person){
		if(listPersons().contains(person)){
			return true;
		}
		return false;
	}
}
