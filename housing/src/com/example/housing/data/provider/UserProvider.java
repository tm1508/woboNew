package com.example.housing.data.provider;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.example.housing.HousingUI;
import com.example.housing.data.model.User;
import com.vaadin.ui.UI;

public class UserProvider {
	
	@PersistenceUnit(name="wobo")
	EntityManager em;
	
	
	public UserProvider() {
		HousingUI hUI = (HousingUI) UI.getCurrent();
		em = Persistence.createEntityManagerFactory("wobo").createEntityManager();
	}
	
	public User doQueryOneResult(Long search){
		Query q = em.createQuery("SELECT u FROM User u"/* WHERE u.idUser =:search"*/);
		//q.setParameter("search", search);
		return (User) q.getResultList().get(0);
		//return (User) q.getSingleResult();
	}
}
