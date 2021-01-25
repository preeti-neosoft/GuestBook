package com.boot.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.entity.User;
import com.boot.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Override
	public Integer saveUser(User user) {
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		 return repo.save(user).getId();
		
	}
	
	// get one use bye username
			 public Optional<User> findByUserName(String username){
				 System.out.println("user from database"+repo.findByUserName(username));
				 return repo.findByUserName(username);
			}

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			  Optional<User> optional =findByUserName(username);
			  if(optional.isEmpty())
				  throw new UsernameNotFoundException("user not exist");
			  //user from database
			  User user=optional.get();
			  
			  return new org.springframework.security.core.userdetails.User(
						username, 
						user.getPassword(),
						user.getRoles().stream()
						.map(role->new SimpleGrantedAuthority(role))
						.collect(Collectors.toList())
						);
			
			
		}
}
