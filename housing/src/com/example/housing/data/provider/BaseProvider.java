package com.example.housing.data.provider;

import javax.persistence.*;

import com.example.housing.data.model.User;

public abstract class BaseProvider<T> {

	@PersistenceContext(name="wobo")
	private static EntityManagerFactory emf;
	EntityManager em;
	
	public BaseProvider() {
		
		if(em!=null && em.isOpen()) {
			
			em.close();
		
		}
		
		if(getEmf()==null) {
			
			emf = Persistence.createEntityManagerFactory("wobo");
		
		}
		
		em = emf.createEntityManager();
	}
	
	protected EntityManagerFactory getEmf() {
		
		return emf;
		
	}
	
	protected abstract Class<T> getEntityClass();

	public T find(Integer id) {
		return (T) em.find(getEntityClass(), id);
	}
	
}
