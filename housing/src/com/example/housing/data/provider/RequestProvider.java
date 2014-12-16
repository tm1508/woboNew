package com.example.housing.data.provider;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;

public class RequestProvider extends BaseProvider<Request> {

	@Override
	protected Class<Request> getEntityClass() {
		return Request.class;
	}
	
	public Request findById(Integer id) {
		return (Request) super.find(id);
	}

}
