 package com.app.employeePortal.registration.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
	
	
	

	@GetMapping(value = "/")
	
	public String welcome() {
		
	System.out.println("inside home controller .....");
	
	LocalDate localDate = LocalDate.now();
	
 return "Server running fine "+localDate ;

   }
	
	
	
	

	
}
