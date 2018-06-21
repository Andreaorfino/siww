package com.progetto.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Responsabile;
import com.progetto.Enity.User;

public interface AllievoRepository extends JpaRepository<Allievo, Long>{
	Optional<User> findByUsername(String username);
	Optional<User> findByNome(String string);
	Optional<User> findByMail(String string);
	}