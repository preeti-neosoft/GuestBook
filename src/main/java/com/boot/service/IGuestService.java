package com.boot.service;



import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.boot.dto.GuestDTO;
import com.boot.entity.Guest;
import com.boot.repository.GuestRepository;


public interface IGuestService {
	String saveGuestDetail(GuestDTO guestdto,MultipartFile file) throws IOException;

	//List<Guest> getAllGuest();

	List<Guest> getAllGuest(Integer pageNo, Integer pageSize);

	



Guest findGuestById(Integer id);

void deleteGuest(Guest guestById);

Integer[] getNoOfGuestRegisterdPerMonth();

ResponseEntity<?> deleteGuestById(Integer id);


}
