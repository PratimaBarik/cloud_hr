package com.app.employeePortal.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.api.service.CurrencyExchangeService;


@RestController
@CrossOrigin(maxAge = 3600)
public class ApiController {
	
	@Autowired
	CurrencyExchangeService service;
	@GetMapping("/saveCurrency")
	public void saveCurrency(){
		
		service.saveToCurrencyExchange();		
	
     }

}
