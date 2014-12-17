package com.example.housing.data.provider;

import com.example.housing.data.model.Offer;

public class OfferProvider extends BaseProvider<Offer>{

	@Override
	protected Class<Offer> getEntityClass() {
		return Offer.class;
	}
	
	public Offer findById(Integer id) {
		return (Offer) super.find(id);
	}
	
}