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
import com.app.employeePortal.category.mapper.SupplierCategoryMapper;
import com.app.employeePortal.category.service.SupplierCategoryService;

@RestController
@CrossOrigin(maxAge = 3600)

public class SupplierCategoryController {
	@Autowired
	SupplierCategoryService supplierCategoryService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/supplierCategory")
	public ResponseEntity<?> createSupplierCategory(@RequestBody SupplierCategoryMapper mapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(mapper.getSupplierCatName())) {
				boolean b = supplierCategoryService.checkNameInSupplierCategory(mapper.getSupplierCatName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("supplierCategoryInd", b);
					map.put("message", "SupplierCategory can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					SupplierCategoryMapper Id = supplierCategoryService.saveSupplierCategory(mapper);
					return new ResponseEntity<>(Id, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a supplierCategory");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/supplierCategory/All")
	public ResponseEntity<?> getSupplierCategoryByOrgId(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<SupplierCategoryMapper> mapper = supplierCategoryService
					.getSupplierCategoryByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/supplierCategory/{supplierCategoryId}")
	public ResponseEntity<?> updateSupplierCategory(@PathVariable("supplierCategoryId") String supplierCategoryId,
			@RequestBody SupplierCategoryMapper mapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(mapper.getSupplierCatName())) {

				boolean b = supplierCategoryService.checkNameInSupplierCategoryInUpdate(mapper.getSupplierCatName(),
						jwtTokenUtil.getOrgIdFromToken(authToken),supplierCategoryId);
				if (b == true) {
					map.put("SupplierCategoryInd", b);
					map.put("message", "SupplierCategory can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<SupplierCategoryMapper>(supplierCategoryService.updateSupplierCategory(supplierCategoryId, mapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/supplierCategory/{supplierCategoryId}")
	public ResponseEntity<?> deleteSupplierCategory(@PathVariable("supplierCategoryId") String supplierCategoryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			supplierCategoryService.deleteSupplierCategory(supplierCategoryId, jwtTokenUtil.getUserIdFromToken(authToken));

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/supplierCategory/search/{name}")
	public ResponseEntity<?> getSupplierCategoryByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<SupplierCategoryMapper> mapper = supplierCategoryService.getSupplierCategoryByName(name,
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

	@GetMapping("/api/v1/supplierCategory/count/All")
	public ResponseEntity<?> getsupplierCategoryCountByOrgId(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity
					.ok(supplierCategoryService.getSupplierCategoryCountByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken)));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
