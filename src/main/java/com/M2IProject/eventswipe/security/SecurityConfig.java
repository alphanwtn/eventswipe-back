package com.M2IProject.eventswipe.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = passwordEncoder();

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"SELECT email as principal, password as credentials, active FROM users WHERE email=?")
				.authoritiesByUsernameQuery(
						"select email principal, name role FROM users U RIGHT JOIN users_roles UR ON U.id = UR.users_id LEFT JOIN roles R ON UR.roles_id = R.id WHERE email=?")
				.passwordEncoder(passwordEncoder).rolePrefix("ROLE_");

		// System.out.println(passwordEncoder.encode("chocapic"));
		// System.out.println(passwordEncoder.encode("jocelin"));

		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN",
				"USER");
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("user")).roles("USER");
	}

	@Override // Definit les requetes autorisée en fct des roles des users
	protected void configure(HttpSecurity http) throws Exception {
		// http.formLogin(); // invoque le formu de base de spring sec, on peut mettre
		// en paramtres notre
		// propre formu
		http.httpBasic(); // invoque le formulaire de base du navigateur
		http.authorizeHttpRequests().antMatchers("/user/**", "/delete**/**").hasRole("ADMIN");
		http.authorizeHttpRequests().antMatchers("/users").authenticated();
		http.authorizeHttpRequests().anyRequest().permitAll();
		// http.authorizeHttpRequests().anyRequest().authenticated();
		// http.authorizeHttpRequests().anyRequest().permitAll();
		// http.authorizeHttpRequests().antMatchers("/user/**").permitAll(); // autorise
		// tous les user a y accéder
		http.csrf().disable(); // activation ou non du token anti csrf

		http.logout().logoutSuccessUrl("/afterlogout.html");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
