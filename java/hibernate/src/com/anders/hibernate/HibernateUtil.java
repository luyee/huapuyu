package com.anders.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public final class HibernateUtil {
	// private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

	private static SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();

	private HibernateUtil() {
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() {
		return sessionFactory.openSession();
	}
}
