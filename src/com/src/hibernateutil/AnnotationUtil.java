package com.src.hibernateutil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class AnnotationUtil {

	public static Session giveSession()
	{
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		return factory.openSession();	
		
	}
}
