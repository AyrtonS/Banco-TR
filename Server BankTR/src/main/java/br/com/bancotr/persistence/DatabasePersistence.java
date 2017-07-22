package br.com.bancotr.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabasePersistence{
	private static EntityManagerFactory emf;
	
	public static void init(){
		
		emf = Persistence.createEntityManagerFactory("banktr");
		
	}
	public static EntityManagerFactory getEntityManagerFactory(){
		return emf;
	}
	public static void close(){
		emf.close();
	}
	
}
