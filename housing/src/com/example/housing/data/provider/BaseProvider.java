package com.example.housing.data.provider;

import javax.persistence.*;

import com.example.housing.data.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseProvider.
 *
 * @param <T> the generic type
 */
public abstract class BaseProvider<T> {

	/** The emf. */
	@PersistenceContext(name="wobo")
	private static EntityManagerFactory emf;
	
	/** The em. */
	EntityManager em;
	
	/**
	 * Instantiates a new base provider.
	 */
	public BaseProvider() {
		
		if(em!=null && em.isOpen()) {
			
			em.close();
		
		}
		
		if(getEmf()==null) {
			
			emf = Persistence.createEntityManagerFactory("wobo");
		
		}
		
		em = emf.createEntityManager();
	}
	
	/**
	 * Gets the emf.
	 *
	 * @return the emf
	 */
	protected EntityManagerFactory getEmf() {
		
		return emf;
		
	}
	
	/**
	 * Gets the entity class.
	 *
	 * @return the entity class
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the t
	 */
	public T find(Integer id) {
		return (T) em.find(getEntityClass(), id);
	}
	
}
