package com.app.employeePortal.registration.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.registration.mapper.EmailValidationMapper;
import com.app.employeePortal.registration.mapper.NewAdminRegisterMapper;
import com.app.employeePortal.registration.mapper.UserPasswordRq;
import com.app.employeePortal.registration.service.RegistrationService;

@RestController
@CrossOrigin(maxAge = 3600)

@RequestMapping("/api/v1")
public class RegistrationController {
	
	@Autowired
	RegistrationService registrationService;
	@Autowired
    private TokenProvider jwtTokenUtil;
	
//	@PostMapping(value = "/registration")
//	public ResponseEntity<?> processRegistration( @RequestBody AdminRegisterMapper adminRegisterMapper,
//                                                  HttpServletRequest request){
//		
//		String emailId = adminRegisterMapper.getEmployee().getEmailId();
//		HashMap map = new HashMap();
//		
//	    if (registrationService.emailExist(emailId)) {
//	    map.put("emailInd", true);			
//			
//			return new ResponseEntity<>(map,HttpStatus.OK);
//		}else {
//		
//			String orgId = registrationService.adminRegistrationProcess(adminRegisterMapper);
//			return new ResponseEntity<>(orgId,HttpStatus.OK);
//
//		}
//
//		
//	}
	
	@PostMapping(value = "/registration")
	public ResponseEntity<?> processRegistration(@RequestBody NewAdminRegisterMapper adminRegisterMapper,
			HttpServletRequest request) {

		String emailId = adminRegisterMapper.getEmailId();
		HashMap map = new HashMap();

		if (registrationService.emailExist(emailId)) {
			map.put("emailInd", true);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {

			String orgId = registrationService.adminRegistrationProcess(adminRegisterMapper);
			return new ResponseEntity<>(orgId, HttpStatus.OK);

		}


}
	
	
	@RequestMapping(value = "/emailValidation")

	public ResponseEntity<?> emailValidation( @RequestBody EmailValidationMapper emailValidationMapper,
			                                 HttpServletRequest request) {
		
	   boolean b = registrationService.validateEmailAddress(emailValidationMapper);
		
		return new ResponseEntity(b, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/setPassword")
	public ResponseEntity<?> setPassword( @RequestBody UserPasswordRq passwordRq,HttpServletRequest request) {
	  boolean b = registrationService.setPassword(passwordRq);
	  return new ResponseEntity(b, HttpStatus.OK);
	}


	@RequestMapping(value = "/forgotPassword")

	public ResponseEntity<String> forgetPassword(@RequestParam("email")String email,
			                                     HttpServletRequest request){
		registrationService.forgetPassword(email);
	 return new ResponseEntity<String>(HttpStatus.OK);
	 }
	
	@RequestMapping(value = "/changePassword")
	public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authorization,
			                                @RequestBody UserPasswordRq passwordRq,
			                                HttpServletRequest request)  {
	                                      
		
		
		 if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			 
	         String authToken = authorization.replace(TOKEN_PREFIX,"");

	  	passwordRq.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
	  	boolean b = registrationService.setPassword(passwordRq);
	     return new ResponseEntity(b, HttpStatus.OK);   
		 }
			
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		
		
		
		
	}
}
