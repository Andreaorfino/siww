package com.progetto.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progetto.Enity.Responsabile;
import com.progetto.Enity.User;

public interface ResponsabileRepository extends JpaRepository<Responsabile, Integer>{
	Optional<User> findByUsername(String username);
}
