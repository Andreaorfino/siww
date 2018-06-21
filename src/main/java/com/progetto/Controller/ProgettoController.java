package com.progetto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import com.progetto.Enity.Allievo;
import com.progetto.Enity.Corso;
import com.progetto.Enity.Centro;
import com.progetto.Enity.Responsabile;
import com.progetto.Enity.User;
import com.progetto.Services.AllievoServices;
import com.progetto.Services.CorsoServices;
import com.progetto.Services.CentroServices;
import com.progetto.Services.ResponsabileService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.AbstractMap.SimpleEntry;


@Controller
public class ProgettoController{
	@Autowired
	private AllievoServices allievoServices;
	@Autowired
	private CorsoServices corsoServices;
	@Autowired
	private CentroServices centroServices;

	@Autowired
	private ResponsabileService responsabileService;


	@RequestMapping("/admin")
	public String admin (Authentication aut,Model model) {
		aut.getName();
		Optional<User> admin =  responsabileService.getresponsabileUsername(aut.getName());
		if(!admin.isPresent()) return "redirect:/user";
		model.addAttribute("responsabile",(Responsabile) admin.get());
		return ("/admin.html");
	} 

	@RequestMapping("/user")
	public String user (Authentication aut,Model model) {
		//info allievo
		aut.getName();
		Optional<User> userCorrente = allievoServices.getAllievoUsername(aut.getName());
		if(!userCorrente.isPresent()) return "redirect:/admin";
		Allievo allievoCorrente = (Allievo) userCorrente.get();
		model.addAttribute("allievo",allievoCorrente);

		//corso a cui si e iscritti
		List<Corso> corsoIscritte = allievoServices.getcorso(aut.getName()).getcorso();
		model.addAttribute("corso",corsoIscritte);

		//corso a cui iscriversi
		List<SimpleEntry<String, String>> info = new ArrayList<SimpleEntry<String, String>>();
		for(Corso corso : allievoServices.getCentro(aut.getName()).getcorso()) {
			Long id = corso.getId();
			boolean b = true;
			for(Corso corsoIscritta : corsoIscritte) {
				if(corsoIscritta.getId()==id) {
					b=false;
				}
			}
			if(b) {
				String value = corso.getNome()
						+ " alle " + corso.getOra().toString() + " il " + corso.getData();
				info.add(new SimpleEntry<String, String>(id.toString(),value));
			}
		}

		//centro dell'allievo
		model.addAttribute("centro",allievoCorrente.getCentro());

		model.addAttribute("iscriviti",info);
		return ("/user.html");
	} 

	@RequestMapping("/login")
	public String restituisciaccesso () {

		return ("login.html");
	}


	@RequestMapping ("/tuttelecorso")
	public String getTutteLecorso (Model model) {

		model.addAttribute("corso", corsoServices.RestituisciTutti());
		return "corsi";
	}

	//restituisce tutti i centri
	@RequestMapping ("/ricercatuttiicentri")
	public String getTuttiiCentri (Model model) {
		model.addAttribute("centro", centroServices.getCentri());
		return ("tuttiicentri");
	}



	@RequestMapping("tutto")
	public String show(Model model)
	{
		List<Allievo> alliev = (List<Allievo>) allievoServices.RestituisciTutti();
		List<Centro> centr = (List<Centro>) centroServices.RestituisciTutti();
		List<Corso> attivit = (List<Corso>) corsoServices.RestituisciTutti();
		model.addAttribute("allievi",alliev);
		model.addAttribute("centri",centr);
		model.addAttribute("corso",attivit);
		return "tutto";
	}


	//solo con role=user
	@RequestMapping("/user/confermalog")
	public String ConfermaLog (@RequestParam("nome")String nome, @RequestParam("password") String password) {
		return (nome+" "+password);
	}
	

	//non l'ho usata
	/*	@ResponseBody
	@RequestMapping("/nuovoutente")
	public String iscrizione (@RequestParam("nome")String nome, @RequestParam("cognome")String cognome, @RequestParam("password")String password, @RequestParam ("confermapassword") String confermapassword) {

		utenteServices.AddElem(nome, cognome,password);
		return(utenteServices.getUtente(nome).toString());
	}*/



	@RequestMapping("/inizializza")
	public String inizializza(Model model)
	{
		if(allievoServices.getAllievi().isEmpty()) {
			Centro centro = new Centro("SF united","sfCentro@emma.it", "roma", 33578953, 100);
			centroServices.save(centro);
			responsabileService.save(new Responsabile("pass", "lorenzo", "pratico", "amministratore", centro));
			corsoServices.AggiungiCorso("matematica", "12/12/18", java.sql.Time.valueOf("12:00:00"), centro);
			corsoServices.AggiungiCorso("italiano", "12/12/18", java.sql.Time.valueOf("13:00:00"), centro);
			allievoServices.AggiungiAllievo("luca", "monaco", "roma", "12/12/96", "luke@gmail.com",
					centro, "pass", "3347995318", "luke");
		}
		
		List<Allievo> alliev = (List<Allievo>) allievoServices.RestituisciTutti();
		List<Centro> centr = (List<Centro>) centroServices.RestituisciTutti();
		List<Corso> attivit = (List<Corso>) corsoServices.RestituisciTutti();
		model.addAttribute("allievi",alliev);
		model.addAttribute("centri",centr);
		model.addAttribute("corso",attivit);

		return "tutto";
	}




}