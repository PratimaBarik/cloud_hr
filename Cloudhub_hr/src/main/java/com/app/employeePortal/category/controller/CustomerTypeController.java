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
import com.app.employeePortal.category.mapper.CustomerTypeMapper;
import com.app.employeePortal.category.service.CustomerTypeService;
import com.app.employeePortal.sector.mapper.SectorMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class CustomerTypeController {
	@Autowired
	CustomerTypeService customerTypeService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/customerType")
	public ResponseEntity<?> createCustomerType(@RequestBody CustomerTypeMapper customerTypeMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(customerTypeMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = customerTypeService.checkNameInCustomerTypeByOrgLevel(customerTypeMapper.getName(), orgId);
				if (b == true) {
					map.put("CustomertypeInd", b);
					map.put("message", "Customertype can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {

					customerTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					customerTypeMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

					CustomerTypeMapper customerTypeId = customerTypeService.saveCustomerType(customerTypeMapper);
					return new ResponseEntity<>(customerTypeId, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide Customertype !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/catagory/customerType/{customerTypeId}")
	public ResponseEntity<?> getCustomerTypeByCustomerTypeId(@PathVariable("customerTypeId") String customerTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CustomerTypeMapper customerTypeMapper = customerTypeService.getCustomerTypeByCustomerTypeId(customerTypeId);

			return new ResponseEntity<>(customerTypeMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customerType/{orgId}")
	public ResponseEntity<?> getCustomerTypeByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CustomerTypeMapper> customerTypeMapper = customerTypeService.getCustomerTypeByOrgId(orgId);

			return new ResponseEntity<>(customerTypeMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/customerType/{customerTypeId}")

	public ResponseEntity<?> updateCustomerType(@PathVariable("customerTypeId") String customerTypeId,
			@RequestBody CustomerTypeMapper customerTypeMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			customerTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			customerTypeMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			if (!StringUtils.isEmpty(customerTypeMapper.getName())) {
				Map map = new HashMap<>();
				boolean b = customerTypeService.checkNameInCustomerTypeByOrgLevel(customerTypeMapper.getName(), orgId);
				if (b == true) {
					map.put("customerTypeInd", b);
					map.put("message", "CustomerType can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

			return new ResponseEntity<CustomerTypeMapper>(
					customerTypeService.updateCustomerType(customerTypeId, customerTypeMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/customerType/{customerTypeId}")
	public ResponseEntity<?> deleteShipBy(@PathVariable("customerTypeId") String customerTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			customerTypeService.deleteCustomerType(customerTypeId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customerType/search/{name}")
	public ResponseEntity<?> getCustomerTypeByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CustomerTypeMapper> mapper = customerTypeService.getCustomerTypeByNameAndOrgId(name, orgId);
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customerType/count/{orgId}")
	public ResponseEntity<?> getCustomerTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(customerTypeService.getCustomerTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
