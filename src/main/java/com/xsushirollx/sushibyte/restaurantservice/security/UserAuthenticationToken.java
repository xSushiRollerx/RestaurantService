package com.xsushirollx.sushibyte.restaurantservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.xsushirollx.sushibyte.restaurantservice.model.User;


public class UserAuthenticationToken implements Authentication {

	private static final long serialVersionUID = 1L;

	private final User user;
	private final String jwtToken;

	private Logger log = Logger.getLogger("CustomerAuthenticationToken");

	private Boolean isAuthenticated = true;

	public UserAuthenticationToken(User user, String jwtToken) {
		log.log(Level.INFO, "role: " + user.getRole());
		this.user = user;
		this.jwtToken = jwtToken;
	}

	@Override
	public String getName() {
		return user.getId().toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.log(Level.INFO, "Authentication Authorities SET");
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (user.getRole() == 0) {
			authorities.add(new SimpleGrantedAuthority("NONE"));
			log.log(Level.INFO, "Customer null so token null");
			return authorities;
		}
		switch (user.getRole()) {
		case 1:
			authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
			log.log(Level.INFO, "Customer user");
			break;
		case 2:
			authorities.add(new SimpleGrantedAuthority("ADMINISTRATOR"));
			log.log(Level.INFO, "Customer administrator");
			break;
		case 3:
			authorities.add(new SimpleGrantedAuthority("DRIVER"));
			log.log(Level.INFO, "Customer driver");
			break;
		default:
			authorities.add(new SimpleGrantedAuthority("NONE"));
			log.log(Level.INFO, "Customer none");
			break;
		}
		return authorities;

	}

	@Override
	public String getCredentials() {
		return jwtToken;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public User getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;

	}
}
