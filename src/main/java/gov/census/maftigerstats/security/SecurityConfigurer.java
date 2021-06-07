package gov.census.maftigerstats.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import gov.census.maftigerstats.filters.JwtRequestFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	CustomUserDetailsContextMapper customUserDetailsContextMapper;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication()
				.userDetailsContextMapper(userDetailsContextMapper()).userDnPatterns("uid={0},ou=people").groupSearchBase("ou=groups")
				.contextSource(contextSource()).passwordCompare()
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

	@Bean
	public DefaultSpringSecurityContextSource contextSource() {
		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(
				Arrays.asList("ldap://localhost:10389/"), "dc=springframework,dc=org");
		return contextSource;
	}

	@Bean
	public UserDetailsContextMapper userDetailsContextMapper() {
		return customUserDetailsContextMapper;
	}

}