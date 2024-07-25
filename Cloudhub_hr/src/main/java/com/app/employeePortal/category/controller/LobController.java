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
import com.app.employeePortal.category.mapper.LobMapper;
import com.app.employeePortal.category.service.LobService;
import com.app.employeePortal.sector.mapper.SectorMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class LobController {
	@Autowired
	LobService lobService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/lob/save")
	public ResponseEntity<?> createLob(@RequestBody LobMapper lobMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(lobMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = lobService.checkNameInLobByOrgLevel(lobMapper.getName(), orgId);
				if (b == true) {
					map.put("lobInd", b);
					map.put("message", "Lob can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					lobMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					lobMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

					LobMapper paymentId = lobService.saveLob(lobMapper);

					return new ResponseEntity<>(paymentId, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide Lob !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/lob/all/{orgId}")
	public ResponseEntity<?> getLobMapperByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LobMapper> lobMapper = lobService.getLobMapperByOrgId(orgId);

			return new ResponseEntity<>(lobMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/lob/update/{lobDetailsId}")

	public ResponseEntity<?> updateLob(@PathVariable("lobDetailsId") String lobDetailsId,
			@RequestBody LobMapper lobMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			Map map = new HashMap();
			if (!StringUtils.isEmpty(lobMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = lobService.checkNameInLobByOrgLevel(lobMapper.getName(), orgId);
				if (b == true) {
					map.put("lobInd", b);
					map.put("message", "Lob can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			lobMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			lobMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<LobMapper>(lobService.updateLob(lobDetailsId, lobMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/lob/delete/{lobDetailsId}")
	public ResponseEntity<?> deleteLob(@PathVariable("lobDetailsId") String lobDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			lobService.deleteLob(lobDetailsId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/lob/search/{name}")
	public ResponseEntity<?> getLobByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<LobMapper> mapper = lobService.getLobByNameByOrgLevel(name,orgId);
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/lob/count/{orgId}")
	public ResponseEntity<?> getLobCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(lobService.getLobCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
