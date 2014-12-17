package com.example.housing.data.provider;

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
		StringBuffer filter = new StringBuffer();
		filter.append("SELECT o FROM Offer o WHERE ");
		if(!startDate.equals(new Date(0))){
			filter.append("o.startDate <=:"+startDate + " AND "); //Formatierung?
		}
		if(!endDate.equals(new Date(0))){
			filter.append("o.endDate >=:"+endDate + " AND ");
		}
		if(minSquareMetre != 0.0){
			filter.append("o.squareMetre >=:" + minSquareMetre + " AND ");
		}
		if(maxSquareMetre != 0.0){
			filter.append("o.squareMetre <=;" + maxSquareMetre + " AND ");
		}
		if(minPrice != 0.0){
			filter.append("o.price >=:" + minPrice + " AND ");
		}
		//TODO:
		//NOCH NICHT FERTIG!!!
		if(filter.lastIndexOf("AND ") == filter.length()-5){
			filter.delete(filter.length()-5, filter.length()-1);
		} else if(filter.lastIndexOf("WHERE ") == filter.length()-7){
			filter.delete(filter.length()-7, filter.length()-1);
		}
		filter.append(");");
		
		Query filterAbfrage = em.createQuery(filter.toString());
		@SuppressWarnings("unchecked")
		List<Offer> filterErgebnis = (List<Offer>) filterAbfrage.getResultList();
		return filterErgebnis;
	}
}