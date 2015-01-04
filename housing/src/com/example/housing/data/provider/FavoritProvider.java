package com.example.housing.data.provider;

import java.util.List;

import javax.persistence.Query;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class FavoritProvider.
 */
public class FavoritProvider extends BaseProvider<Favorit>{

	/* (non-Javadoc)
	 * @see com.example.housing.data.provider.BaseProvider#getEntityClass()
	 */
	@Override
	protected Class<Favorit> getEntityClass() {
		return Favorit.class;
	}
	
	public void addFavorit(User user, Offer offer) {
		
		Favorit newFav = new Favorit();
		newFav.setFavorit_idUser(user);
		newFav.setFavorit_idOffer(offer);
		
		if (!super.save(newFav)) {

			System.out.println("Angebot konnte nicht zu den Favoriten hinzugefügt werden.");

		}

	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the favorit
	 */
	public Favorit findById(Integer id) {
		return (Favorit) super.find(id);
	}
	
	/**
	 * Find by user.
	 *
	 * @param u the u
	 * @return the list
	 */
	public List<Favorit> findByUser(User u) {
		Query q = em.createQuery("select f from Favorit f where f.favorit_idUser =:u");
		q.setParameter("u", u.getIdUser());
		return (List<Favorit>) q.getResultList();
	}
	
}
