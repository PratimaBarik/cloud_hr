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
import com.app.employeePortal.category.mapper.ShipByMapper;
import com.app.employeePortal.category.service.ShipByService;
import com.app.employeePortal.sector.mapper.SectorMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class ShipByController {
	@Autowired
	ShipByService shipByService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/shipBy")
	public ResponseEntity<?> createShipBy(@RequestBody ShipByMapper shipByMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			shipByMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			shipByMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(shipByMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = shipByService.checkNameInShipByByOrgLevel(shipByMapper.getName(), orgId);
				if (b == true) {
					map.put("shipByInd", b);
					map.put("message", "ShipByName can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {

					ShipByMapper shipByid = shipByService.saveShipBy(shipByMapper);

					return new ResponseEntity<>(shipByid, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide ShipByName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/shipBy/{shipById}")
	public ResponseEntity<?> getShipByByShipById(@PathVariable("shipById") String shipById,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ShipByMapper shipByMapper = shipByService.getShipByByShipById(shipById);

			return new ResponseEntity<>(shipByMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/shipBy/All/{orgId}")
	public ResponseEntity<?> getShipByByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ShipByMapper> shipByMapper = shipByService.getShipByByOrgId(orgId);

			return new ResponseEntity<>(shipByMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/shipBy/{shipById}")

	public ResponseEntity<?> updateShipBy(@PathVariable("shipById") String shipById,
			@RequestBody ShipByMapper shipByMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			shipByMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			shipByMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(shipByMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = shipByService.checkNameInShipByByOrgLevel(shipByMapper.getName(), orgId);
				if (b == true) {
					map.put("shipByInd", b);
					map.put("message", "ShipByName can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<ShipByMapper>(shipByService.updateShipBy(shipById, shipByMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/shipBy/{shipById}")
	public ResponseEntity<?> deleteShipBy(@PathVariable("shipById") String shipById,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			shipByService.deleteShipBy(shipById, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/shipBy/search/{name}")
	public ResponseEntity<?> getShipByByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<ShipByMapper> shipByMapper = shipByService.getShipByByNameByOrgLevel(name,orgId);
			if (null != shipByMapper && !shipByMapper.isEmpty()) {
				return new ResponseEntity<>(shipByMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/shipBy/count/{orgId}")
	public ResponseEntity<?> getShipByCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(shipByService.getShipByCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
