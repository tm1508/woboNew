package com.example.housing.data.model;

import javax.persistence.*;

@Entity
@Table(name="request")
public class Request {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idRequest;
	@ManyToOne
	@JoinColumn(name="request_idUser")
	private User request_idUser;
	@ManyToOne
	@JoinColumn(name="request_idOffer")
	private Offer request_idOffer;
	
	public int getIdRequest() {
		return idRequest;
	}
	public void setIdRequest(int idRequest) {
		this.idRequest = idRequest;
	}
	public User getRequest_idUser() {
		return request_idUser;
	}
	public void setRequest_idUser(User request_idUser) {
		this.request_idUser = request_idUser;
	}
	public Offer getRequest_idOffer() {
		return request_idOffer;
	}
	public void setRequest_idOffer(Offer request_idOffer) {
		this.request_idOffer = request_idOffer;
	}

}
