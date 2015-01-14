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
	
	public void addFavorit(Favorit newFavorit) {
		
		if (!super.save(newFavorit)) {

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
	/*  funktioniert nicht
	 *  public List<Favorit> findByUser(User u) {
		Query q = em.createQuery("select f from Favorit f where f.favorit_idUser =:u");
		q.setParameter("u", u.getIdUser());
		return (List<Favorit>) q.getResultList();
	} */
	
	public List<Favorit> findFav(User user) {
		StringBuffer favs = new StringBuffer();
		int favorit_idUser = user.getIdUser();
		favs.append("SELECT f FROM Favorit f WHERE f.favorit_idUser = " + favorit_idUser);
		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}
		Query q = em.createQuery(favs.toString());
		@SuppressWarnings("unchecked")
		List<Favorit> ownFavs = (List<Favorit>) q.getResultList();
		return ownFavs;
	}

	public boolean removeFavorit(Favorit f) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
