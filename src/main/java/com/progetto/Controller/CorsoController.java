package com.progetto.Controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.progetto.Enity.Corso;
import com.progetto.Enity.Centro;
import com.progetto.Repository.CorsoRepository;
import com.progetto.Services.AllievoServices;
import com.progetto.Services.CorsoServices;
import com.progetto.Services.CentroServices;

@Controller
public class CorsoController {

	@Autowired
	private AllievoServices allievoServices;
	@Autowired
	private CorsoServices corsoServices;
	@Autowired
	private CentroServices centroServices;
	@Autowired 
	private CorsoRepository corsoRepository;


	@RequestMapping("/registracorso")
	public String FormAggiungtiCentro(Model model) {
		return "registraCorsi";
	}

	@RequestMapping("/Aggiungicorso")
	public String RegistraCentro(
			@RequestParam("nome") String nome,
			@RequestParam("data") String data,
			@RequestParam("ora") String ora,
			@RequestParam("centro") String nomeCentro,
			@RequestParam("citta") String citta,
			Model model) {


		boolean e = false;
		if(!centroServices.esisteCentro(nomeCentro, citta)) {
			model.addAttribute("erroreNome", "centro non presente in questa citta'");
			e=true;
		}
		else {
			if(corsoServices.esistecorso(nome,nomeCentro,citta,data)) {
				model.addAttribute("erroreNome", "centro gia' presente in questa citta'");
				e=true;
			}	else {
				if(nome.isEmpty()) { 
					model.addAttribute("erroreNome", "necessario specificare il nome");
					e=true;
				}
				if(citta.isEmpty()) { 
					model.addAttribute("erroreCitta", "necessario specificare la citta'");
					e=true;
				}
				if(data.isEmpty()) { 
					model.addAttribute("erroreData", "necessario specificare la data");
					e=true;
				}
				if(nomeCentro.isEmpty()) { 
					model.addAttribute("erroreCentro", "necessario specificare il nome del centro");
					e=true;
				}
			}

			if(ora.isEmpty()||ora.length()<4) { 
				model.addAttribute("erroreOra","specificare l'orario con il formato indicato");
				e=true;
			}
		}

		if(e) {
			model.addAttribute("errore", "Impossibile aggiungere centro");
			return "registraCorsi";
		}
		corsoServices.AggiungiCorso(nome, data, java.sql.Time.valueOf(ora.substring(0, 2)+":"+ora.substring(2, 4)+":00"), centroServices.getCentro(nomeCentro, citta));
		return "home";

	}
	
	//ricerca corso che l'alievo può fare nel centro, l'id è quello della pagina dell'allievo che lo identifica
	@GetMapping(value = "/listacorso")
	public String getcorsoCentro(Model model,Authentication aut) {
		model.addAttribute("corso",allievoServices.getCentro (aut.getName()).getcorso());
		return ("corsiinformatica");
	}

	/*
	@RequestMapping("/prova1")
	public String prova1(Model model) {
		List<Corso> tutti = (List<Corso>) corsoServices.RestituisciTutti();
		model.addAttribute("corso",tutti.get(0));
		return "home";
	}

*/

}

