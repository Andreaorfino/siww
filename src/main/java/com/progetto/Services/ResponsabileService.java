package com.progetto.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.Enity.Responsabile;
import com.progetto.Enity.User;
import com.progetto.Repository.ResponsabileRepository;

@Service
public class ResponsabileService {

	@Autowired
	private ResponsabileRepository responsabileRepository;

	public Optional<User> getresponsabileUsername(String name) {
		return responsabileRepository.findByUsername(name);
	}

	public void save(Responsabile responsabile) {
		responsabileRepository.save(responsabile);
		
	}
	
	
}
