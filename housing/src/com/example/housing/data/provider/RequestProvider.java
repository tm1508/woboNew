package com.example.housing.data.provider;

import java.util.List;

import javax.persistence.Query;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestProvider.
 */
public class RequestProvider extends BaseProvider<Request> {

	/* (non-Javadoc)
	 * @see com.example.housing.data.provider.BaseProvider#getEntityClass()
	 */
	@Override
	protected Class<Request> getEntityClass() {
		return Request.class;
	}
	
	/**
	 * Adds the request.
	 *
	 * @param newRequest the new request
	 */
	public void addRequest(Request newRequest) {
		
		if (!super.save(newRequest)) {

			System.out.println("Anfrage konnte nicht zu den abgesendeten Anfragen hinzugefügt werden.");

		}

	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the request
	 */
	public Request findById(Integer id) {
		return (Request) super.find(id);
	}

	/**
	 * Request exists.
	 *
	 * @param user the user
	 * @param offer the offer
	 * @return true, if successful
	 */
	public boolean requestExists(User user, Offer offer) {
		
		try {
			
			this.findByUserOffer(user, offer);
			return true; //es existiert bereits eine anfrage zu dieser User-Offer-Kombi
			
		} catch(Exception e) {
			
			return false; //Fehlermeldung -> eine Anfrage zu dieser User-Offer-Kombi existiert noch nicht
		
		}
	
	}
	
	/**
	 * Find by user offer.
	 *
	 * @param user the user
	 * @param offer the offer
	 * @return the request
	 */
	private Request findByUserOffer(User user, Offer offer) {
		
		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}
		
		Query q = em.createQuery("SELECT r FROM REQUEST r WHERE r.request_idUser =:user AND r.request_idOffer =:offer");
		q.setParameter("user", user);
		q.setParameter("offer", offer);
		return (Request) q.getSingleResult();
		
	}

	/**
	 * Find req.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<Request> findReq(User user) {
		StringBuffer reqs = new StringBuffer();
		int request_idUser = user.getIdUser();
		reqs.append("SELECT r FROM Request r WHERE r.request_idUser = " + request_idUser);
		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}
		Query q = em.createQuery(reqs.toString());
		@SuppressWarnings("unchecked")
		List<Request> ownReqs = (List<Request>) q.getResultList();
		return ownReqs;
	}

	/**
	 * Removes the request.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	public boolean removeRequest(Request request) {
		
		return super.delete(request.getIdRequest());
		
	}
	

}
