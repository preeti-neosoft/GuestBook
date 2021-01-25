package com.boot.service;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boot.commons.Constant;
import com.boot.dto.GuestDTO;
import com.boot.entity.Guest;
import com.boot.exception.RecourseNotFoundException;
import com.boot.repository.GuestRepository;

import jdk.internal.module.ModuleLoaderMap.Mapper;

@Service
public class GuestServiceImpl implements IGuestService{
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public String saveGuestDetail(GuestDTO guestdto,MultipartFile file) throws IOException {
		String message;
		/*
		 * Guest guest=new Guest(); guest.setFirstName(guestdto.getFirstName());
		 * guest.setLastName(guestdto.getLastName());
		 * guest.setEmail(guestdto.getEmail());
		 * guest.setMobileNo(guestdto.getMobileNo());
		 * guest.setDateOfRegistration(guestdto.getDateOfRegistration());
		 * 
		 */
		Guest guest=new Guest();
		mapper.map(guestdto, guest);
		byte[] bytes = file.getBytes();
		guest.setImage(bytes);
		guest.setCreated(LocalDateTime.now());
		guest.setStatus(Boolean.TRUE);
		System.out.println("boolean value..."+guest.getStatus());
		
		Guest saveGuest = guestRepository.save(guest);
		if(saveGuest!=null) 
			message=Constant.SAVE_GUEST_DETAILS;
		else
			message=Constant.NOT_SAVED;
		
		
		
		return message;

}

	
	@Override
	public List<Guest> getAllGuest( Integer pageNo,
			 Integer pageSize) {
		Pageable paging=PageRequest.of(pageNo,pageSize);
		Page<Guest> pagedResult=guestRepository.findAll(paging);
		pagedResult.forEach(System.out::println);
	 
	
	

		
		if(pagedResult.hasContent()) {
			return pagedResult.getContent();
		}
		else {
			return new ArrayList<Guest>();
		}
		
	}


	

	@Override
	public Guest findGuestById(Integer id) {
		Guest guest = guestRepository.findById(id).orElseThrow(()->new RecourseNotFoundException("Employee not Exist with id :"+id));
		return guest;
	}


	@Override
	public void deleteGuest(Guest guestById) {
		
//	guestRepository.delete(guestById);
		
	}


	@Override
	public Integer[] getNoOfGuestRegisterdPerMonth() {
		Integer[] noOfGuestRegisterdPerMonth = guestRepository.getNoOfGuestRegisterdPerMonth();
		return noOfGuestRegisterdPerMonth;
	}
@Override
public ResponseEntity<?> deleteGuestById(Integer id) {
	Optional<Guest> findById = guestRepository.findById(id);
	if(findById.isPresent()) {
		Guest guest = findById.get();
		guestRepository.softDelete(guest.getId());
		return new ResponseEntity<>("Data Deleted...",HttpStatus.OK);
	}
	else {
	return new ResponseEntity<>("Data Not Deleletd",HttpStatus.NOT_FOUND);
}

	
}
}