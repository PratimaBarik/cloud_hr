package com.app.employeePortal.features.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.features.mapper.FeaturesMapper;
import com.app.employeePortal.features.service.FeaturesService;

@RestController
@CrossOrigin(maxAge = 3600)
public class FeaturesController {
	
	@Autowired
	FeaturesService featureService;
//	@Autowired
//	private TokenProvider jwtTokenUtil;
	
	@PostMapping("/api/v1/advance/feature")
	public String createAdvanceFeature(
			@RequestBody FeaturesMapper featuresMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		String id = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			id = featureService.saveAdvanceFeature(featuresMapper);
		}

		return id;

	}
	
	
	@GetMapping("/api/v1/advance/feature/{orgId}")
	public List<FeaturesMapper> getAdavnceFeatureByOrgId(
			@PathVariable("orgId") String orgId,
			 @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		List<FeaturesMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {			
			mapperList = featureService.getAdavnceFeatureByOrgId(orgId);

		}

		return mapperList;

	}

	
	@PutMapping("/api/v1/advance/feature")
	public ResponseEntity<?> updateAdavnceFeature(
			@RequestBody FeaturesMapper featuresMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws Exception{

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			FeaturesMapper featuresMapperr = featureService.updateAdavnceFeature(featuresMapper);
			return new ResponseEntity<FeaturesMapper>(featuresMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	@PostMapping("/api/v1/job")
	public String createJobDetails(
			 @RequestBody FeaturesMapper featuresMapper,
			 @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		String id = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {			
			id = featureService.addJobPublish(featuresMapper);

		}

		return id;

	}
	
	@GetMapping("/api/v1/Job/publish/{orgId}")
	public FeaturesMapper getJobPublishDetails(
			@PathVariable("orgId") String orgId,
			 @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		FeaturesMapper featuresMapper  = new FeaturesMapper();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {			
			featuresMapper = featureService.getJobpublishByOrgId(orgId);

		}

		return featuresMapper;

	}
	
}
