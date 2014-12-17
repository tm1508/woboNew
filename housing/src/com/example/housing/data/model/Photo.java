package com.example.housing.data.model;

import javax.persistence.*;

@Entity
@Table(name="photo")
public class Photo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idPhoto;
	@Lob
	private byte[] picture;
	@ManyToOne
	@JoinColumn(name="photo_idOffer")
	private Offer photo_idOffer;
	
	public int getIdPhoto() {
		return idPhoto;
	}
	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}
	public byte[] getPhoto() {
		return picture;
	}
	public void setPhoto(byte[] photo) {
		this.picture = photo;
	}
	public Offer getPhoto_idOffer() {
		return photo_idOffer;
	}
	public void setPhoto_idOffer(Offer photo_idOffer) {
		this.photo_idOffer = photo_idOffer;
	}
	
}
