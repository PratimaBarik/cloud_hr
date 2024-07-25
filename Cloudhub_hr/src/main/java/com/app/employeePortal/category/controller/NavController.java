package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.app.employeePortal.category.mapper.NavRequestMapper;
import com.app.employeePortal.category.mapper.NavResponseMapper;
import com.app.employeePortal.category.service.NavService;
import com.app.employeePortal.sector.mapper.SectorMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class NavController {
	@Autowired
	NavService navService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/nav/save")
	public ResponseEntity<?> createNav(@RequestBody NavRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(requestMapper.getNavName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = navService.checkNavNameInNavDetailsByOrgLevel(requestMapper.getNavName(), orgId);
				if (b == true) {
					map.put("sectorInd", b);
					map.put("message", "NavName can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

					NavResponseMapper paymentId = navService.createNav(requestMapper);

					return new ResponseEntity<>(paymentId, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide NavName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/nav/details/{navDetailId}")
	public ResponseEntity<?> getNavById(@PathVariable("navDetailId") int navDetailId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			NavResponseMapper mapper = navService.getNavById(navDetailId);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/nav/all/{orgId}")
	public ResponseEntity<?> getNavByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<NavResponseMapper> mapper = navService.getNavByOrgId(orgId);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/nav/update/{navDetailId}")
	public ResponseEntity<?> updateNav(@PathVariable("navDetailId") int navDetailId,
			@RequestBody NavRequestMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(requestMapper.getNavName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = navService.checkNavNameInNavDetailsByOrgLevel(requestMapper.getNavName(), orgId);
				if (b == true) {
					map.put("sectorInd", b);
					map.put("message", "NavName can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<NavResponseMapper>(navService.updateNav(navDetailId, requestMapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/nav/delete/{navDetailId}")
	public ResponseEntity<?> deleteNav(@PathVariable("navDetailId") int navDetailId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			navService.deleteNav(navDetailId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/nav/count/{orgId}")
	public ResponseEntity<?> getNavCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(navService.getNavCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
