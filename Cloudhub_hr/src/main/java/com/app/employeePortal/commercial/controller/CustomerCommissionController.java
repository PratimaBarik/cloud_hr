package com.app.employeePortal.commercial.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.commercial.mapper.CustomerCommissionMapper;
import com.app.employeePortal.commercial.service.CustomerCommissionService;

@RestController
@CrossOrigin(maxAge = 3600)



public class CustomerCommissionController {
	
	

	@Autowired
	CustomerCommissionService customerCommissionService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	
	
	
	@PostMapping("/api/v1/customer/commission")
	public ResponseEntity<?> createCustomerCommission(@RequestBody CustomerCommissionMapper customerCommissionMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			//CustomerCommissionMapper customerCommissionMapper1 = new CustomerCommissionMapper();
			//customerCommissionMapper1.setCustomerId(JwtTokenUtil.getCustomerIdFromToken(authToken));
			customerCommissionMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			customerCommissionMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			String customerCommissionId = customerCommissionService.saveCustomerCommission(customerCommissionMapper);

			return new ResponseEntity<String>(customerCommissionId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
	
	@GetMapping("/api/v1/customer/commission/{customerId}")
	public List<CustomerCommissionMapper> getCustomerCommissionListByCustomerId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<CustomerCommissionMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			
			mapperList = customerCommissionService.getCustomerCommissionListByCustomerId(customerId);

		}

		return mapperList;

	}
  
	

	
}
