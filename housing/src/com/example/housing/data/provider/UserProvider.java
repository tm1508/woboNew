package com.example.housing.data.provider;

import java.util.List;
import javax.persistence.Query;
import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;

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
	 * Adds the user.
	 *
	 * @param newUser the new user
	 */
	public void addUser(User newUser) {
		if (!super.save(newUser)) {

			System.out.println("Neuer User konnte nicht in die Datenbank gespeichert werden!");

		}

	}
	
	/**
	 * Alter user.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	public boolean alterUser(User user) {
		
		if(!em.isOpen()) {
			
			em = getEmf().createEntityManager();
		
		}
		try {
			User persistedUser = this.findById(user.getIdUser());
			em.detach(persistedUser);
			em.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		
		return super.update(user); //true bei Erfolg, false bei Fehler
		
	}
	
	/**
	 * Removes the user.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	public boolean removeUser(User user) {
		
		List<Offer> offers = user.getOffers();
		List<Favorit> favorits = user.getFavorits();
		List<Request>requests = user.getRequests(); 
		OfferProvider offerProv = new OfferProvider();
		FavoritProvider favoritProv = new FavoritProvider();
		RequestProvider requestProv = new RequestProvider();
		boolean success = true;
		
		try {
			for(Offer o : offers) {
				success = offerProv.removeOffer(o);
			}
			
			for(Favorit f : favorits) {
				success = favoritProv.removeFavorit(f);
			}
			
			for(Request r : requests) {
				success = requestProv.removeRequest(r);
			}
		} catch (NullPointerException npe) { // falls keine Offers etc vorhanden sind
		}
		
		if(success) {
			return super.delete(user.getIdUser()); //true bei Erfolg, false bei Fehler
		} else {
			return false;
		}
		
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
	
	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the user
	 */
	public User findByEmail(String email) {
		Query q = em.createQuery("SELECT u FROM User u WHERE u.email =:email");
		q.setParameter("email", email);
		return (User) q.getSingleResult();
	}
	
	/**
	 * User exists.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	public boolean userExists(String email){
		try{
			this.findByEmail(email);
			return true;//es existiert ein Benutzer mit dieser E-Mail-Adresse
		}catch(Exception e){
			return false;//Fehlermeldung -> ein Nutzer mit dieser E-Mail-Adresse existiert noch nicht
		}
	}
	
}
