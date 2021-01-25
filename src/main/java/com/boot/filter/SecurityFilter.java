package com.boot.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.boot.util.JwtUtil;


@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private UserDetailsService userdetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//1. Read token from Auth head
		String token=request.getHeader("Authorization");
		if(token!=null) {
			
			String UserName=util.getUserName(token);
			if(UserName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
				
				UserDetails user=userdetailsService.loadUserByUsername(UserName);
				// validate token with 
			boolean isValid=util.validateToken(token, user.getUsername());
			if(isValid) {
				UsernamePasswordAuthenticationToken authenticationToken=
						new UsernamePasswordAuthenticationToken(UserName ,user.getPassword(),user.getAuthorities());
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
				
			}
		}
		filterChain.doFilter(request, response);
	}

}
