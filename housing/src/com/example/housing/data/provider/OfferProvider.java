package com.example.housing.data.provider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class OfferProvider.
 */
public class OfferProvider extends BaseProvider<Offer> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.housing.data.provider.BaseProvider#getEntityClass()
	 */
	@Override
	protected Class<Offer> getEntityClass() {
		return Offer.class;
	}

	/**
	 * Adds the offer.
	 *
	 * @param newOffer the new offer
	 */
	public void addOffer(Offer newOffer) {
		if (!super.save(newOffer)) {

			System.out.println("Neues Angebot konnte nicht in die Datenbank gespeichert werden!");

		}

	}

	/**
	 * Alter offer.
	 *
	 * @param offer the offer
	 * @return true, if successful
	 */
	public boolean alterOffer(Offer offer) {
		
		if(!em.isOpen()) {
			
			em = getEmf().createEntityManager();
		
		}
		try {
			Offer persistedOffer = this.findById(offer.getIdOffer());
			em.detach(persistedOffer);
			em.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		return super.update(offer); // true bei Erfolg, false bei Fehler

	}

	/**
	 * Removes the offer.
	 *
	 * @param offer the offer
	 * @return true, if successful
	 */
	public boolean removeOffer(Offer offer) {

		List<Request> requests = offer.getRequests();
		RequestProvider reqProv = new RequestProvider();
		List<Favorit> favorits = offer.getFavorits();
		FavoritProvider favProv = new FavoritProvider();
		List<Photo> photos = offer.getPhotos();
		PhotoProvider photoProv = new PhotoProvider();
		boolean success = true;
		
		for (Request r : requests) {
			
			success = reqProv.removeRequest(r);
			
		}
		for (Favorit f : favorits) {
			
			success = favProv.removeFavorit(f);
			
		}
		for (Photo p : photos) {

			success = photoProv.removePhoto(p);

		}

		if(success) {
			return super.delete(offer.getIdOffer()); //true bei Erfolg, false bei Fehler
		} else {
			return false;
		}

	}

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id
	 * @return the offer
	 */
	public Offer findById(Integer id) {
		return (Offer) super.find(id);
	}

	/**
	 * Own offers.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<Offer> ownOffers(User user) {
		StringBuffer owns = new StringBuffer();
		int offer_idUser = user.getIdUser();
		owns.append("SELECT o FROM Offer o WHERE o.offer_idUser = " + offer_idUser);
		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}
		Query filterAbfrage = em.createQuery(owns.toString());
		@SuppressWarnings("unchecked")
		List<Offer> ownOffers = (List<Offer>) filterAbfrage.getResultList();
		return ownOffers;
	}

	/**
	 * Filter.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param minSquareMetre the min square metre
	 * @param maxSquareMetre the max square metre
	 * @param minPrice the min price
	 * @param maxPrice the max price
	 * @param type the type
	 * @param internet the internet
	 * @param furnished the furnished
	 * @param kitchen the kitchen
	 * @param smoker the smoker
	 * @param pets the pets
	 * @param city the city
	 * @return the list
	 */
	public List<Offer> filter(Date startDate, Date endDate, float minSquareMetre, float maxSquareMetre, float minPrice,
			float maxPrice, int type, boolean internet, boolean furnished, boolean kitchen, boolean smoker,
			boolean pets, String city) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer filter = new StringBuffer();
		filter.append("SELECT o FROM Offer o WHERE o.inactive = false AND ");
		if (startDate != null && !startDate.equals(new Date(0))) {
			filter.append("o.startDate >= '" + sdf.format(startDate) + "' AND "); // Formatierung?
		}
		if (endDate != null && !endDate.equals(new Date(0))) {
			filter.append("o.endDate <= '" + sdf.format(endDate) + "' AND ");
		}
		if (minSquareMetre != 0.0) {
			filter.append("o.squareMetre >= " + minSquareMetre + " AND ");
		}
		if (maxSquareMetre != 0.0) {
			filter.append("o.squareMetre <= " + maxSquareMetre + " AND ");
		}
		if (minPrice != 0.0) {
			filter.append("o.price >= " + minPrice + " AND ");
		}
		if (maxPrice != 0.0) {
			filter.append("o.price <= " + maxPrice + " AND ");
		}
		if (type != 0) {
			if (type == 4)
				filter.append("o.type = 1 OR o.type = 2 AND ");
			else if (type == 5)
				filter.append("o.type = 2 OR o.type = 3 AND ");
			else if (type == 6)
				filter.append("o.type = 1 OR o.type = 3 AND ");
			else if (type == 7) {
			} else
				filter.append("o.type = " + type + " AND ");
		}
		if (internet) {
			filter.append("o.internet = " + internet + " AND ");
		}
		if (furnished) {
			filter.append("o.furnished = " + furnished + " AND ");
		}
		if (kitchen) {
			filter.append("o.kitchen = " + kitchen + " AND ");
		}
		if (smoker) {
			filter.append("o.smoker = " + smoker + " AND ");
		}
		if (pets) {
			filter.append("o.pets = " + pets + " AND ");
		}
		if (!city.equals("")) {
			filter.append("o.city LIKE '" + city + "%'");
		}

		if (filter.lastIndexOf("AND ") == filter.length() - 4) {
			filter.delete(filter.length() - 4, filter.length());
		} else if (filter.lastIndexOf("WHERE ") == filter.length() - 7) {
			filter.delete(filter.length() - 7, filter.length() - 1);
		}
		filter.append(")");

		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}

		Query filterAbfrage = em.createQuery(filter.toString());
		@SuppressWarnings("unchecked")
		List<Offer> filterErgebnis = (List<Offer>) filterAbfrage.getResultList();
		return filterErgebnis;
	}
	
	/**
	 * Gets the latest offers.
	 *
	 * @return the latest offers
	 */
	public List<Offer> getLatestOffers() {
		
		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}
		
		Query latestAbfrage = em.createQuery("SELECT o FROM Offer o WHERE o.inactive = false ORDER BY o.offerTime DESC");
		@SuppressWarnings("unchecked")
		List<Offer> allOffers = (List<Offer>) latestAbfrage.getResultList();
		
		List<Offer> latestFive = new ArrayList<Offer>();
		for(int i = 0; i < 5; i++) {
			try {
				latestFive.add(allOffers.get(i));
			} catch (Exception e) { //Abfangen von NullPointer (wenn weniger als fünf Angebote in der Datenbank sind)
			}
		}
		
		return (List<Offer>) latestFive;
		
	}

}
