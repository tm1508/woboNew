package com.example.housing.data.provider;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.example.housing.HousingUI;
import com.example.housing.data.model.User;
import com.vaadin.ui.UI;

// TODO: Auto-generated Javadoc
/**
 * The Class UserProvider.
 */
public class UserProvider extends BaseProvider<User>{
	
	/* (non-Javadoc)
	 * @see com.example.housing.data.provider.BaseProvider#getEntityClass()
	 */
	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
	
	/**
	 * Do query one result.
	 *
	 * @param search the search
	 * @return the user
	 */
	public User doQueryOneResult(int search){
		/*Query q = em.createQuery("SELECT u FROM User u WHERE u.idUser =:search");
		q.setParameter("search", search);
		return (User) q.getResultList().get(0);
		//return (User) q.getSingleResult();*/
		return em.find(User.class, search);
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the user
	 */
	public User findById(Integer id) {
		return (User) super.find(id);
	}
	
}
