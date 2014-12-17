package com.example.housing.data.model;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Photo.
 */
@Entity
@Table(name="photo")
public class Photo {
	
	/** The id photo. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idPhoto;
	
	/** The picture. */
	@Lob
	private byte[] picture;
	
	/** The photo_id offer. */
	@ManyToOne
	@JoinColumn(name="photo_idOffer")
	private Offer photo_idOffer;
	
	/**
	 * Gets the id photo.
	 *
	 * @return the id photo
	 */
	public int getIdPhoto() {
		return idPhoto;
	}
	
	/**
	 * Sets the id photo.
	 *
	 * @param idPhoto the new id photo
	 */
	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}
	
	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public byte[] getPhoto() {
		return picture;
	}
	
	/**
	 * Sets the photo.
	 *
	 * @param photo the new photo
	 */
	public void setPhoto(byte[] photo) {
		this.picture = photo;
	}
	
	/**
	 * Gets the photo_id offer.
	 *
	 * @return the photo_id offer
	 */
	public Offer getPhoto_idOffer() {
		return photo_idOffer;
	}
	
	/**
	 * Sets the photo_id offer.
	 *
	 * @param photo_idOffer the new photo_id offer
	 */
	public void setPhoto_idOffer(Offer photo_idOffer) {
		this.photo_idOffer = photo_idOffer;
	}
	
}
