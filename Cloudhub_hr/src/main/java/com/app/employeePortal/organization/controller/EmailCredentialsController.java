package com.app.employeePortal.organization.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.organization.mapper.EmailCredentialsMapper;
import com.app.employeePortal.organization.service.EmailCredentialsService;

@RestController
@CrossOrigin(maxAge = 3600)
public class EmailCredentialsController {

	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Autowired
	EmailCredentialsService emailCredentialsService;
	
	@PostMapping("/api/v1/employee/email-credentials")
	public ResponseEntity<?> saveLeave( @RequestBody EmailCredentialsMapper emailCredentialsMapper,
			                     @RequestHeader("Authorization") String authorization
			                     ,HttpServletRequest request) throws Exception {
				
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			emailCredentialsMapper.setEmployeeId(loggedInUserId);
			emailCredentialsMapper.setOrganizationId(loggedInUserOrgId);
              String id = emailCredentialsService.saveEmailCredentials(emailCredentialsMapper);
  			return new ResponseEntity<>(id,HttpStatus.OK);

        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
	
	@GetMapping("/api/v1/employee/email-credentials")

	public ResponseEntity<?> getOrgLeaveRuleById(@RequestHeader("Authorization") String authorization,
			                                      HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

			EmailCredentialsMapper emailCredentialsMapper = emailCredentialsService.getEmailCredentialsByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(emailCredentialsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PutMapping("/api/v1/employee/email-credentials")

	public ResponseEntity<?> updateOrgLeave( @RequestBody EmailCredentialsMapper emailCredentialsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
        	String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			emailCredentialsMapper.setEmployeeId(loggedInUserId);
			emailCredentialsMapper.setOrganizationId(loggedInUserOrgId);
			
			EmailCredentialsMapper resultMapper =  emailCredentialsService.updateEmailCredentials(emailCredentialsMapper);
			return new ResponseEntity<EmailCredentialsMapper>(resultMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/employee/email-credentials/user")

	public List<EmailCredentialsMapper> getEmailCredentialsByUserId(@RequestHeader("Authorization") String authorization,
			                                      HttpServletRequest request) {
		List<EmailCredentialsMapper> emailCredentialsMapper = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
        	String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			emailCredentialsMapper = emailCredentialsService.getEmailCredentialsByUserId(userId);
			return emailCredentialsMapper;

		}
		return emailCredentialsMapper;

	}
	
	@PostMapping("/api/v1/employee/user/email-credentials")
	public ResponseEntity<?> saveEmail( @RequestBody EmailCredentialsMapper emailCredentialsMapper,
			                     @RequestHeader("Authorization") String authorization
			                     ,HttpServletRequest request) throws Exception {
				
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

			//String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			emailCredentialsMapper.setEmployeeId(loggedInUserId);
			//emailCredentialsMapper.setOrganizationId(loggedInUserOrgId);
              String id = emailCredentialsService.saveUserEmailCredentials(emailCredentialsMapper);
  			return new ResponseEntity<>(id,HttpStatus.OK);

        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
	
	@PutMapping("/api/v1/employee/email-credentials/user/default")

	public ResponseEntity<?> updateDefaultSetting( @RequestBody EmailCredentialsMapper emailCredentialsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
        	
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			emailCredentialsMapper.setEmployeeId(loggedInUserId);
			
			List<EmailCredentialsMapper> resultMapper =  emailCredentialsService.updateDefaultSetting(emailCredentialsMapper);
			return new ResponseEntity<List<EmailCredentialsMapper>>(resultMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
}
