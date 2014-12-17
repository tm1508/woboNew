package com.example.housing.data.provider;

import java.util.List;

import javax.persistence.Query;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.User;

public class FavoritProvider extends BaseProvider<Favorit>{

	@Override
	protected Class<Favorit> getEntityClass() {
		return Favorit.class;
	}
	
	public Favorit findById(Integer id) {
		return (Favorit) super.find(id);
	}
	
	public List<Favorit> findByUser(User u) {
		Query q = em.createQuery("select f from Favorit f where f.favorit_idUser =:u");
		q.setParameter("u", u.getIdUser());
		return (List<Favorit>) q.getResultList();
	}
	
}
