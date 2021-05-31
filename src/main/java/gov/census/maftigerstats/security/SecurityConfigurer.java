package gov.census.maftigerstats.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import gov.census.maftigerstats.filters.JwtRequestFilter;
import gov.census.maftigerstats.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

//	@Autowired
//	MyUserDetailsService myUserDetailsService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication().userDnPatterns("uid={0},ou=people").groupSearchBase("ou=groups").contextSource()
				.url("ldap://localhost:10389/dc=springframework,dc=org").and().passwordCompare()
				.passwordEncoder(new LdapShaPasswordEncoder()).passwordAttribute("userPassword");
//		auth.userDetailsService(myUserDetailsService);
//		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http .authorizeRequests() .anyRequest().fullyAuthenticated() .and()
		 * .formLogin();
		 */

		http.authorizeRequests().antMatchers("/api/authenticate", "/api/refreshtoken").permitAll()
				.antMatchers("/api/**").authenticated().and().csrf().disable().formLogin().disable().httpBasic()
				.disable().logout().disable();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
}