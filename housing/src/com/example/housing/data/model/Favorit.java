package com.example.housing.data.model;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Favorit.
 */
@Entity
@Table(name="favorit")
public class Favorit {
	
	/** The id favorit. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idFavorit;
	
	/** The favorit_id user. */
	@ManyToOne
	@JoinColumn(name="favorit_idUser")
	private User favorit_idUser;
	
	/** The favorit_id offer. */
	@ManyToOne
	@JoinColumn(name="favorit_idOffer")
	private Offer favorit_idOffer;
	
	/**
	 * Gets the id favorit.
	 *
	 * @return the id favorit
	 */
	public int getIdFavorit() {
		return idFavorit;
	}
	
	/**
	 * Sets the id favorit.
	 *
	 * @param idFavorit the new id favorit
	 */
	public void setIdFavorit(int idFavorit) {
		this.idFavorit = idFavorit;
	}
	
	/**
	 * Gets the favorit_id user.
	 *
	 * @return the favorit_id user
	 */
	public User getFavorit_idUser() {
		return favorit_idUser;
	}
	
	/**
	 * Sets the favorit_id user.
	 *
	 * @param favorit_idUser the new favorit_id user
	 */
	public void setFavorit_idUser(User favorit_idUser) {
		this.favorit_idUser = favorit_idUser;
	}
	
	/**
	 * Gets the favorit_id offer.
	 *
	 * @return the favorit_id offer
	 */
	public Offer getFavorit_idOffer() {
		return favorit_idOffer;
	}
	
	/**
	 * Sets the favorit_id offer.
	 *
	 * @param favorit_idOffer the new favorit_id offer
	 */
	public void setFavorit_idOffer(Offer favorit_idOffer) {
		this.favorit_idOffer = favorit_idOffer;
	}

}
