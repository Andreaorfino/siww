package com.progetto.Services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Corso;
import com.progetto.Enity.Centro;
import com.progetto.Repository.CentroRepository;

@Service
public class CentroServices {
	@Autowired
	private  CentroRepository centroRepository;
	
	public Collection <Centro> RestituisciTutti (){
		return ((Collection<Centro>) centroRepository.findAll());
	}
	
	public boolean AddElem (String nome, String mail, String citta, int telefono, int max_allievi) {
		try {
			centroRepository.save(new Centro(nome, mail, citta, telefono, max_allievi));
		}catch (Exception e) {
			System.out.println("Error in CentroServices: "+e);
			return false;
		}
		return true;
	}

	public Centro getCentro(String string,String citta) {
		return centroRepository.findByNomeAndCitta(string,citta);
	}
	
	public void AddElem(Centro c) {
		centroRepository.save(c);
	}

	public void setcorso(Corso a, Centro centro) {
		Centro x= centroRepository.findById(centro.getId()).get();
		x.getcorso().add(a);
		centroRepository.save(x);	
	}
	
	public void save(Centro centro) {
		centroRepository.save(centro);
		
	}

	public Optional<Centro> findById(Long id) {
		return centroRepository.findById(id);
	}
	
	
	public void setAllievo(Allievo a, Centro centro) {
		Centro x= centroRepository.findById(centro.getId()).get();
		x.getAllievi().add(a);
		centroRepository.save(x);
	}

	public boolean esisteCentro(String nome, String citta) {
		return centroRepository.findByNomeAndCitta(nome, citta)!=null; 
	}

	public  Iterable<Centro> getCentri() {
		return centroRepository.findAll();
	}

	public List<Centro> findAll() {
		return (List<Centro>) centroRepository.findAll();
	}


	


}
