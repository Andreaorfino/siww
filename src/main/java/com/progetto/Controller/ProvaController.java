package com.progetto.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ProvaController {
	//----------------------------//
	
		//METODI DI PROVA PER ADMIN E USER
		//RIEMPIRE MAUALMENTE LE ENTITA DI USER,ROLE E USER-ROLE PROVARE
		
		@PreAuthorize("hasAnyRole('ADMIN')")
		@RequestMapping("/admin/secured")
	    public  String Admin() {
	        return "admin";
	    }
		
		@PreAuthorize("hasAnyRole('USER')")
		@RequestMapping("/user/secured")
	    public String User() {
	        return "user";
	    }
		
//		@PreAuthorize("hasAnyRole('USER','ADMIN')")
//		@RequestMapping("/useradmin")
//	    public @ResponseBody String UserAdmin() {
//	        return "USER - ADMIN";
//	    }
	//    
	  //----------------------------//
	    
		//forbidden
		@RequestMapping("/accessDenied")
		public String accessDenied() {
		    return "accessDenied";
		}
		
		@RequestMapping("/home")
		public String home() {
		    return "home";
		}

}
