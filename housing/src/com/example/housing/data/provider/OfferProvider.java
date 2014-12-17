package com.example.housing.data.provider;

import com.example.housing.data.model.Offer;

// TODO: Auto-generated Javadoc
/**
 * The Class OfferProvider.
 */
public class OfferProvider extends BaseProvider<Offer>{

	/* (non-Javadoc)
	 * @see com.example.housing.data.provider.BaseProvider#getEntityClass()
	 */
	@Override
	protected Class<Offer> getEntityClass() {
		return Offer.class;
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the offer
	 */
	public Offer findById(Integer id) {
		return (Offer) super.find(id);
	}
	
}