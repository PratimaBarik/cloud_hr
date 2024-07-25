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
import com.app.employeePortal.category.mapper.PaymentMapper;
import com.app.employeePortal.category.service.PaymentService;
import com.app.employeePortal.sector.mapper.SectorMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class PaymentController {
	@Autowired
	PaymentService paymentService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/paymentCategory/save")
	public ResponseEntity<?> createPayment(@RequestBody PaymentMapper paymentMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(paymentMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = paymentService.checkNameInPaymentByOrgLevel(paymentMapper.getName(), orgId);
				if (b == true) {
					map.put("paymentInd", b);
					map.put("message", "PaymentName can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					paymentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					paymentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

					PaymentMapper paymentId = paymentService.savePayment(paymentMapper);

					return new ResponseEntity<>(paymentId, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide PaymentName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/details/{paymentCatagoryId}")
	public ResponseEntity<?> getPaymentCatagoryBYId(@PathVariable("paymentId") String paymentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			PaymentMapper PaymentMapper = paymentService.getPaymentCatagoryById(paymentId);

			return new ResponseEntity<>(PaymentMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/paymentCategory/all/{orgId}")
	public ResponseEntity<?> getpaymentCategoryByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<PaymentMapper> PaymentMapper = paymentService.getPaymentCategoryByOrgId(orgId);

			return new ResponseEntity<>(PaymentMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/paymentCategory/update/{paymentCatagoryId}")

	public ResponseEntity<?> updatePaymentCategory(@PathVariable("paymentCatagoryId") String paymentCatagoryId,
			@RequestBody PaymentMapper paymentMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(paymentMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = paymentService.checkNameInPaymentByOrgLevel(paymentMapper.getName(), orgId);
				if (b == true) {
					map.put("sectorInd", b);
					map.put("message", "SectorName can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} 

			}
			paymentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			paymentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<PaymentMapper>(
					paymentService.updatePaymentCategory(paymentCatagoryId, paymentMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/paymentCategory/delete/{paymentCatagoryId}")
	public ResponseEntity<?> deletePaymentCatagory(@PathVariable("paymentCatagoryId") String paymentCatagoryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			paymentService.deletePaymentCatagory(paymentCatagoryId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/paymentCategory/search/{name}")
	public ResponseEntity<?> getpaymentByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<PaymentMapper> mapper = paymentService.getPaymentByNameByOrgLevel(name,orgId);
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/paymentCategory/count/{orgId}")
	public ResponseEntity<?> getPaymentCategoryCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(paymentService.getPaymentCategoryCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
