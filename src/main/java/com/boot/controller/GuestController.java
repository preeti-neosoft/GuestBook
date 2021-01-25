package com.boot.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boot.dto.GuestDTO;
import com.boot.entity.Guest;
import com.boot.entity.User;
import com.boot.entity.UserRequest;
import com.boot.entity.UserResponse;
import com.boot.repository.GuestRepository;
import com.boot.service.IGuestService;
import com.boot.service.IUserService;
import com.boot.util.JwtUtil;

@RestController
@RequestMapping("/api")

public class GuestController {
	@Autowired
	private IGuestService guestService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private GuestRepository guestRepo;
	
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager  authenticationManager;
	
	@PostMapping("/saveUser")
	public ResponseEntity<String> SignUp(@RequestBody User user) {
		Integer id=userService.saveUser(user);
		String body="user '"+id+"' saved";
		//return new ResponseEntity<String>(body,HttpStatus.OK);ok
		return ResponseEntity.ok(body);
		
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUserName(),
						 request.getPassword()));
		String token=util.generateToken(request.getUserName());
		
		return ResponseEntity.ok(new UserResponse(token,"Success!!!Token generated NeoSOft-Preeti sharma"));
	}


	@PostMapping(value="/saveGuest")
	public ResponseEntity<?> addGuest( @ModelAttribute GuestDTO guestdto,@RequestPart("file") MultipartFile image) throws IOException{
		String saveGuestDetail = guestService.saveGuestDetail(guestdto, image);

		return ResponseEntity.ok(saveGuestDetail);


	}

	@GetMapping("/getGuestList")
	 public ResponseEntity<?> getAllGuest(@RequestParam Integer pageNo,
	 @RequestParam Integer pageSize){
	 int page_No=pageNo-1;
	 List<Guest> allGuest = guestService.getAllGuest(page_No,pageSize);
	 return ResponseEntity.ok(allGuest);

}


	

/*
 * @DeleteMapping("/deleteGuest/{id}") public ResponseEntity<?>
 * deleteGuestById(@PathVariable Integer id){ Guest guestById =
 * service.findGuestById(id); service.deleteGuest(guestById);
 * 
 * Map<String,Boolean> response=new HashMap(); response.put("deleted",
 * Boolean.TRUE); return ResponseEntity.ok(response);
 * 
 * }
 */
	@DeleteMapping("/deleteGuest/{id}")
	private ResponseEntity<?> deleteGuestById(@PathVariable("id") Integer id){
		return guestService.deleteGuestById(id);
	}
	

	@GetMapping("/guestPerMonth")
	public	ResponseEntity<Integer[]> getNoOfGuestRegisterdPerMonth() {
		Integer[] noOfGuestRegisterdPerMonth = guestService.getNoOfGuestRegisterdPerMonth();
		return ResponseEntity.ok(noOfGuestRegisterdPerMonth);

	}
	
	@GetMapping("/top")
	public  List<Guest> getLatestData(){
		return guestRepo.findTop5ByOrderByIdDesc();
	}
}
