package com.progetto.Enity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Responsabile extends User{
	
	@ManyToOne
	private Centro centro;

	public Responsabile() {
		super();
	}

	public Responsabile(String password, String nome, String cognome,	String username, Centro centro) {
		super(password, nome, cognome, "ADMIN" , username);
		this.centro = centro;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}
	
	

}
