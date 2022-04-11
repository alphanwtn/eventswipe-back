package com.M2IProject.eventswipe.security;

import static java.lang.String.format;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.M2IProject.eventswipe.repository.UserEntityRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = passwordEncoder();

		// Load users from DB
		auth.userDetailsService(username -> userEntityRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(format("User: %s, not found", username))));

		// Put users in memory
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN",
				"USER");
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("user")).roles("USER");
	}

	@Override // Definit les requetes autorisée en fct des roles des users
	protected void configure(HttpSecurity http) throws Exception {

		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();

		// Set session management to stateless
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

		// Set unauthorized requests exception handler
		http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}).and();

		// Set permissions on endpoints
		http.authorizeRequests()
				// Our public endpoints
				.anyRequest().permitAll();

		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

		// http.httpBasic(); // invoque le formulaire de base du navigateur

		// http.authorizeHttpRequests().antMatchers("/", "/login",
		// "/logout").permitAll();
		// http.authorizeHttpRequests().antMatchers("/get-all-genres-by-segment-id").permitAll();
		// Peut pas creer si deja user
		// http.authorizeHttpRequests().antMatchers(HttpMethod.POST,
		// "/users").hasAnyRole("ADMIN", "ANONYMOUS");

		// http.authorizeHttpRequests().anyRequest().authenticated();

		// http.authorizeHttpRequests().antMatchers("/user/**",
		// "/delete**/**").hasRole("ADMIN");
		// activation ou non du token anti csrf
		http.logout().logoutSuccessUrl("/afterlogout.html");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Used by spring security if CORS is enabled.
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
