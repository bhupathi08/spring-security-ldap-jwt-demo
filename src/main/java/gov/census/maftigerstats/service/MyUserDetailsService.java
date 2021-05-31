package gov.census.maftigerstats.service;

import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = new User("Sai", "password", Collections.singleton(new SimpleGrantedAuthority("USER")));
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getAuthorities().toString());
		return user;
	}

}