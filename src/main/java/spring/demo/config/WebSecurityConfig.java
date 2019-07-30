package spring.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Value("${security.allowed-origins}")
	private List<String> allowedOrigins;
	@Value("${security.allowed-headers}")
	private List<String> allowedHeaders;
	@Value("${security.exposed-headers}")
	private List<String> exposedHeaders;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Configure cors
		http.cors().configurationSource(corsConfigurationSource());
		// Disable CSRF (cross site request forgery)
		http.csrf().disable();

		// No session will be created or used by spring security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

		// Entry points
		http.authorizeRequests().antMatchers("/token/**").permitAll().antMatchers("/user").permitAll().anyRequest()
				.authenticated();

		// If a user try to access a resource without having enough permissions
		// http.exceptionHandling().accessDeniedPage("/login");

		// Apply JWT
		JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

		// Optional, if you want to test the API from a browser
		http.httpBasic();

	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOrigins);
		//configuration.setAllowedHeaders(allowedHeaders);
		
		configuration.addAllowedHeader("Authorization");
		configuration.addAllowedHeader("Content-Type");
		configuration.setExposedHeaders(exposedHeaders);

		//configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS"));
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600l);
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
