package com.progetto.Enity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Set;

@MappedSuperclass
public class User {
	

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    @NotBlank
    private String password;
    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    @NotBlank
    private String ruolo;
    @NotBlank
    private String username;

    public User() {
    }


	public User( String password, String nome, String cognome, String ruolo, String username) {
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.ruolo = ruolo;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User(User user) {
		this.id = user.getId();
		this.password = user.getPassword();
		this.nome = user.getNome();
		this.cognome = user.getCognome();
		this.ruolo = user.getRuolo();
		this.username = user.getUsername();
	}
}
