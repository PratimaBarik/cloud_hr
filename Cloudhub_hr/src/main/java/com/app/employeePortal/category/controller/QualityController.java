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
import com.app.employeePortal.category.mapper.QualityMapper;
import com.app.employeePortal.category.service.QualityService;

@RestController
@CrossOrigin(maxAge = 3600)

public class QualityController {
	@Autowired
	QualityService qualityService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/quality")
	public ResponseEntity<?> createQuality(@RequestBody QualityMapper mapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

//			if (!StringUtils.isEmpty(mapper.getQualityName())) {
//				boolean b = qualityService.checkQualityNameInQuality(mapper.getQualityName(),
//						jwtTokenUtil.getOrgIdFromToken(authToken));
//				if (b == true) {
//					System.out.println("true");
//					map.put("qualityInd", b);
//					map.put("message", "Quality can not be created as same name already exists!!!");
//					return new ResponseEntity<>(map, HttpStatus.OK);
//				} else {
//					QualityMapper Id = qualityService.saveQuality(mapper);
//					return new ResponseEntity<>(Id, HttpStatus.OK);
//				}
//			} else {
//				map.put("message", "please provide a Quality");
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}

			QualityMapper Id = qualityService.saveQuality(mapper);
			return new ResponseEntity<>(Id, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/quality/All")
	public ResponseEntity<?> getQualityByOrgId(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<QualityMapper> mapper = qualityService.getQualityByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/quality/{qualityId}")

	public ResponseEntity<?> updateQuality(@PathVariable("qualityId") String qualityId,
			@RequestBody QualityMapper mapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<QualityMapper>(qualityService.updateQuality(qualityId, mapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/quality/{qualityId}")
	public ResponseEntity<?> deleteQuality(@PathVariable("qualityId") String qualityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			qualityService.deleteQuality(qualityId, jwtTokenUtil.getUserIdFromToken(authToken));

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@GetMapping("/api/v1/quality/search/{qualityName}")
//	public ResponseEntity<?> getQualityByName(@PathVariable("qualityName") String qualityName,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		Map map = new HashMap();
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			List<QualityMapper> mapper = qualityService.getQualityByName(qualityName,
//					jwtTokenUtil.getOrgIdFromToken(authToken));
//			if (null != mapper && !mapper.isEmpty()) {
//				return new ResponseEntity<>(mapper, HttpStatus.OK);
//			} else {
//				map.put("message", " No Records Found !!!");
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}

	@GetMapping("/api/v1/quality/count/All")
	public ResponseEntity<?> getQualityCountByOrgId(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(qualityService.getQualityCountByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken)));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
