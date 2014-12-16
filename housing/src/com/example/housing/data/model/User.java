package com.example.housing.data.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idUser;
	private String firstname;
	private String lastname;
	private String email;
	private String mobile;
	private String password;
	private String dhMail;
	private Integer accessLevel;
	@OneToMany(mappedBy="offer_idUser")
	private List<Offer> offers;
	@OneToMany(mappedBy="request_idUser")
	private List<Request> requests;
	@OneToMany(mappedBy="favorit_idUser")
	private List<Favorit> favorits;
	
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDhMail() {
		return dhMail;
	}
	public void setDhMail(String dhMail) {
		this.dhMail = dhMail;
	}
	public int getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	public List<Offer> getOffers() {
		return offers;
	}
	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
	public List<Request> getRequests() {
		return requests;
	}
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	public List<Favorit> getFavorits() {
		return favorits;
	}
	public void setFavorits(List<Favorit> favorits) {
		this.favorits = favorits;
	}

}
