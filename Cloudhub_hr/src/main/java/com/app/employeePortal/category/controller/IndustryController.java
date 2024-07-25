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
import com.app.employeePortal.category.mapper.IndustryMapper;
import com.app.employeePortal.category.service.IndustryService;

@RestController
@CrossOrigin(maxAge = 3600)

public class IndustryController {
	@Autowired
	IndustryService industryService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/industry/save")
	public ResponseEntity<?> createIndustry(@RequestBody IndustryMapper industryMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			industryMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			industryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(industryMapper.getName())) {
				boolean b = industryService.checkNameInIndustry(industryMapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("industryInd", b);
					map.put("message", "Industry can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					IndustryMapper paymentId = industryService.saveIndustry(industryMapper);
					return new ResponseEntity<>(paymentId, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a role");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/industry/all/{orgId}")
	public ResponseEntity<?> getIndustryMapperByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<IndustryMapper> industryMapper = industryService.getIndustryMapperByOrgId(orgId);

			return new ResponseEntity<>(industryMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/industry/update/{industryId}")

	public ResponseEntity<?> updateIndustry(@PathVariable("industryId") String industryId,
			@RequestBody IndustryMapper industryMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			industryMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			industryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(industryMapper.getName())) {
				boolean b = industryService.checkNameInIndustry(industryMapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("IndustryInd", b);
					map.put("message", "Industry can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<IndustryMapper>(industryService.updateIndustry(industryId, industryMapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/industry/delete/{industryId}")
	public ResponseEntity<?> deleteIndustry(@PathVariable("industryId") String industryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			industryService.deleteIndustry(industryId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/industry/search/{name}")
	public ResponseEntity<?> getIndustryByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<IndustryMapper> mapper = industryService.getIndustryByName(name, orgId);
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/industry/count/{orgId}")
	public ResponseEntity<?> getIndustryCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(industryService.getIndustryCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
