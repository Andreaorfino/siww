package com.progetto.CustomService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.progetto.Enity.CustomUserDetails;
import com.progetto.Enity.Responsabile;
import com.progetto.Enity.User;
import com.progetto.Repository.AllievoRepository;
import com.progetto.Repository.ResponsabileRepository;



@Service
public class CustomService implements UserDetailsService{

	 @Autowired
	 private AllievoRepository allievoRepository;
	 @Autowired
	 private ResponsabileRepository dipendeteRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Optional<User> optionalUsers = dipendeteRepository.findByUsername(username);
	        if(!optionalUsers.isPresent()) {
	        	optionalUsers = allievoRepository.findByUsername(username);
	        }
	

	        optionalUsers
	                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	        return optionalUsers
	                .map(CustomUserDetails::new).get();
	    }
	
}
