package com.progetto.Controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import com.progetto.Enity.Allievo;
import com.progetto.Enity.Corso;
import com.progetto.Services.AllievoServices;
import com.progetto.Services.CorsoServices;
import com.progetto.Services.CentroServices;

@Controller
public class AllievoController{
	@Autowired
	private AllievoServices allievoServices;
	@Autowired
	private CorsoServices corsoServices;
	@Autowired
	private CentroServices centroServices;

	@RequestMapping("/registraAllievo")
	public String FormAggiungiAllievo()
	{
		return "AllieviRegistrati";
	}


	@RequestMapping("/AggiungiAllievo")
	public String addAllievo(
			@RequestParam("nome") String nome,
			@RequestParam("cognome") String cognome,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("mail") String mail,
			@RequestParam("luogo") String luogo,
			@RequestParam("data") String data,
			@RequestParam("telefono") String telefono,
			@RequestParam("centro") String centro,
			@RequestParam("citta") String citta,
			Model model
			)
	{
		boolean e = false;


		if (!centroServices.esisteCentro(centro, citta)){
			model.addAttribute("erroreCentro", "errore il centro non esiste");
			e=true;
		}
		if(mail.isEmpty()) {
			model.addAttribute("erroreMail", "inserire mail");
			e=true;
		}
		if(allievoServices.esisteMail(mail)) {
			model.addAttribute("erroreMail", "utente gia presente nel sistema");
			e=true;
		}
		if(nome.isEmpty()) {
			model.addAttribute("erroreNome", "non lasciare vuoto questo campo");
			e=true;
		}
		if(cognome.isEmpty()) {
			model.addAttribute("erroreCognome", "non lasciare vuoto questo campo");
			e=true;
		}
		if(allievoServices.esisteUsername(username)) {
			model.addAttribute("erroreUsername", "username gia' esistente");
		}
		if(username.isEmpty()) {
			model.addAttribute("erroreUsername", "non lasciare vuoto questo campo");
			e=true;
		}
		if(password.isEmpty()) {
			model.addAttribute("errorepassword", "non lasciare vuoto questo campo");
			e=true;
		}
		if(luogo.isEmpty()) {
			model.addAttribute("erroreLuogo", "non lasciare vuoto questo campo");
			e=true;
		}
		if(data.isEmpty()) {
			model.addAttribute("erroreData", "non lasciare vuoto questo campo");
			e=true;
		}
		if(telefono.isEmpty()) {
			model.addAttribute("erroreTelefono", "non lasciare vuoto questo campo");
			e=true;
		}
		if(centro.isEmpty()) {
			model.addAttribute("erroreCentro", "non lasciare vuoto questo campo");
			e=true;
		}
		if(citta.isEmpty()) {
			model.addAttribute("erroreCitta", "non lasciare vuoto questo campo");
			e=true;
		}

		if(e) {
			model.addAttribute("errore", "impossibile aggiungere l'allievo");
			return "AllieviRegistrati";
		}

		allievoServices.AggiungiAllievo(nome, cognome, luogo, data, mail, centroServices.getCentro(centro,citta), password, telefono, username);
		centroServices.setAllievo( (Allievo) allievoServices.getAllievoNome(nome).get(0), centroServices.getCentro(centro,citta));
		return "home";
	}

	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping("/Allievocorso")
	public String FormAggiungicorso(Model model, Authentication authentication) {
		List<SimpleEntry<String, String>> info = new ArrayList<SimpleEntry<String, String>>();
		for(Corso corso : allievoServices.getCentro(authentication.getName()).getcorso()) {
			info.add(new SimpleEntry<String, String>(corso.getId().toString(),corso.getNome()
					+ " alle " + corso.getOra().toString() + " il " + corso.getData()));
		}
		model.addAttribute("corso",info);
		return "allievocorso";
	}

	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping("/CollegaAllievocorso/{id}")
	public String collegaAllievocorso(@PathVariable("id") Long id, Model model, Authentication authentication) {
		Allievo allievoCorrente = (Allievo) allievoServices.getAllievoUsername(authentication.getName()).get();
		allievoCorrente.addcorso(corsoServices.findById(id));
		Corso corso = corsoServices.findById(id);
		corso.addAllievo(allievoCorrente);
		corsoServices.save(corso);
		allievoServices.salva(allievoCorrente);
		return "redirect:/user";
	}
	
	//restituisce tutti gli allievi
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/allAllievo")
	public String AllAllievi(Model model) {
		List<Allievo> allievi = allievoServices.getAllievi();
		model.addAttribute("allievi", allievi);
		return "stampaallievi.html";
	}
	


	@RequestMapping("/addAllievo")
	public String vai(Model model)
	{
		String err = "";
		model.addAttribute("errore",err);
		return "allieviRegistrati";
	}

	@RequestMapping("/addAllievoErr")
	public String vai2(Model model)
	{
		String err = "errore";
		model.addAttribute("errore",err);
		return "allieviRegistrati";
	}

}