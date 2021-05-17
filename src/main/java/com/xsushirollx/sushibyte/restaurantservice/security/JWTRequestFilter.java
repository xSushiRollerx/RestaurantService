package com.xsushirollx.sushibyte.restaurantservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xsushirollx.sushibyte.restaurantservice.dao.UserDAO;
import com.xsushirollx.sushibyte.restaurantservice.model.User;



@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	JWTUtil util;

	@Autowired
	UserDAO cdao;



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// assume authentication so valid jwt which starts w/ "Bearer "
		try {
			String token = request.getHeader("Authorization").substring(7);
			int userId = Integer.parseInt(util.extractUserId(request.getHeader("Authorization").substring(7)));
			
			User customer = null;
			
			if (util.validateToken(token)) {
				customer = cdao.findById(userId).get();
			} else {
				customer = new User(0,0); 
			}
			
			UserAuthenticationToken customerAuthentication = new UserAuthenticationToken(customer, token);
			SecurityContextHolder.getContext().setAuthentication(customerAuthentication);
		} catch (Exception e) {

		} finally {
			filterChain.doFilter(request, response);
		}
	}


}
