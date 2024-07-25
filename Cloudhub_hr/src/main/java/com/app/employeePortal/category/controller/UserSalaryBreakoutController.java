package com.app.employeePortal.category.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.UserSalaryBreakoutReqMapper;
import com.app.employeePortal.category.mapper.UserSalaryBreakoutResponseMapper;
import com.app.employeePortal.category.service.UserSalaryBreakoutService;

@RestController
@CrossOrigin(maxAge = 3600)

public class UserSalaryBreakoutController {
	@Autowired
	UserSalaryBreakoutService userSalaryBreakoutService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/userSalaryBreakout")
	public ResponseEntity<?> createUserSalaryBreakout(@RequestBody UserSalaryBreakoutReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			UserSalaryBreakoutResponseMapper responseMapper = userSalaryBreakoutService
					.saveUserSalaryBreakout(requestMapper);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/userSalaryBreakout/list/{roleTypeId}")
	public ResponseEntity<?> getUserSalaryBreakoutByOrgId(@PathVariable("roleTypeId") String roleTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			return new ResponseEntity<>(userSalaryBreakoutService
					.getUserSalaryBreakoutByRoleTypeId(roleTypeId), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/userSalaryBreakout/{userSalaryBreakoutId}")

	public ResponseEntity<?> updateUserSalaryBreakout(
			@PathVariable("userSalaryBreakoutId") String userSalaryBreakoutId,
			@RequestBody UserSalaryBreakoutReqMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<UserSalaryBreakoutResponseMapper>(
					userSalaryBreakoutService.updateUserSalaryBreakout(userSalaryBreakoutId, requestMapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/userSalaryBreakout/{userSalaryBreakoutId}")
	public ResponseEntity<?> deleteUserSalaryBreakout(
			@PathVariable("userSalaryBreakoutId") String userSalaryBreakoutId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			userSalaryBreakoutService.deleteUserSalaryBreakout(userSalaryBreakoutId, userId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/userSalaryBreakout/count/{orgId}")
    public ResponseEntity<?> getUserSalaryBreakoutCountByOrgId(@RequestHeader("Authorization") String authorization,
                                                                     @PathVariable("orgId") String orgId) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");

            return ResponseEntity.ok(userSalaryBreakoutService.getUserSalaryBreakoutCountByOrgId(orgId));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
