package com.boot.dto;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

public class UserDto {
	
	private String name;
	private String userName;
	private String password;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="roles_Tab",
	                 joinColumns=@JoinColumn(name="id")
	                 )
	@Column(name="role")
	private Set<String> roles;
	

}
