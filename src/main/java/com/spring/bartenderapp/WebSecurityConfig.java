package com.spring.bartenderapp;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private ApplicationUserDetailsService _userDetailService;

	public WebSecurityConfig(ApplicationUserDetailsService userDetailsService) {
		_userDetailService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/h2/**").permitAll()
		.antMatchers("/**").permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();

		http.authorizeRequests()
		.antMatchers("/register*").permitAll()
		.antMatchers("/login*").permitAll().anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login.html").permitAll()
		.and()
		.logout().permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(_userDetailService);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return _userDetailService;
	}
}
