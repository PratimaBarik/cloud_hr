package com.app.employeePortal.expense.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.expense.service.ExpenseService;
import com.app.employeePortal.voucher.mapper.VoucherMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1")
public class ExpenseController {
	@Autowired
	ExpenseService expenseService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/expense")
	public ResponseEntity<?> saveExpense(@RequestBody List<ExpenseMapper> expenseList,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			VoucherMapper voucherId = expenseService.saveToExpenseProcess(expenseList, loggedInUserId,
					loggedInUserOrgId);

			return new ResponseEntity<>(voucherId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/expense/{expenseId}")

	public ResponseEntity<?> fetchExpense(@PathVariable("expenseId") String expenseId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ExpenseMapper expenseMapper = expenseService.getExpenseRelatedDetails(expenseId);

			return new ResponseEntity<ExpenseMapper>(expenseMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/* retrieve all expenses of an user */
	@GetMapping("/expense/user/{userId}")

	public ResponseEntity<?> getExpensesByUserId(@PathVariable("userId") String userId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ExpenseMapper> expList = expenseService.getExpenseDetailsListByUserId(userId);
			return new ResponseEntity<>(expList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/expense/organization/{orgId}")

	public ResponseEntity<?> getExpensesByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ExpenseMapper> expList = expenseService.getExpenseDetailsListByOrganizationId(orgId);
			return new ResponseEntity<>(expList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/expense/voucher/{voucherId}")

	public ResponseEntity<?> getExpensesByVoucherId(@PathVariable("voucherId") String voucherId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ExpenseMapper> expList = expenseService.getExpenseListByVoucherId(voucherId);
			Collections.sort(expList,
					(ExpenseMapper m1, ExpenseMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(expList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/expense")

	public ResponseEntity<?> updateMileage(@RequestBody ExpenseMapper expenseMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ExpenseMapper resultMapper = expenseService.updateExpenseDetails(expenseMapper);

			return new ResponseEntity<ExpenseMapper>(resultMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

//	@DeleteMapping("/expense/{expenseId}")
//	public ResponseEntity<?> deleteExpense(@PathVariable("expenseId") String expenseId,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			boolean b = expenseService.deleteExpense(expenseId);
//			return new ResponseEntity<>(b, HttpStatus.OK);
//
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@PostMapping("/expenseType")
	public ResponseEntity<?> createExpenseType(@RequestBody ExpenseMapper expenseMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		String expenseTypeId = null;
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(expenseMapper.getExpenseType())) {
				boolean b = expenseService.checkExpenseNameInExpenseTypeByOrgLevel(expenseMapper.getExpenseType(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("expenseInd", b);
					map.put("message", "Expense can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					expenseMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					expenseMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

					expenseTypeId = expenseService.saveExpenseType(expenseMapper);
					return new ResponseEntity<>(expenseTypeId, HttpStatus.OK);
				}
			} else {
				map.put("message", "Please Provide Expense !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/expenseType")
	public ResponseEntity<?> getAllExpenseType(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		List<ExpenseMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			typeList = expenseService.getExpenseTypeByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}
			return new ResponseEntity<>(typeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/expenseType")
	public ResponseEntity<?> updateExpenseType(@RequestBody ExpenseMapper expenseMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			expenseMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			expenseMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(expenseMapper.getExpenseType())) {
				boolean b = expenseService.checkExpenseNameInExpenseTypeByOrgLevel(expenseMapper.getExpenseType(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					Map map = new HashMap<>();
					map.put("expenseInd", true);
					map.put("message", "Expense can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			ExpenseMapper expenseMapperr = expenseService.updateExpenseType(expenseMapper.getExpenseTypeId(),
					expenseMapper);

			return new ResponseEntity<ExpenseMapper>(expenseMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/expenseType/{name}")
	public ResponseEntity<?> getExpenseTypeByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<ExpenseMapper> expenseMapper = expenseService.getExpenseTypeByNameByOrgLevel(name,orgId);
			if (null != expenseMapper && !expenseMapper.isEmpty()) {
				return new ResponseEntity<>(expenseMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			// return new ResponseEntity<>(expenseMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/expenseType/{expenseTypeId}")

	public ResponseEntity<?> deleteExpenseType(@PathVariable("expenseTypeId") String expenseTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			expenseService.deleteExpenseTypeById(expenseTypeId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/delete/{expenseId}")

	public ResponseEntity<?> deleteExpense(@PathVariable("expenseId") String expenseId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String message = expenseService.deleteExpense(expenseId);
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/voucher/delete/{voucherId}")

	public ResponseEntity<?> deleteVoucher(@PathVariable("voucherId") String voucherId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String message = expenseService.deleteVoucher(voucherId);
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/expense/status/{userId}/{status}/{pageNo}")
	public ResponseEntity<?> getExpenseStatusListByUserId(@PathVariable("userId") String userId,
			@PathVariable("status") String status, @PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<VoucherMapper> expenseList = expenseService.getExpenseStatusListByUserId(userId, status, pageNo,
					pageSize);
			return new ResponseEntity<>(expenseList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/expense/notes")
	public ResponseEntity<?> createExpenseNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = expenseService.saveExpenseNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/expense/notes/{expenseId}")
	public ResponseEntity<?> getNoteListByLeaveId(@PathVariable("expenseId") String expenseId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = expenseService.getNoteListByExpenseId(expenseId);
			if (null != notesMapper && !notesMapper.isEmpty()) {
				notesMapper
						.sort((NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@PutMapping("/api/v1/expense/notes/update/{notesId}")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@PathVariable("notesId") String notesId, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = expenseService.updateNoteDetails(notesId, notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@DeleteMapping("/api/v1/expense/note/{notesId}")
	public ResponseEntity<?> deleteExpenseNotes(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			expenseService.deleteExpenseNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/expenseType/count/{orgId}")
	public ResponseEntity<?> getExpenseTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(expenseService.getExpenseTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
