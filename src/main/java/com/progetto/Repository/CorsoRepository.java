package com.progetto.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.progetto.Enity.Corso;
import com.progetto.Enity.Centro;

public interface CorsoRepository extends CrudRepository<Corso, Long>{
	Corso findByNome (String nome);
	Corso findByNomeAndCentroAndData(String nome, Centro centro, String data);
	List<Corso> findByCentro_id(Long id);
}
