package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.app.employeePortal.category.mapper.CurrencyConversionRequestMapper;
import com.app.employeePortal.category.mapper.CurrencyConversionResponseMapper;
import com.app.employeePortal.category.service.CurrencyConversionService;

@RestController
@CrossOrigin(maxAge = 3600)

public class CurrencyConversionController {
	@Autowired
	CurrencyConversionService currencyConversionService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/currencyConversion/save")
	public ResponseEntity<?> createCurrencyConversion(@RequestBody CurrencyConversionRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			CurrencyConversionResponseMapper responseMapper = currencyConversionService
					.SaveCurrencyConversion(requestMapper);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/currencyConversion/{currencyConversionId}")
	public ResponseEntity<?> getCurrencyConversionByCurrencyConversionId(
			@PathVariable("currencyConversionId") String currencyConversionId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CurrencyConversionResponseMapper responseMapper = currencyConversionService
					.getCurrencyConversionById(currencyConversionId);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/currencyConversion/All/{orgId}")
	public ResponseEntity<?> getCurrencyConversionByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CurrencyConversionResponseMapper> responseMapper = currencyConversionService
					.getCurrencyConversionByOrgId(orgId);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

//	@PutMapping("/api/v1/currencyConversion/{currencyConversionId}")
//
//	public ResponseEntity<?> updateCurrencyConversion(
//			@PathVariable("currencyConversionId") String currencyConversionId,
//			@RequestBody CurrencyConversionRequestMapper requestMapper, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
//
//			return new ResponseEntity<>(
//					currencyConversionService.updateCurrencyConversion(currencyConversionId, requestMapper),
//					HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}
//
//	@DeleteMapping("/api/v1/performanceManagement/{performanceManagementId}")
//	public ResponseEntity<?> deletePerformanceManagement(
//			@PathVariable("performanceManagementId") String performanceManagementId,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
//			performanceManagementService.deletePerformanceManagement(performanceManagementId, userId);
//
//			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}

}
