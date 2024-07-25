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
import com.app.employeePortal.category.mapper.MachinaryLocationCellMapper;
import com.app.employeePortal.category.mapper.MachinaryLocationMapper;
import com.app.employeePortal.category.mapper.MachinaryMapper;
import com.app.employeePortal.category.service.MachinaryService;

@RestController
@CrossOrigin(maxAge = 3600)

public class MachinaryController {
	@Autowired
	MachinaryService machinaryService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/machinary")
	public ResponseEntity<?> createMachinary(@RequestBody MachinaryMapper mapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(mapper.getName())) {
				boolean b = machinaryService.checkNameInMachinary(mapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("MachinaryInd", b);
					map.put("message", "Machinary can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					MachinaryMapper Id = machinaryService.saveMachinary(mapper);
					return new ResponseEntity<>(Id, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a Machinary");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/machinary/All")
	public ResponseEntity<?> getMachinaryByOrgId(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<MachinaryMapper> mapper = machinaryService
					.getMachinaryByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/machinary/{machinaryId}")

	public ResponseEntity<?> updateMachinary(@PathVariable("machinaryId") String machinaryId,
			@RequestBody MachinaryMapper mapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(mapper.getName())) {

				boolean b = machinaryService.checkNameInMachinaryInUpdate(mapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken), machinaryId);
				if (b == true) {
					map.put("MachinaryInd", b);
					map.put("message", "Machinary can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<MachinaryMapper>(machinaryService.updateMachinary(machinaryId, mapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/machinary/{machinaryId}")
	public ResponseEntity<?> deleteMachinary(@PathVariable("machinaryId") String machinaryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			machinaryService.deleteMachinary(machinaryId, jwtTokenUtil.getUserIdFromToken(authToken));

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/machinary/search/{name}")
	public ResponseEntity<?> getMachinaryByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<MachinaryMapper> mapper = machinaryService.getMachinaryByName(name,
					jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/machinary/count/All")
	public ResponseEntity<?> getMachinaryCountByOrgId(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			return ResponseEntity
					.ok(machinaryService.getMachinaryCountByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken)));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/machinary/location")
	public ResponseEntity<?> createMachinaryLocation(@RequestBody MachinaryLocationMapper mapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			MachinaryLocationMapper Id = machinaryService.saveMachinaryLocation(mapper);
			return new ResponseEntity<>(Id, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/api/v1/machinary/location/{locationId}")
	public ResponseEntity<?> getMachinaryLocationByOrgId(@PathVariable("locationId") String locationId,@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<MachinaryLocationMapper> mapper = machinaryService
					.getMachinaryLocationByLocationId(locationId);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PostMapping("/api/v1/machinary/location/cell")
	public ResponseEntity<?> createMachinaryLocationCell(@RequestBody MachinaryLocationCellMapper mapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			MachinaryLocationMapper Id = machinaryService.saveMachinaryLocationCell(mapper);
			return new ResponseEntity<>(Id, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
