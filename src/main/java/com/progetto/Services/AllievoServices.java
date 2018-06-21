package com.progetto.Services;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Corso;
import com.progetto.Enity.Centro;
import com.progetto.Enity.User;
import com.progetto.Repository.AllievoRepository;

@Service
public class AllievoServices{
	@Autowired
	private  AllievoRepository allievoRepository;
	
	
	public boolean AggiungiAllievo (String nome, String cognome, String luogo_nascita, String data, String mail,Centro centro, String password, String telefono,String username) {
		try {allievoRepository.save(new Allievo(password,nome,cognome,username,mail,telefono,luogo_nascita,data,centro));
	}catch (Exception e) {
		System.out.println("Error in corsoServices: "+e);
		return false;
	}
	return true;
	}
	
	public List<User> getAllievoNome(String string) {
		return Collections.singletonList(allievoRepository.findByNome(string).get());
	}
	
	public Optional<User> getAllievoUsername(String string) {
		return  allievoRepository.findByUsername(string);
	}
	
	public boolean esisteMail(String string){
		return allievoRepository.findByMail(string).isPresent();
	}
	
	public boolean esisteUsername(String username) {
		return allievoRepository.findByUsername(username)!=null;
	}
	
	public boolean esisteAllievoCentro(Long id, Centro centro) {
		return allievoRepository.findById(id).isPresent()&&allievoRepository.findById(id).get().getCentro().equals(centro);
	}
	
		public Allievo getcorso(String name) {
		return (Allievo) allievoRepository.findByUsername(name).get();
	}

	public Collection <Allievo> RestituisciTutti (){
		return ((Collection<Allievo>)allievoRepository.findAll());
	}

	public Centro getCentro(String username) {
		Allievo a = (Allievo) allievoRepository.findByUsername(username).get();
		return a.getCentro();
	}


	public void salva(Allievo allievo) {
		allievoRepository.save(allievo);
	}

		public List<Allievo> getAllievi() {
		return (List<Allievo>) allievoRepository.findAll();
	}
	
}