package com.app.employeePortal.sequence.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.sequence.mapper.SequenceMapper;
import com.app.employeePortal.sequence.mapper.SequenceRuleMapper;
import com.app.employeePortal.sequence.service.SequenceService;

@RestController
@CrossOrigin(maxAge = 3600)

public class SequenceController {
	@Autowired
	SequenceService sequenceService;
	
	@Autowired
    private TokenProvider jwtTokenUtil;
	
	@PostMapping("/api/v1/sequence")
	public String createSequence(@RequestBody SequenceMapper sequenceMapper,
			@RequestHeader("Authorization") String authorization) {

		String sequenceId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			sequenceMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			sequenceMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			sequenceId = sequenceService.saveSequence(sequenceMapper);

		}

		return sequenceId;
	}
	
	@GetMapping("/api/v1/sequence/{orgId}")
		public ResponseEntity<?> getSequenceByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<SequenceMapper> sequenceMapper = sequenceService.getSequenceByOrgId(orgId);
			
			return new ResponseEntity<>(sequenceMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/sequence/rule")
	public ResponseEntity<?> createSequenceRule(@RequestBody SequenceRuleMapper sequenceRuleMapper,
			@RequestHeader("Authorization") String authorization) {

		//String sequenceId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			sequenceRuleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			sequenceRuleMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			SequenceRuleMapper SequenceRuleMapper1 = sequenceService.createSequenceRule(sequenceRuleMapper);
			return new ResponseEntity<>(SequenceRuleMapper1, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	

	@GetMapping("/api/v1/sequence/rule/{SequenceId}")
		public ResponseEntity<?> getSequenceRuleBySequenceId(@PathVariable("SequenceId") String SequenceId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			SequenceRuleMapper sequenceMapper = sequenceService.getSequenceRuleBySequenceId(SequenceId);
			
			return new ResponseEntity<>(sequenceMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PutMapping("/api/v1/sequence/{sequenceId}")

	public ResponseEntity<?> updateSequence(@PathVariable("sequenceId") String sequenceId,
			@RequestBody SequenceMapper sequenceMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		SequenceMapper sequenceMapper1 = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			sequenceMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			sequenceMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			sequenceMapper1 = sequenceService.updateSequence(sequenceId, sequenceMapper);

			return new ResponseEntity<SequenceMapper>(sequenceMapper1, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	@DeleteMapping("/api/v1/sequence/{sequenceId}")
	public ResponseEntity<?> deleteSequence(@PathVariable("sequenceId") String sequenceId,
										@RequestHeader("Authorization") String authorization ,
										HttpServletRequest request){
			  
			 if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			  
			 sequenceService.deleteSequence(sequenceId); 
			  
			  return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
			  } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);		  
	}
}
