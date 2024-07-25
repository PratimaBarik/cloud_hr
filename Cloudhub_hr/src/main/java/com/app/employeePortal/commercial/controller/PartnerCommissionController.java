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
import com.app.employeePortal.commercial.mapper.PartnerCommissionMapper;
import com.app.employeePortal.commercial.service.PartnerCommissionService;

@RestController
@CrossOrigin(maxAge = 3600)

public class PartnerCommissionController {

	@Autowired
	PartnerCommissionService partnerCommissionService;
	
	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@PostMapping("/api/v1/partner/commission")
	public ResponseEntity<?> createPartnerCommission(@RequestBody PartnerCommissionMapper partnerCommissionMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			//CustomerCommissionMapper customerCommissionMapper1 = new CustomerCommissionMapper();
			//customerCommissionMapper1.setCustomerId(JwtTokenUtil.getCustomerIdFromToken(authToken));
			partnerCommissionMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			partnerCommissionMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			String partnerCommissionId = partnerCommissionService.savePartnerCommission(partnerCommissionMapper);

			return new ResponseEntity<>(partnerCommissionId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/partner/commission/{partnerId}")
	public List<PartnerCommissionMapper> getPartnerCommissionListByPartnerId(@PathVariable("partnerId") String partnerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<PartnerCommissionMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			
			mapperList = partnerCommissionService.getPartnerCommissionListByPartnerId(partnerId);

		}

		return mapperList;

	}
		
	
}
