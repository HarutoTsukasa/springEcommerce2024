package com.sena.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//clase interceptor con dos anotaciones jpa
@Configuration
public class SpingBootSecurity {
	
	
	//objeto inyectado a la clase
	@Autowired
	private UserDetailsService userDetailService;
	
	// validar que el usuario sea el correcto
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(getEnecoder());
	}
	
	
	// restringir vistas al usuario
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     
//		http.csrf().disable().authorizeRequests()
//		.antMatchers("/administrador/**").hasRole("ADMIN")
//		.antMatchers("/productos/**").hasRole("ADMIN")
//		.and().formLogin().loginPage("/usuario/login")
//		.permitAll().defaultSuccessUrl("/usuario/acceder");
		
		http.authorizeRequests()
        .requestMatchers("/administrador/**").hasRole("ADMIN")
        .requestMatchers("/productos/**").hasRole("ADMIN")
        .and()
        // csrf evita que inyecten codigo malisioso a la aplicacion
        .csrf(csfr -> csfr.disable())
        .formLogin()
        .loginPage("/usuario/login")
        .permitAll()
        .defaultSuccessUrl("/usuario/acceder")
        .and()
        .logout()
        .permitAll();
		
		return http.build();

    }
	
	@Bean
	public BCryptPasswordEncoder getEnecoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
