package spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spring.demo.config.JwtTokenProvider;
import spring.demo.model.AuthenticationRequest;
import spring.demo.service.UserService;

@RestController
@RequestMapping("/token")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Value("${security.jwt-token.header-key}")
	private String headerKey;
	


	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody AuthenticationRequest request) throws AuthenticationException {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		//UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenProvider.createToken(userService.getByUserName(request.getUsername()));
		HttpHeaders headers = new HttpHeaders();
		headers.set(headerKey, token);
		return new ResponseEntity<>(headers,HttpStatus.OK);
		//return ResponseEntity.ok(token);

	}

}
