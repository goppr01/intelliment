package com.inteliment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
	
	private static String REALM="MY_TEST_REALM";
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	       .csrf().disable()    
	        .authorizeRequests()
	        .antMatchers("/counter-api/**").hasRole("ADMIN")
	        .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
	         .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
	    }

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	            .inMemoryAuthentication()
	                .withUser("admin").password("admin").roles("ADMIN");
	    }
	    
	    
	    @Bean
	    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
	        return new CustomBasicAuthenticationEntryPoint();
	    }

}
