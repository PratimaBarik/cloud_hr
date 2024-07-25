package com.app.employeePortal.processApproval.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.processApproval.mapper.ProcessApprovalMapper;
import com.app.employeePortal.processApproval.mapper.ProcessApprovalViewMapper;
import com.app.employeePortal.processApproval.service.ProcessApprovalService;


@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1/approve")
public class ProcessApprovalController {

	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Autowired
	ProcessApprovalService processApprovalService;
	
	 @PostMapping("/save/processName/subProcessName")
	    public ResponseEntity<ProcessApprovalViewMapper> saveProcessApproval(@RequestBody ProcessApprovalMapper processApprovalMapper,
	    		@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	        return ResponseEntity.ok(processApprovalService.saveProcessApproval(processApprovalMapper));
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	 
	 @GetMapping("/{subProcessName}")
	    public ResponseEntity<ProcessApprovalViewMapper> getApproval(@PathVariable("subProcessName") String subProcessName,
	    		@RequestHeader("Authorization") String authorization,
	    		HttpServletRequest request) {
		 if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	        return ResponseEntity.ok(processApprovalService.getApproval(subProcessName));
		 }
		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  
	    }
	 
//	 @PostMapping("/processName/subProcessName")
//	    public ResponseEntity<?> ApproveProcess(@RequestBody String taskId,String userId ,
//	    		@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		 if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//	        	return ResponseEntity.ok(processApprovalService.ProcessApprove( taskId, userId));
//		 }
//		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	    }
}
