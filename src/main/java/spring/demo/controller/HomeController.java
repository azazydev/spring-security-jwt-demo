package spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.demo.model.User;
import spring.demo.service.UserService;

@RestController
@RequestMapping
public class HomeController {
	@Autowired
	private UserService userService;

	@RequestMapping("/status")
	public String getStatus() {
		return "Up ";
	}

	@RequestMapping("/user")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping("/admin")
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String getAdmin() {
		return "Admin";
	}

}
