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
	
	public void addRequest(User user, Offer offer) {
		
		Request newReq = new Request();
		newReq.setRequest_idUser(user);
		newReq.setRequest_idOffer(offer);
		
		if (!super.save(newReq)) {

			System.out.println("Angebot konnte nicht zu den abgesendeten Anfragen hinzugefügt werden.");

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

}
