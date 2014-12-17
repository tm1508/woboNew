package com.example.housing.data.model;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Request.
 */
@Entity
@Table(name="request")
public class Request {
	
	/** The id request. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idRequest;
	
	/** The request_id user. */
	@ManyToOne
	@JoinColumn(name="request_idUser")
	private User request_idUser;
	
	/** The request_id offer. */
	@ManyToOne
	@JoinColumn(name="request_idOffer")
	private Offer request_idOffer;
	
	/**
	 * Gets the id request.
	 *
	 * @return the id request
	 */
	public int getIdRequest() {
		return idRequest;
	}
	
	/**
	 * Sets the id request.
	 *
	 * @param idRequest the new id request
	 */
	public void setIdRequest(int idRequest) {
		this.idRequest = idRequest;
	}
	
	/**
	 * Gets the request_id user.
	 *
	 * @return the request_id user
	 */
	public User getRequest_idUser() {
		return request_idUser;
	}
	
	/**
	 * Sets the request_id user.
	 *
	 * @param request_idUser the new request_id user
	 */
	public void setRequest_idUser(User request_idUser) {
		this.request_idUser = request_idUser;
	}
	
	/**
	 * Gets the request_id offer.
	 *
	 * @return the request_id offer
	 */
	public Offer getRequest_idOffer() {
		return request_idOffer;
	}
	
	/**
	 * Sets the request_id offer.
	 *
	 * @param request_idOffer the new request_id offer
	 */
	public void setRequest_idOffer(Offer request_idOffer) {
		this.request_idOffer = request_idOffer;
	}

}
