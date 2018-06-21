package com.progetto.Services;

import java.sql.Time;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Corso;
import com.progetto.Enity.Centro;
import com.progetto.Repository.CorsoRepository;
import com.progetto.Repository.CentroRepository;

@Service
public class CorsoServices {
	@Autowired
	private  CorsoRepository corsoRepository;
	@Autowired
	private  CentroRepository centroRepository;
	@Autowired
	private CentroServices centroServices;


	public boolean AggiungiCorso (String nome, String data, Time ora, Centro centro) {
		try {
			corsoRepository.save(new Corso(nome, data, ora, centro));
		}catch (Exception e) {
			System.out.println( "Error in corsoServices: "+ e );
			return false;
		}
		return true;
	}

	public Corso getcorso(String string) {
		return corsoRepository.findByNome(string);
	}


	public Corso Restituisci (String name) {
		return corsoRepository.findByNome(name);
	}
	
	public  Iterable<Corso> RestituisciTutti (){
		return corsoRepository.findAll();
	}



	public List<Corso> getAttvitaCentro(Centro centro) {
		return corsoRepository.findByCentro_id(centro.getId());
	}

	public boolean esistecorso(String nome, String nomeCentro, String citta, String data) {
		return corsoRepository.findByNomeAndCentroAndData(nome, centroRepository.findByNomeAndCitta(nomeCentro, citta), data)!=null;
	}

	public Corso findById(Long id) {
		return corsoRepository.findById(id).get();
	}

	public void save(Corso corso) {
		corsoRepository.save(corso);
		
	}


}
