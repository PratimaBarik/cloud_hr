package com.app.employeePortal.category.controller;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.ShipperCategoryMapper;
import com.app.employeePortal.category.mapper.ShipperCategoryMapper;
import com.app.employeePortal.category.service.ShipperCategoryService;
import com.app.employeePortal.category.service.ShipperCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

@RestController
@CrossOrigin(maxAge = 3600)

public class ShipperCategoryController {
	@Autowired
	ShipperCategoryService shipperCategoryService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/shipperCategory")
	public ResponseEntity<?> createShipperCategory(@RequestBody ShipperCategoryMapper mapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(mapper.getShipperCatName())) {
				boolean b = shipperCategoryService.checkNameInShipperCategory(mapper.getShipperCatName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("ShipperCategoryInd", b);
					map.put("message", "ShipperCategory can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					ShipperCategoryMapper Id = shipperCategoryService.saveShipperCategory(mapper);
					return new ResponseEntity<>(Id, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a ShipperCategory");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/shipperCategory/All")
	public ResponseEntity<?> getShipperCategoryByOrgId(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<ShipperCategoryMapper> mapper = shipperCategoryService
					.getShipperCategoryByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/shipperCategory/{shipperCategoryId}")
	public ResponseEntity<?> updateShipperCategory(@PathVariable("shipperCategoryId") String shipperCategoryId,
			@RequestBody ShipperCategoryMapper mapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(mapper.getShipperCatName())) {

				boolean b = shipperCategoryService.checkNameInShipperCategoryInUpdate(mapper.getShipperCatName(),
						jwtTokenUtil.getOrgIdFromToken(authToken),shipperCategoryId);
				if (b == true) {
					map.put("ShipperCategoryInd", b);
					map.put("message", "ShipperCategory can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<ShipperCategoryMapper>(shipperCategoryService.updateShipperCategory(shipperCategoryId, mapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/shipperCategory/{shipperCategoryId}")
	public ResponseEntity<?> deleteShipperCategory(@PathVariable("shipperCategoryId") String shipperCategoryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			shipperCategoryService.deleteShipperCategory(shipperCategoryId, jwtTokenUtil.getUserIdFromToken(authToken));

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/shipperCategory/search/{name}")
	public ResponseEntity<?> getShipperCategoryByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<ShipperCategoryMapper> mapper = shipperCategoryService.getShipperCategoryByName(name,
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

	@GetMapping("/api/v1/shipperCategory/count/All")
	public ResponseEntity<?> getShipperCategoryCountByOrgId(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity
					.ok(shipperCategoryService.getShipperCategoryCountByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken)));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
