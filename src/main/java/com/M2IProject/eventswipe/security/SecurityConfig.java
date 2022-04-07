package com.M2IProject.eventswipe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = passwordEncoder();
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN",
				"USER");
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("user")).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin(); // invoque le formu de base de spring sec, on peut mettre en paramtres notre
							// propre formu
		// http.httpBasic(); invoque le formulaire de base du navigateur

		http.authorizeHttpRequests().antMatchers("/user/**", "/delete**/**").hasRole("ADMIN");
		http.authorizeHttpRequests().anyRequest().authenticated();

		http.csrf().disable(); // activation ou non du token anti csrf
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
