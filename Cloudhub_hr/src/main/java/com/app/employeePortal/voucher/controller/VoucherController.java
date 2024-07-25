package com.app.employeePortal.voucher.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.voucher.mapper.VoucherMapper;
import com.app.employeePortal.voucher.service.VoucherService;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1")
public class VoucherController {

	@Autowired
	VoucherService voucherService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	// retrieve all expense vouchers of an user
	@GetMapping("/voucher/expense/user/{pageNo}/{userId}")

	public ResponseEntity<?> getExpenseVouchersByUserId(@PathVariable("userId") String userId,
			HttpServletRequest request, @PathVariable("pageNo") int pageNo,@RequestHeader("Authorization") String authorization) {
		int pageSize = 10;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<VoucherMapper> voucherList = voucherService.getVoucherListByUserId(userId, "Expense",pageSize,pageNo);

			Collections.sort(voucherList,
					(VoucherMapper m1, VoucherMapper m2) -> m2.getVoucherDate().compareTo(m1.getVoucherDate()));
			return new ResponseEntity<>(voucherList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	// retrieve all expense vouchers of an organization

	@GetMapping("/voucher/expense/organization/{orgId}")

	public ResponseEntity<?> getExpenseVouchersByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<VoucherMapper> voucherList = voucherService.getVoucherListByOrganizationId(orgId, "Expense");
			Collections.sort(voucherList,
					(VoucherMapper m1, VoucherMapper m2) -> m2.getVoucherDate().compareTo(m1.getVoucherDate()));
			return new ResponseEntity<>(voucherList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	// retrieve all mileage vouchers of an user
	@GetMapping("/voucher/mileage/user/{pageNo}/{userId}")

	public ResponseEntity<?> getMileageVouchersByUserId(@PathVariable("userId") String userId,
			HttpServletRequest request, @PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 10;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<VoucherMapper> voucherList = voucherService.getVoucherListByUserId(userId, "Mileage",pageSize,pageNo);
			Collections.sort(voucherList,
					(VoucherMapper m1, VoucherMapper m2) -> m2.getVoucherDate().compareTo(m1.getVoucherDate()));
			return new ResponseEntity<>(voucherList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	// retrieve all mileage vouchers of an organization

	@GetMapping("/voucher/mileage/organization/{orgId}")

	public ResponseEntity<?> getMileageVouchersByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<VoucherMapper> voucherList = voucherService.getVoucherListByOrganizationId(orgId, "Mileage");
			Collections.sort(voucherList,
					(VoucherMapper m1, VoucherMapper m2) -> m2.getVoucherDate().compareTo(m1.getVoucherDate()));
			return new ResponseEntity<>(voucherList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/voucher/expenses/{name}")
	public ResponseEntity<?> getExpensesByVoucherName(@PathVariable("name") String name, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		String voucherType = "Expense";
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<VoucherMapper> voucherList = voucherService.getExpensesByVoucherName(voucherType, name);
			voucherList
					.sort((VoucherMapper m1, VoucherMapper m2) -> m2.getVoucherDate().compareTo(m1.getVoucherDate()));
			return new ResponseEntity<>(voucherList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
}
