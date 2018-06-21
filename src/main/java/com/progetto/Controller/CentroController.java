package com.progetto.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Centro;
import com.progetto.Enity.Corso;
import com.progetto.Services.AllievoServices;
import com.progetto.Services.CorsoServices;
import com.progetto.Services.CentroServices;

@Controller
public class CentroController {
	@Autowired
	private AllievoServices allievoServices;
	@Autowired
	private CorsoServices corsoServices;
	@Autowired
	private CentroServices centroServices;

	@RequestMapping("/registraCentro")
	public String FormAggiungiCentro(Model model) {
		return "centroRegistrati";
	}

	@RequestMapping("/AggiungiCentro")
	public String RegistraCentro(
			@RequestParam("nome") String nome,
			@RequestParam("mail") String mail,
			@RequestParam("citta") String citta,
			@RequestParam("telefono") String telefono,
			@RequestParam("max_allievo") String max_allievo,
			Model model) {


		boolean e = false;
		if(centroServices.esisteCentro(nome, citta)) {
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
		}

		if(mail.isEmpty()) { 
			model.addAttribute("erroreMail","necessario specificare mail");
			e=true;
		}
		else {
			model.addAttribute("mail", mail);
		}
		if(telefono.isEmpty()) {
			model.addAttribute("erroreTelefono", "necessario specificare il telefono");
			e=true;
		}
		else {
			model.addAttribute("telefono", telefono);
		}
		if(max_allievo.isEmpty()) {
			model.addAttribute("erroreMax_allievo", "specificare il numero massimo di allievi");
			e=true;
		}
		else {
			model.addAttribute("max_allievo",max_allievo);
		}

		if(e) {
			model.addAttribute("errore", "Impossibile aggiungere centro");
			return "centroRegistrati";
		}
		Integer tel = Integer.parseInt(telefono);
		Integer max = Integer.parseInt(max_allievo);
		int tel1 = tel;
		int max1 = max;
		Centro centro = new Centro(nome, mail, citta,tel1, max1);
		centroServices.AddElem(centro);
		return "home";

	}

	@RequestMapping("/tuttiCentri")
	public String mostraCentri(Model model) {
		model.addAttribute("centri",centroServices.findAll());
		return "centri";
	}

	//attvità del centro
	@RequestMapping("/corsoDelCentro")
	public String getcorsoDelCentro(@RequestParam("nome")String nome,@RequestParam("citta")String citta, Model model) {
		model.addAttribute("corso", corsoServices.getAttvitaCentro (centroServices.getCentro(nome,citta)));
		return ("corsoCentro");
	}

	@RequestMapping("/ricercacentri")
	public String getCentriAllievo(Model model) {
		List<SimpleEntry<String, String>> info = new ArrayList<SimpleEntry<String, String>>();
		for(Centro centro : centroServices.findAll()) {
			info.add(new SimpleEntry<String, String>(centro.getId().toString(), centro.getNome()+" @" + centro.getCitta() ));
		}
		model.addAttribute("centri", info);
		return "allievicentro";
	}



	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/centriAllievo/{id}")
	public String collegaAllievocorso(@PathVariable("id") Long id, Model model) {
		if(centroServices.findById(id).isPresent()){
			System.err.println(centroServices.findById(id).get().getAllievi().toString());
			model.addAttribute("allievi",(centroServices.findById(id)).get().getAllievi());
			return "allieviCentro2";
		}
		return "redirect:/admin";
	}


}
