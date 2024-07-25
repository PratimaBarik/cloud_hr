package com.app.employeePortal.Team.controller;

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

import com.app.employeePortal.Team.mapper.TeamMapper;
import com.app.employeePortal.Team.mapper.TeamResponseMapper;
import com.app.employeePortal.Team.service.TeamService;
import com.app.employeePortal.authentication.config.TokenProvider;

@RestController
@CrossOrigin(maxAge = 3600)

public class TeamController {
	@Autowired
	TeamService teamService;
	
	@Autowired
    private TokenProvider jwtTokenUtil;
	
	@PostMapping("/api/v1/team")
	public ResponseEntity<?> createTeam(@RequestBody TeamMapper teamMapper,
			@RequestHeader("Authorization") String authorization) {


		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			teamMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			teamMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			
			TeamResponseMapper teamId = teamService.saveTeam(teamMapper);
			
			return new ResponseEntity<>(teamId, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/api/v1/team/{teamId}")
	public ResponseEntity<?> getTeamByTeamId(@PathVariable("teamId") String teamId,
		@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

		TeamResponseMapper teamMapper = teamService.getTeamByTeamId(teamId);
		
		return new ResponseEntity<>(teamMapper, HttpStatus.OK);
	}

	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

}
	
	@GetMapping("/api/v1/team/user/{userId}")
		public ResponseEntity<?> getTeamByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TeamResponseMapper> teamMapper = teamService.getTeamByUserId(userId);
			
			return new ResponseEntity<>(teamMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	
	@PutMapping("/api/v1/team/{teamId}")

	public ResponseEntity<?> updateSource(@PathVariable("teamId") String teamId,
			@RequestBody TeamMapper teamMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			teamMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			teamMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<TeamResponseMapper>(teamService.updateTeam(teamId, teamMapper), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	@DeleteMapping("/api/v1/team/{teamId}")
	public ResponseEntity<?> deleteSequence(@PathVariable("teamId") String teamId,
										@RequestHeader("Authorization") String authorization ,
										HttpServletRequest request){
			  
			 if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				 String authToken = authorization.replace(TOKEN_PREFIX, "");
					
				 teamService.deleteTeam(teamId); 
			  
			  return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
			  } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);		  
	}
	
	@GetMapping("/api/v1/team/teamLead/{teamLead}")
	public ResponseEntity<?> getTeamByTeamLead(@PathVariable("teamLead") String teamLead,
		@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

		List<TeamResponseMapper> teamMapper = teamService.getTeamByTeamLead(teamLead);
		
		return new ResponseEntity<>(teamMapper, HttpStatus.OK);
	}

	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

}
}
