package com.example.housing.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Offer.
 */
@Entity
@Table(name="Offer")
public class Offer implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The id offer. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idOffer;
	
	/** The title. */
	private String title;
	
	/** The street. */
	private String street;
	
	/** The zip. */
	private String zip;
	
	/** The city. */
	private String city;
	
	/** The start date. */
	@Temporal(value=TemporalType.DATE)
	private Date startDate;
	
	/** The end date. */
	@Temporal(value=TemporalType.DATE)
	private Date endDate;
	
	/** The square metre. */
	private float squareMetre;
	
	/** The price. */
	private float price;
	
	/** The type. */
	private int type;
	
	/** The number of roommate. */
	private int numberOfRoommate;
	
	/** The internet. */
	private boolean internet;
	
	/** The furnished. */
	private boolean furnished;
	
	/** The kitchen. */
	private boolean kitchen;
	
	/** The smoker. */
	private boolean smoker;
	
	/** The pets. */
	private boolean pets;
	
	/** The gender. */
	private int gender;
	
	/** The text. */
	@Lob
	private String text;
	
	/** The bond. */
	private float bond;
	
	/** The inactive. */
	private boolean inactive;
	
	/** The offer_id user. */
	@ManyToOne
	@JoinColumn(name="offer_idUser")
	private User offer_idUser;
	
	/** The offer time. */
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date offerTime;
	
	/** The latitude. */
	private BigDecimal latitude;
	
	/** The longitude. */
	private BigDecimal longitude;
	
	/** The photos. */
	@OneToMany(mappedBy="photo_idOffer", fetch=FetchType.LAZY)
	private List<Photo> photos;
	
	/** The favorits. */
	@OneToMany(mappedBy="favorit_idOffer")
	private List<Favorit> favorits;
	
	@OneToMany(mappedBy="request_idOffer")
	private List<Request> requests;
	
	/**
	 * Gets the id offer.
	 *
	 * @return the id offer
	 */
	public Integer getIdOffer() {
		return idOffer;
	}
	
	/**
	 * Sets the id offer.
	 *
	 * @param idOffer the new id offer
	 */
	public void setIdOffer(Integer idOffer) {
		this.idOffer = idOffer;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * Gets the zip.
	 *
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	
	/**
	 * Sets the zip.
	 *
	 * @param zip the new zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Gets the square metre.
	 *
	 * @return the square metre
	 */
	public float getSquareMetre() {
		return squareMetre;
	}
	
	/**
	 * Sets the square metre.
	 *
	 * @param squareMetre the new square metre
	 */
	public void setSquareMetre(float squareMetre) {
		this.squareMetre = squareMetre;
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	/**
	 * Checks if is shared.
	 *
	 * @return true, if is shared
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Sets the shared.
	 *
	 * @param type the new type
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * Gets the number of roommate.
	 *
	 * @return the number of roommate
	 */
	public int getNumberOfRoommate() {
		return numberOfRoommate;
	}
	
	/**
	 * Sets the number of roommate.
	 *
	 * @param numberOfRoommate the new number of roommate
	 */
	public void setNumberOfRoommate(int numberOfRoommate) {
		this.numberOfRoommate = numberOfRoommate;
	}
	
	/**
	 * Checks if is internet.
	 *
	 * @return true, if is internet
	 */
	public boolean isInternet() {
		return internet;
	}
	
	/**
	 * Sets the internet.
	 *
	 * @param internet the new internet
	 */
	public void setInternet(boolean internet) {
		this.internet = internet;
	}
	
	/**
	 * Checks if is furnished.
	 *
	 * @return true, if is furnished
	 */
	public boolean isFurnished() {
		return furnished;
	}
	
	/**
	 * Sets the furnished.
	 *
	 * @param furnished the new furnished
	 */
	public void setFurnished(boolean furnished) {
		this.furnished = furnished;
	}
	
	/**
	 * Checks if is kitchen.
	 *
	 * @return true, if is kitchen
	 */
	public boolean isKitchen() {
		return kitchen;
	}
	
	/**
	 * Sets the kitchen.
	 *
	 * @param kitchen the new kitchen
	 */
	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}
	
	/**
	 * Checks if is smoker.
	 *
	 * @return true, if is smoker
	 */
	public boolean isSmoker() {
		return smoker;
	}
	
	/**
	 * Sets the smoker.
	 *
	 * @param smoker the new smoker
	 */
	public void setSmoker(boolean smoker) {
		this.smoker = smoker;
	}
	
	/**
	 * Checks if is pets.
	 *
	 * @return true, if is pets
	 */
	public boolean isPets() {
		return pets;
	}
	
	/**
	 * Sets the pets.
	 *
	 * @param pets the new pets
	 */
	public void setPets(boolean pets) {
		this.pets = pets;
	}
	
	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}
	
	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Gets the bond.
	 *
	 * @return the bond
	 */
	public float getBond() {
		return bond;
	}
	
	/**
	 * Sets the bond.
	 *
	 * @param bond the new bond
	 */
	public void setBond(float bond) {
		this.bond = bond;
	}
	
	/**
	 * Checks if is inactive.
	 *
	 * @return true, if is inactive
	 */
	public boolean isInactive() {
		return inactive;
	}
	
	/**
	 * Sets the inactive.
	 *
	 * @param inactive the new inactive
	 */
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	
	/**
	 * Gets the offer_id user.
	 *
	 * @return the offer_id user
	 */
	public User getOffer_idUser() {
		return offer_idUser;
	}
	
	/**
	 * Sets the offer_id user.
	 *
	 * @param offer_idUser the new offer_id user
	 */
	public void setOffer_idUser(User offer_idUser) {
		this.offer_idUser = offer_idUser;
	}
	
	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
	
	/**
	 * Sets the photos.
	 *
	 * @param photos the new photos
	 */
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
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
	 * Gets the offer time.
	 *
	 * @return the offer time
	 */
	public Date getOfferTime() {
		return offerTime;
	}

	/**
	 * Sets the offer time.
	 *
	 * @param offerTime the new offer time
	 */
	public void setOfferTime(Date offerTime) {
		this.offerTime = offerTime;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public BigDecimal getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

}
