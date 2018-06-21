package com.progetto.Repository;

import org.springframework.data.repository.CrudRepository;

import com.progetto.Enity.Centro;

public interface CentroRepository extends CrudRepository<Centro, Long> {
	public Centro findByNome(String string);
	public Centro findByNomeAndCitta(String nome,String citta);
}
