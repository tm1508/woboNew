package com.example.housing.data.provider;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.example.housing.data.model.Offer;

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
		if(!super.save(newOffer)) {
			
			System.out.println("Datensatz konnte nicht in die Datenbank gespeichert werden!");
			
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

	public List<Offer> filter(Date startDate, Date endDate, float minSquareMetre, float maxSquareMetre, float minPrice,
			float maxPrice, int type, boolean internet, boolean furnished, boolean kitchen, boolean smoker, boolean pets, String city) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		StringBuffer filter = new StringBuffer();
		filter.append("SELECT o FROM Offer o WHERE ");
		if(startDate != null && !startDate.equals(new Date(0))){
			filter.append("o.startDate <= "+df.format(startDate) + " AND "); //Formatierung?
		}
		if(endDate != null && !endDate.equals(new Date(0))){
			filter.append("o.endDate >= "+ df.format(endDate) + " AND ");
		}
		if(minSquareMetre != 0.0){
			filter.append("o.squareMetre >= " + minSquareMetre + " AND ");
		}
		if(maxSquareMetre != 0.0){
			filter.append("o.squareMetre <= " + maxSquareMetre + " AND ");
		}
		if(minPrice != 0.0){
			filter.append("o.price >= " + minPrice + " AND ");
		}
		if(maxPrice != 0.0){
			filter.append("o.price <= "+ maxPrice + " AND ");
		}
		if(type != 0){
			filter.append("o.type = " + type + " AND ");
		}
		if(internet){
			filter.append("o.internet = " + internet + " AND ");
		}
		if(furnished){
			filter.append("o.furnished = " + furnished + " AND ");
		}
		if(kitchen){
			filter.append("o.kitchen = " + kitchen + " AND ");
		}
		if(smoker){
			filter.append("o.smoker = " + smoker + " AND ");
		}
		if(pets){
			filter.append("o.pets = " + pets + " AND ");
		}
		if(!city.equals("")){
			filter.append("o.city = '" + city + "'");
		}

		if(filter.lastIndexOf("AND ") == filter.length()-5){
			filter.delete(filter.length()-5, filter.length()-1);
		} else if(filter.lastIndexOf("WHERE ") == filter.length()-7){
			filter.delete(filter.length()-7, filter.length()-1);
		}	
		filter.append(")");
		//TODO: createQuery mit Übergabe des filters führt zu einer Exception. 
		
		if(!em.isOpen()) {
			
			em = getEmf().createEntityManager();
		
		}

		Query filterAbfrage = em.createQuery(filter.toString());
		@SuppressWarnings("unchecked")
		List<Offer> filterErgebnis = (List<Offer>) filterAbfrage.getResultList();
		return filterErgebnis;
	}

}
