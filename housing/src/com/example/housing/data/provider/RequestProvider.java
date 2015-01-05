package com.example.housing.data.provider;

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

	public boolean requestExists(User user, Offer offer) {
		// TODO Auto-generated method stub
		return false;
	}

}
