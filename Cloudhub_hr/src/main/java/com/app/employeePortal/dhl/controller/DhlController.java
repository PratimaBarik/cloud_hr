package com.app.employeePortal.dhl.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.app.employeePortal.dhl.service.DhlService;

public class DhlController {
	
	@Autowired
	DhlService dhlService;
	
	@GetMapping("/api/v1/dhl/save")
    public ResponseEntity<?> saveDhl( @RequestHeader("Authorization") String authorization,
    		HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            return ResponseEntity.ok(dhlService.saveDhl());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
