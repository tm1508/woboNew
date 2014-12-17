package com.example.housing.data.provider;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;

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
	 * Find by id.
	 *
	 * @param id the id
	 * @return the request
	 */
	public Request findById(Integer id) {
		return (Request) super.find(id);
	}

}
