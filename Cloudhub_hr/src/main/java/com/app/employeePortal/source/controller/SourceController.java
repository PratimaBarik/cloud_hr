package com.app.employeePortal.source.controller;

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
import com.app.employeePortal.source.mapper.SourceMapper;
import com.app.employeePortal.source.service.SourceService;

@RestController
@CrossOrigin(maxAge = 3600)

public class SourceController {
	@Autowired
	SourceService sourceService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/source")
	public ResponseEntity<?> createSource(@RequestBody SourceMapper sourceMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			Map map = new HashMap();
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			boolean b = sourceService.checkSourceByNameByOrgLevel(sourceMapper.getName().trim(), orgId);
			if (b == true) {
				map.put("SourceInd", b);
				map.put("message", "Source can not be created as same name already exists!!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			} else {
				sourceMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				sourceMapper.setOrgId(orgId);
				SourceMapper sourceid = sourceService.saveSource(sourceMapper);

				return new ResponseEntity<>(sourceid, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/source/rule/{sourceId}")
	public ResponseEntity<?> getSourceBySourceId(@PathVariable("sourceId") String sourceId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			SourceMapper sourceMapper = sourceService.getSourceBySourceId(sourceId);

			return new ResponseEntity<>(sourceMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/source/{orgId}")
	public ResponseEntity<?> getSourceByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<SourceMapper> sourceMapper = sourceService.getSourceByOrgId(orgId);

			return new ResponseEntity<>(sourceMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/source/{sourceId}")

	public ResponseEntity<?> updateSource(@PathVariable("sourceId") String sourceId,
			@RequestBody SourceMapper sourceMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			sourceMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			sourceMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(sourceMapper.getName())) {
				Map map = new HashMap<>();
				boolean b = sourceService.checkSourceByNameByOrgLevel(sourceMapper.getName(), orgId);
				if (b == true) {
					map.put("sourceInd", b);
					map.put("message", "source can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<SourceMapper>(sourceService.updateSource(sourceId, sourceMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/source/{sourceId}")
	public ResponseEntity<?> deleteSequence(@PathVariable("sourceId") String sourceId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			sourceService.deleteSource(sourceId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/source/search/{name}")
	public ResponseEntity<?> getSourceByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<SourceMapper> sourceMapper = sourceService.getSourceByNameAndOrgId(name,orgId);
			if (null != sourceMapper && !sourceMapper.isEmpty()) {
				return new ResponseEntity<>(sourceMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/source/count/{orgId}")
	public ResponseEntity<?> getSourceCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(sourceService.getSourceCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
