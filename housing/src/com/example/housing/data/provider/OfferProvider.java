package com.example.housing.data.provider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;

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

	public void addOffer(Offer newOffer) {
		if (!super.save(newOffer)) {

			System.out.println("Neues Angebot konnte nicht in die Datenbank gespeichert werden!");

		}

	}
	
	public boolean alterOffer(Offer offer) {
		
		return super.update(offer); //true bei Erfolg, false bei Fehler
		
	}
	
	public boolean removeOffer(Offer offer) {
		
		List<Photo> photos = offer.getPhotos();
		PhotoProvider photoProv = new PhotoProvider();
		for(Photo p : photos) {
			
			photoProv.removePhoto(p);
			
		}
		
		return super.delete(offer); //true bei Erfolg, false bei Fehler
		
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

	public List<Offer> filter(Date startDate, Date endDate, float minSquareMetre, float maxSquareMetre, float minPrice,
			float maxPrice, int type, boolean internet, boolean furnished, boolean kitchen, boolean smoker,
			boolean pets, String city) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer filter = new StringBuffer();
		filter.append("SELECT o FROM Offer o WHERE ");
		if (startDate != null && !startDate.equals(new Date(0))) {
			filter.append("o.startDate < " + sdf.format(startDate) + " AND "); // Formatierung?
		}
		if (endDate != null && !endDate.equals(new Date(0))) {
			filter.append("o.endDate > " + sdf.format(endDate) + " AND ");
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
			filter.append("o.city = '" + city + "'");
		}

		if (filter.lastIndexOf("AND ") == filter.length() - 4) {
			filter.delete(filter.length() - 4, filter.length());
		} else if (filter.lastIndexOf("WHERE ") == filter.length() - 7) {
			filter.delete(filter.length() - 7, filter.length() - 1);
		}
		filter.append(")");
		// TODO: createQuery mit Übergabe des filters führt zu einer Exception.

		if (!em.isOpen()) {

			em = getEmf().createEntityManager();

		}

		Query filterAbfrage = em.createQuery(filter.toString());
		@SuppressWarnings("unchecked")
		List<Offer> filterErgebnis = (List<Offer>) filterAbfrage.getResultList();
		return filterErgebnis;
	}

}
