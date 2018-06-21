package com.progetto.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.progetto.CustomService.CustomService;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	 @Autowired
	    private CustomService userDetailsService;

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService)
	        .passwordEncoder(getPasswordEncoder());
	        }
	    
	    private PasswordEncoder getPasswordEncoder() {
	        return new PasswordEncoder() {
	            @Override
	            public String encode(CharSequence charSequence) {
	                return charSequence.toString();
	            }

	            @Override
	            public boolean matches(CharSequence charSequence, String s) {
	                return charSequence.equals(s);
	            }
	        };
	    }
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {

	  
	        //http.csrf().disable();
	        http.authorizeRequests()
            .antMatchers("/admin", "/user").authenticated()
            .antMatchers("/resurces/**").permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            //.defaultSuccessUrl()
            	.permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/index");   		
	    }
	    
	    @Bean
	    public AccessDeniedHandler accessDeniedHandler(){
	        return new CustomAccessDeniedHandler();
	    }	    
}