package com.progetto.Enity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Corso {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String data; 
	@NotNull
	private Time ora;
	
	@ManyToOne
	private Centro centro;
	
	@ManyToMany 
	@JoinTable(name = "Attivita_Allievi",
			joinColumns = { @JoinColumn(name = "id_Attivita") },
			inverseJoinColumns = { @JoinColumn(name = "id_Allievo") })
	private List<Allievo> allievi;
	
	public Corso(String nome, String data, Time ora,Centro centro) {

		this.nome = nome;
		this.data = data;
		this.ora = ora;
		this.centro=centro;
		this.allievi= new ArrayList<>();

	}

	public List<Allievo> getAllievi() {
		return allievi;
	}

	public void setAllievi(List<Allievo> allievi) {
		this.allievi = allievi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Time getOra() {
		return ora;
	}

	public void setOra(Time ora) {
		this.ora = ora;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Corso() {

	}

	public void addAllievo(Allievo allievo) {
		this.allievi.add(allievo);
	}

}
