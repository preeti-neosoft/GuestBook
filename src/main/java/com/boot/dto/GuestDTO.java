package com.boot.dto;

import java.util.Date;

import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class GuestDTO {
	
	 private String firstName;
	
	 private String lastName;
	 private String email;
	 private long mobileNo;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateOfRegistration;
	
	//@Lob
	private byte[] image;

}
