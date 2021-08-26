package com.guilherme.springvideolocadora.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/js/**", "/css/**", "/webfonts/**", "/login").permitAll()
				.antMatchers("/*/deletar/**", "/usuario/**").hasRole("ADMIN")
				.antMatchers("/*/listar/**", 
						"/locacao/devolver/**", "/locacao/cadastrar/**", "/locacao/salvar/**", 
						"/cliente/cadastrar/**", "/cliente/salvar/**",
						"/home", "/index", "/")
					.hasAnyRole("ADMIN", "FUNCIONARIO", "ESTAGIARIO")
				.antMatchers("/*/salvar/**", "/*/editar/**", "/*/cadastrar/**").hasAnyRole("ADMIN", "FUNCIONARIO")
			.anyRequest()
				.authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/", true)
				.usernameParameter("login")
				.passwordParameter("senha")
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
			.and()
				.rememberMe()
					.disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsServiceImpl)
			.passwordEncoder(passwordEncoder);
	}
	
}
