package br.com.lupus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.lupus.config.filters.JWTFilter;

/**
 * <h1>	Classe de configuração do Spring Security </h1>
 * <p>	Configura os end-points liberados e os quais necessitam autenticação </p>
 * <p> 	Desativa o csrf e mantem ativado o cors </p>
 * <p>	Define o filtro utilizado para fazer a autenticação e a o tipo de autenticação </p>
 * 
 * @author Mateus A.S
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
								.authorizeRequests()
								.antMatchers("/api/v1/jwt", "/app/**", "/assets/**").permitAll()
								.anyRequest().authenticated()
								.and()
								.csrf().disable()
								.cors();
		http.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
