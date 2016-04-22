package main.java.coffee;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryHolder {
	
	private static SessionFactory factory;
	
	private static void init(){
		try{
	        Configuration configuration = new Configuration();
	        configuration.configure();
	        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	                configuration.getProperties()). build();
	        factory = configuration.buildSessionFactory(serviceRegistry);
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	}
	
	public static SessionFactory getSessionFactory(){
		if(factory == null){
			init();
		}
		return factory;
	}

}
