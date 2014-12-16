package com.example.housing.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="offer")
public class Offer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idOffer;
	private String title;
	private String street;
	private String zip;
	private String city;
	@Temporal(value=TemporalType.DATE)
	private Date startDate;
	@Temporal(value=TemporalType.DATE)
	private Date endDate;
	private float squareMetre;
	private float price;
	private boolean isShared;
	private int numberOfRoommate;
	private boolean internet;
	private boolean furnished;
	private boolean kitchen;
	private boolean smoker;
	private boolean pets;
	private boolean isFemale;
	@Lob
	private String text;
	private Float bond;
	private boolean inactive;
	@ManyToOne
	@JoinColumn(name="offer_idUser")
	private User offer_idUser;
	@OneToMany(mappedBy="photo_idOffer")
	private List<Photo> photos;
	@OneToMany(mappedBy="favorit_idOffer")
	private List<Favorit> favorits;
	
	public Integer getIdOffer() {
		return idOffer;
	}
	public void setIdOffer(Integer idOffer) {
		this.idOffer = idOffer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public float getSquareMetre() {
		return squareMetre;
	}
	public void setSquareMetre(float squareMetre) {
		this.squareMetre = squareMetre;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isShared() {
		return isShared;
	}
	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}
	public int getNumberOfRoommate() {
		return numberOfRoommate;
	}
	public void setNumberOfRoommate(int numberOfRoommate) {
		this.numberOfRoommate = numberOfRoommate;
	}
	public boolean isInternet() {
		return internet;
	}
	public void setInternet(boolean internet) {
		this.internet = internet;
	}
	public boolean isFurnished() {
		return furnished;
	}
	public void setFurnished(boolean furnished) {
		this.furnished = furnished;
	}
	public boolean isKitchen() {
		return kitchen;
	}
	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}
	public boolean isSmoker() {
		return smoker;
	}
	public void setSmoker(boolean smoker) {
		this.smoker = smoker;
	}
	public boolean isPets() {
		return pets;
	}
	public void setPets(boolean pets) {
		this.pets = pets;
	}
	public boolean isFemale() {
		return isFemale;
	}
	public void setFemale(boolean isFemale) {
		this.isFemale = isFemale;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public float getBond() {
		return bond;
	}
	public void setBond(float bond) {
		this.bond = bond;
	}
	public boolean isInactive() {
		return inactive;
	}
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	public User getOffer_idUser() {
		return offer_idUser;
	}
	public void setOffer_idUser(User offer_idUser) {
		this.offer_idUser = offer_idUser;
	}
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	public List<Favorit> getFavorits() {
		return favorits;
	}
	public void setFavorits(List<Favorit> favorits) {
		this.favorits = favorits;
	}

}
