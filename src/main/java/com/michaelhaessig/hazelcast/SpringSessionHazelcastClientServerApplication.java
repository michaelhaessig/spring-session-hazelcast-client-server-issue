package com.michaelhaessig.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.hazelcast.SessionUpdateEntryProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringSessionHazelcastClientServerApplication extends WebSecurityConfigurerAdapter {

    @RequestMapping("/")
    @ResponseBody
    public String app(){

        SecurityContext context = SecurityContextHolder.getContext();

        Authentication authentication = context.getAuthentication();

        // principal access
        Object principal = authentication.getPrincipal();

        return "Logged in: " + authentication.getName() + " with principal class: " + principal.getClass().getName();
    }

	@Bean // custom user details with constant "password"
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

	@Bean // comment out to disable hazelcast session auto config
	public HazelcastInstance hazelcastInstance() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress("0.0.0.0:5701"); // connect to local server
		clientConfig.getUserCodeDeploymentConfig().setEnabled(true)
				.addClass(Session.class).addClass(MapSession.class)
				.addClass(SessionUpdateEntryProcessor.class);
		return HazelcastClient.newHazelcastClient(clientConfig);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSessionHazelcastClientServerApplication.class, args);
	}

}

