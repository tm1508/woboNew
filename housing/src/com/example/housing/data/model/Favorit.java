package com.example.housing.data.model;

import javax.persistence.*;

@Entity
@Table(name="favorit")
public class Favorit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idFavorit;
	@ManyToOne
	@JoinColumn(name="favorit_idUser")
	private User favorit_idUser;
	@ManyToOne
	@JoinColumn(name="favorit_idOffer")
	private Offer favorit_idOffer;
	
	public int getIdFavorit() {
		return idFavorit;
	}
	public void setIdFavorit(int idFavorit) {
		this.idFavorit = idFavorit;
	}
	public User getFavorit_idUser() {
		return favorit_idUser;
	}
	public void setFavorit_idUser(User favorit_idUser) {
		this.favorit_idUser = favorit_idUser;
	}
	public Offer getFavorit_idOffer() {
		return favorit_idOffer;
	}
	public void setFavorit_idOffer(Offer favorit_idOffer) {
		this.favorit_idOffer = favorit_idOffer;
	}

}
