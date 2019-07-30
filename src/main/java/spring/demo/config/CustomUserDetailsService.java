package spring.demo.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import spring.demo.model.User;
import spring.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepository.getByUserName(username);
		List<GrantedAuthority> authorities=user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleCode())).collect(Collectors.toList());
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
		;
		return org.springframework.security.core.userdetails.User//
				.withUsername(username)//
				.password(encoder.encode("123"))//user.getPassword()
				.authorities(authorities)//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();
	};

}
