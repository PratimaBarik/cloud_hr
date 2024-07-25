package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.SecreteKeyMapper;
import com.app.employeePortal.category.mapper.ServiceLineRespMapper;
import com.app.employeePortal.category.service.SecreteKeyService;

@RestController
@CrossOrigin(maxAge = 3600)

public class SecreteKeyController {
	@Autowired
	SecreteKeyService secreteKeyService;

	@PostMapping("/api/v1/secretekey")
	public ResponseEntity<?> createSecreteKey(@RequestBody SecreteKeyMapper mapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			SecreteKeyMapper responseMapper = secreteKeyService.createSecreteKey(mapper);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/api/v1/secretekey/{secretekeyId}")
	public ResponseEntity<?> updateSecreteKey(@PathVariable("secretekeyId") String secretekeyId,
			@RequestBody SecreteKeyMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			return new ResponseEntity<SecreteKeyMapper>(
					secreteKeyService.updateSecreteKey(secretekeyId, requestMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
