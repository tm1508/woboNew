package com.example.housing.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
@Entity
@Table(name="User")
public class User implements Serializable{
	
	/** The id user. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idUser;
	
	/** The firstname. */
	private String firstname;
	
	/** The lastname. */
	private String lastname;
	
	/** The email. */
	private String email;
	
	/** The mobile. */
	private String mobile;
	
	/** The password. */
	private String password;
	
	/** The dh mail. */
	private String dhMail;
	
	/** The access level. */
	private int accessLevel;
	
	/** The activated. */
	private boolean activated;
	
	/** The offers. */
	@OneToMany(mappedBy="offer_idUser")
	private List<Offer> offers;
	
	/** The requests. */
	@OneToMany(mappedBy="request_idUser")
	private List<Request> requests;
	
	/** The favorits. */
	@OneToMany(mappedBy="favorit_idUser")
	private List<Favorit> favorits;
	
	/**
	 * Gets the id user.
	 *
	 * @return the id user
	 */
	public Integer getIdUser() {
		return idUser;
	}
	
	/**
	 * Sets the id user.
	 *
	 * @param idUser the new id user
	 */
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	
	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Sets the firstname.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * Sets the lastname.
	 *
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gets the mobile.
	 *
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * Sets the mobile.
	 *
	 * @param mobile the new mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the dh mail.
	 *
	 * @return the dh mail
	 */
	public String getDhMail() {
		return dhMail;
	}
	
	/**
	 * Sets the dh mail.
	 *
	 * @param dhMail the new dh mail
	 */
	public void setDhMail(String dhMail) {
		this.dhMail = dhMail;
	}
	
	/**
	 * Gets the access level.
	 *
	 * @return the access level
	 */
	public int getAccessLevel() {
		return accessLevel;
	}
	
	/**
	 * Sets the access level.
	 *
	 * @param accessLevel the new access level
	 */
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	/**
	 * Gets the offers.
	 *
	 * @return the offers
	 */
	public List<Offer> getOffers() {
		return offers;
	}
	
	/**
	 * Sets the offers.
	 *
	 * @param offers the new offers
	 */
	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
	
	/**
	 * Gets the requests.
	 *
	 * @return the requests
	 */
	public List<Request> getRequests() {
		return requests;
	}
	
	/**
	 * Sets the requests.
	 *
	 * @param requests the new requests
	 */
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	
	/**
	 * Gets the favorits.
	 *
	 * @return the favorits
	 */
	public List<Favorit> getFavorits() {
		return favorits;
	}
	
	/**
	 * Sets the favorits.
	 *
	 * @param favorits the new favorits
	 */
	public void setFavorits(List<Favorit> favorits) {
		this.favorits = favorits;
	}

	/**
	 * Checks if is activated.
	 *
	 * @return true, if is activated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * Sets the activated.
	 *
	 * @param activated the new activated
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
