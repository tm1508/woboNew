package com.example.housing.data.provider;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;

public class OfferProvider extends BaseProvider<Offer> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<Offer> getEntityClass() {
		return Offer.class;
	}

	public void addOffer(Offer newOffer) {
		if (!super.save(newOffer)) {
			System.out.println("Neues Angebot konnte nicht in die Datenbank gespeichert werden!");
		}
	}

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

	public boolean removeOffer(Offer offer) {
		List<Request> requests = offer.getRequests();
		List<Favorit> favorits = offer.getFavorits();
		List<Photo> photos = offer.getPhotos();
		boolean success = true;
		
		try {
		
			for (Request r : requests) {
				success = new RequestProvider().removeRequest(r);
			}
		
			for (Favorit f : favorits) {
				success = new FavoritProvider().removeFavorit(f);
			}
		
			for (Photo p : photos) {
				success = new PhotoProvider().removePhoto(p);
			}
		} catch (NullPointerException npe) { //falls keine Requests etc vorhanden
		}

		if(success) {
			return super.delete(offer.getIdOffer()); //true bei Erfolg, false bei Fehler
		} else {
			return false;
		}

	}

	public Offer findById(Integer id) {
		return (Offer) super.find(id);
	}

	public List<Offer> findByUser(User user) {
		if (!em.isOpen()) {
			em = getEmf().createEntityManager();
		}
		
		Query q = em.createQuery("SELECT o FROM Offer o WHERE o.offer_idUser =:user AND o.title NOT LIKE ' '");
		q.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<Offer> offers = (List<Offer>) q.getResultList();
		
		return offers;
	}

	public List<Offer> filter(Date startDate, Date endDate, float minSquareMetre, float maxSquareMetre, float minPrice,
			float maxPrice, int type, boolean internet, boolean furnished, boolean kitchen, boolean smoker,
			boolean pets, String city, int accessLevel) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer filter = new StringBuffer();
		if(accessLevel == 2) { //für Admin deaktivierte Angebote miteinbeziehen
			filter.append("SELECT o FROM Offer o WHERE ");
		} else {
			filter.append("SELECT o FROM Offer o WHERE o.inactive = false AND ");
		}
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
	
	public List<Offer> getAllOffers(int accessLevel) {
		
		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}
		
		String queryString = "";
		if(accessLevel == 2) { //für Admin auch deaktivierte Angebote anzeigen
			queryString = "SELECT o FROM Offer o ORDER BY o.offerTime DESC";	
		} else {	
			queryString = "SELECT o FROM Offer o WHERE o.inactive = false ORDER BY o.offerTime DESC";
		}
		
		Query allAbfrage = em.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Offer> allOffers = (List<Offer>) allAbfrage.getResultList();
		return (List<Offer>) allOffers;
		
	}
	
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

	public List<Offer> findFailedOffersByUser(User user) {
		
		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}
		
		Query q = em.createQuery("SELECT o FROM Offer o WHERE o.offer_idUser =:user AND o.title LIKE ' '");
		q.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<Offer> failedOffers = (List<Offer>) q.getResultList();
		
		return failedOffers;
		
	}
	
	//filtert Ergebnisliste nach Abstand übergebenem Punkt
	public List<Offer> filterMaps(List<Offer> filterErgebnis, double radius, double lat, double lon){
		Iterator<Offer> it = filterErgebnis.iterator();
		while(it.hasNext()){
			Offer o = it.next();
			double distance;
			if(o.getLatitude()!=null){
				distance = getDistance(o.getLatitude().doubleValue(),lat,  o.getLongitude().doubleValue(), lon);
				if(distance > radius){
					it.remove();
				}
			}else{
				it.remove();
			}
			

		}
		return filterErgebnis;
	}
	
	
	//berechnet die Distanz zwischen zwei Punkten
	public static double getDistance(double lat1, double lat2, double lon1, double lon2){
		double dx = 71.5 * (lon1 - lon2);
		double dy = 111.3 * (lat1 - lat2);
		
		double distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}
}
