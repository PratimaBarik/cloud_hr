package com.app.employeePortal.support.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.support.mapper.TicketRequestMapper;
import com.app.employeePortal.support.mapper.TicketResponseMapper;
import com.app.employeePortal.support.service.TicketService;

@RestController
@CrossOrigin(maxAge = 3600)

public class TicketController {
	@Autowired
	TicketService ticketService;
	@Autowired
	WebsiteRepository websiteRepository;
	
	@Autowired
    private TokenProvider jwtTokenUtil;
	
	@PostMapping("/api/v1/support/ticket/save")
	public ResponseEntity<?> createTicket(@RequestBody TicketRequestMapper ticketRequestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			ticketRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			ticketRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			
			TicketResponseMapper ticketResponseMapper = ticketService.createTicket(ticketRequestMapper);
			
			return new ResponseEntity<>(ticketResponseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/api/v1/support/ticket/{ticketId}")
	public ResponseEntity<?> getTicketByTicketId(@PathVariable("ticketId") String ticketId,
		@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

		TicketResponseMapper ticketResponseMapper = ticketService.getTicketByTicketId(ticketId);
		
		return new ResponseEntity<>(ticketResponseMapper, HttpStatus.OK);
	}

	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

}
	
	@GetMapping("/api/v1/support/ticket/{userId}/{pageNo}")
		public ResponseEntity<?> getTicketByUserId(@PathVariable("userId") String userId,
				@PathVariable("pageNo") int pageNo,@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TicketResponseMapper> ticketResponseMapperList = ticketService.getTicketByUserId(userId,pageNo, pageSize);
			return new ResponseEntity<>(ticketResponseMapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PostMapping("/api/v1/support/ticket/save/website")
	public ResponseEntity<?> createTicketThroughWebsite(@RequestBody TicketRequestMapper ticketRequestMapper,
			@RequestParam(value = "url", required = true) String url, @RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {

			ticketRequestMapper.setUserId(web.getUser_id());
			ticketRequestMapper.setOrgId(web.getOrgId());
			
			String id = ticketService.createTicketThroughWebsite(ticketRequestMapper);
			
			map.put("ID", id);
			map.put("message",
					"Thank you for Registering a ticket. We will shortly reachout to you !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@PutMapping("/api/v1/support/ticket/update/{ticketId}")

	public ResponseEntity<?> updateTicket(@PathVariable("ticketId") String ticketId,
			@RequestBody TicketRequestMapper ticketRequestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			ticketRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			ticketRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			TicketResponseMapper ticketResponseMapper = ticketService.updateTicket(ticketId,ticketRequestMapper);
			
			return new ResponseEntity<>(ticketResponseMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	

	@DeleteMapping("/api/v1/support/ticket/delete/{ticketId}")
	public ResponseEntity<?> deleteTicket(@PathVariable("ticketId") String ticketId,
										@RequestHeader("Authorization") String authorization ,
										HttpServletRequest request){
			  
			 if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				 ticketService.deleteTicket(ticketId);   
				 return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
			  } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);		  
	}
	
}
