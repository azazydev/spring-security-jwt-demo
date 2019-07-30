package spring.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import spring.demo.config.JwtTokenProvider;
import spring.demo.model.User;
import spring.demo.repository.UserRepository;


@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	public User getByUserId(Integer userId) {
		return userRepository.findById(userId).get();
	}

	public User getByUserName(String userName) {
		return userRepository.getByUserName(userName);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

}
