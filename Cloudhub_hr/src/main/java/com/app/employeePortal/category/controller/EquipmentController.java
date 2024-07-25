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
import com.app.employeePortal.category.mapper.EquipmentMapper;
import com.app.employeePortal.category.service.EquipmentService;

@RestController
@CrossOrigin(maxAge = 3600)

public class EquipmentController {
	@Autowired
	EquipmentService equipmentService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/equipment")
	public ResponseEntity<?> createEquipment(@RequestBody EquipmentMapper mapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(mapper.getName())) {
				boolean b = equipmentService.checkNameInEquipment(mapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("EquipmentInd", b);
					map.put("message", "Equipment can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					EquipmentMapper Id = equipmentService.saveEquipment(mapper);
					return new ResponseEntity<>(Id, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a Equipment");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/equipment/All")
	public ResponseEntity<?> getEquipmentByOrgId(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<EquipmentMapper> mapper = equipmentService
					.getEquipmentByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/equipment/{equipmentId}")
	public ResponseEntity<?> updateEquipment(@PathVariable("equipmentId") String equipmentId,
			@RequestBody EquipmentMapper mapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(mapper.getName())) {

				boolean b = equipmentService.checkNameInEquipmentInUpdate(mapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken),equipmentId);
				if (b == true) {
					map.put("EquipmentInd", b);
					map.put("message", "Equipment can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<EquipmentMapper>(equipmentService.updateEquipment(equipmentId, mapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/equipment/{equipmentId}")
	public ResponseEntity<?> deleteEquipment(@PathVariable("equipmentId") String equipmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			equipmentService.deleteEquipment(equipmentId, jwtTokenUtil.getUserIdFromToken(authToken));

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/equipment/search/{name}")
	public ResponseEntity<?> getEquipmentByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<EquipmentMapper> mapper = equipmentService.getEquipmentByName(name,
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

	@GetMapping("/api/v1/equipment/count/All")
	public ResponseEntity<?> getEquipmentCountByOrgId(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity
					.ok(equipmentService.getEquipmentCountByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken)));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
