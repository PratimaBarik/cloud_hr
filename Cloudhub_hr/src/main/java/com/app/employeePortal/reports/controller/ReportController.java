package com.app.employeePortal.reports.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.reports.mapper.PdfMyViewRequirementMapper;
import com.app.employeePortal.reports.mapper.PdfMyViewSelectedMapper;
import com.app.employeePortal.reports.mapper.PdfOrgRequirementMapper;
import com.app.employeePortal.reports.mapper.PdfOrgSelectedMapper;
import com.app.employeePortal.reports.service.ReportService;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.voucher.mapper.VoucherMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class ReportController {
	
	@Autowired
    private TokenProvider jwtTokenUtil;
	@Autowired
	ReportService reportService;
	

	/* retrieve all reports of an user */
	@GetMapping("/api/v1/reports/user/{userId}")
	public ResponseEntity<?> getAllReportsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		
		List<MileageMapper>  mileageList = null;
		List<ExpenseMapper>  expenseList = null;
		List<LeavesMapper>  leavesList = null;
		List<EmployeeMapper>  employeeList = null;
		List<TaskViewMapper>  taskList = null;
		List<VoucherMapper>  voucherList = null;
		List<RecruitmentOpportunityMapper> recruitmentList = null;
		List<RecruitmentOpportunityMapper> selectedList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			
			
			if(type.equalsIgnoreCase("mileage")) {
				
				mileageList = reportService.getMileageListByUserIdWithDateRange(userId,startDate,endDate);
				Collections.sort(mileageList, (MileageMapper m1, MileageMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(mileageList, HttpStatus.OK);
	
			}else if(type.equalsIgnoreCase("expense")) {
				
				expenseList = reportService.getExpenseListByUserIdWithDateRange(userId,startDate,endDate); 	
				Collections.sort(expenseList, (ExpenseMapper m1, ExpenseMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(expenseList, HttpStatus.OK);
	
				
			}else if(type.equalsIgnoreCase("voucher")) {
				voucherList = reportService.getVoucherListByUserIdWithDateRange(userId,startDate,endDate); 	
				Collections.sort(voucherList, (VoucherMapper m1, VoucherMapper m2) -> m2.getVoucherDate()
	 					.compareTo(m1.getVoucherDate()));
				return new ResponseEntity<>(voucherList, HttpStatus.OK);

				
			}else if(type.equalsIgnoreCase("employees")) {
				employeeList = reportService.getEmployeeListByUserIdWithDateRange(userId,startDate,endDate); 	
				Collections.sort(employeeList, (EmployeeMapper m1, EmployeeMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(employeeList, HttpStatus.OK);

				
			}else if(type.equalsIgnoreCase("task")) {
				taskList = reportService.getTaskListByUserIdWithDateRange(userId,startDate,endDate); 	
				Collections.sort(taskList, ( m1,  m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(taskList, HttpStatus.OK);
	
				
			}else if(type.equalsIgnoreCase("leave")) {
				
				leavesList = reportService.getLeavesListByUserIdWithDateRange(userId,startDate,endDate); 	
				Collections.sort(leavesList, (LeavesMapper m1, LeavesMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leavesList, HttpStatus.OK);
				
			}else if(type.equalsIgnoreCase("recruitment")) {
				recruitmentList = reportService.getOpenRecruitmentByuserIdAndDateRange(userId,startDate,endDate); 	
				Collections.sort(recruitmentList, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(recruitmentList, HttpStatus.OK);
			
			}else if(type.equalsIgnoreCase("selected")) {
				selectedList = reportService.getSelectedRecruitmentByuserIdAndDateRange(userId,startDate,endDate); 	
			Collections.sort(selectedList, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2.getCreationDate()
 					.compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(selectedList, HttpStatus.OK);
			
		}else if(type.equalsIgnoreCase("y")) {
			recruitmentList = reportService.getOpenRecruitmentByuserIdAndDateRange(userId,startDate,endDate); 	
			Collections.sort(recruitmentList, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2.getCreationDate()
 					.compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(recruitmentList, HttpStatus.OK);
		}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/* retrieve all reports of an user */
	@GetMapping("/api/v1/reports/organization/{organizationId}")
	public ResponseEntity<?> getAllReportsByOrgId(@PathVariable("organizationId") String organizationId,
			@RequestParam(value = "type", required = true) String type,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {
		
	
		List<MileageMapper>  mileageList = null;
		List<ExpenseMapper>  expenseList = null;
		List<LeavesMapper>  leavesList = null;
		List<EmployeeMapper>  employeeList = null;
		List<TaskViewMapper>  taskList = null;
		List<VoucherMapper>  voucherList = null;
		
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			
			
			if(type.equalsIgnoreCase("mileage")) {
				
				mileageList = reportService.getMileageListByOrgIdWithDateRange(organizationId,startDate,endDate); 
				Collections.sort(mileageList, (MileageMapper m1, MileageMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(mileageList, HttpStatus.OK);
	
			}else if(type.equalsIgnoreCase("expense")) {
				
				expenseList = reportService.getExpenseListByOrgIdWithDateRange(organizationId,startDate,endDate); 	
				Collections.sort(expenseList, (ExpenseMapper m1, ExpenseMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(expenseList, HttpStatus.OK);
	
				
			}else if(type.equalsIgnoreCase("voucher")) {
				voucherList = reportService.getVoucherListByOrgIdWithDateRange(organizationId,startDate,endDate); 	
				Collections.sort(voucherList, (VoucherMapper m1, VoucherMapper m2) -> m2.getVoucherDate()
	 					.compareTo(m1.getVoucherDate()));
				return new ResponseEntity<>(voucherList, HttpStatus.OK);

				
			}else if(type.equalsIgnoreCase("employees")) {
				employeeList = reportService.getEmployeeListByOrgIdWithDateRange(organizationId,startDate,endDate); 	
				Collections.sort(employeeList, (EmployeeMapper m1, EmployeeMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(employeeList, HttpStatus.OK);

				
			}else if(type.equalsIgnoreCase("task")) {
				taskList = reportService.getTaskListByOrgIdWithDateRange(organizationId,startDate,endDate); 	
				Collections.sort(taskList, ( m1,  m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(taskList, HttpStatus.OK);
	
				
			}else if(type.equalsIgnoreCase("leave")) {
				
				leavesList = reportService.getLeavesListByOrgIdWithDateRange(organizationId,startDate,endDate); 	
				Collections.sort(leavesList, (LeavesMapper m1, LeavesMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leavesList, HttpStatus.OK);
		
			}
		}
			
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@GetMapping("/api/v1/reports/user/sales/{userId}")
	public ResponseEntity<?> getAllSalesReportsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		List<RecruitmentOpportunityMapper> recruitmentList = null;
		List<RecruitmentOpportunityMapper> selectedList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			 if(type.equalsIgnoreCase("recruitment")) {
				recruitmentList = reportService.getSalesOpenRecruitmentByuserIdAndDateRange(userId,startDate,endDate); 	
				Collections.sort(recruitmentList, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(recruitmentList, HttpStatus.OK);
			
			}else if(type.equalsIgnoreCase("selected")) {
				selectedList = reportService.getOfferedCandidateByuserIdAndDateRange(userId,startDate,endDate); 	
			Collections.sort(selectedList, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2.getCreationDate()
 					.compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(selectedList, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/recruitment/org/reports/{orgId}")
	public ResponseEntity<?> getAllSalesReportsByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "type", required = true) String type,
			@RequestHeader("Authorization") String authorization, 
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		List<RecruitmentOpportunityMapper> recruitmentList = null;
		List<RecruitmentOpportunityMapper> selectedList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			 if(type.equalsIgnoreCase("recruitment")) {
				recruitmentList = reportService.getSalesOpenRecruitmentByOrgIdAndDateRange(orgId,startDate,endDate); 	
				Collections.sort(recruitmentList, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2.getCreationDate()
	 					.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(recruitmentList, HttpStatus.OK);
			
			}else if(type.equalsIgnoreCase("selected")) {
				selectedList = reportService.getOfferedCandidateByOrgIdAndDateRange(orgId,startDate,endDate); 	
			Collections.sort(selectedList, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2.getCreationDate()
 					.compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(selectedList, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/reports/myview/selected/allInd")
	public ResponseEntity<?>updateAllSubmittedInd(@RequestBody PdfMyViewSelectedMapper pdfMyViewSelectedMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			pdfMyViewSelectedMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			pdfMyViewSelectedMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			PdfMyViewSelectedMapper Id = reportService.updateAllSubmittedInd(pdfMyViewSelectedMapper);

			return new ResponseEntity<PdfMyViewSelectedMapper>(Id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/api/v1/reports/myview/requirement/allInd")
	public ResponseEntity<?> updateAllRequirementInd(@RequestBody PdfMyViewRequirementMapper pdfMyViewRequirementMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			pdfMyViewRequirementMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			pdfMyViewRequirementMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			PdfMyViewRequirementMapper Id = reportService.updateAllRequirementInd(pdfMyViewRequirementMapper);

			return new ResponseEntity<PdfMyViewRequirementMapper>(Id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@PostMapping("/api/v1/reports/org/requirement/allInd")
	public ResponseEntity<?> saveOrgRequirementAllInd(@RequestBody PdfOrgRequirementMapper pdfOrgRequirementMapper,
			@RequestHeader("Authorization") String authorization) {
	
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			pdfOrgRequirementMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			pdfOrgRequirementMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
	
			PdfOrgRequirementMapper Id = reportService.saveOrgRequirementAllInd(pdfOrgRequirementMapper);
	
			return new ResponseEntity<PdfOrgRequirementMapper>(Id, HttpStatus.OK);
	
		}
	
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/api/v1/reports/org/selected/allInd")
	public ResponseEntity<?> saveOrgSelectedAllInd(@RequestBody PdfOrgSelectedMapper pdfOrgSelectedMapper,
			@RequestHeader("Authorization") String authorization) {
	
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			pdfOrgSelectedMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			pdfOrgSelectedMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
	
			PdfOrgSelectedMapper Id = reportService.saveOrgSelectedAllInd(pdfOrgSelectedMapper);
	
			return new ResponseEntity<PdfOrgSelectedMapper>(Id, HttpStatus.OK);
	
		}
	
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/reports/myview/selected/allInd/{userId}")
	public ResponseEntity<?> getAllSelectededIndByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<PdfMyViewSelectedMapper> pdfMyViewSelectedMapper = reportService.getAllSelectededIndByUserId(userId);

			return new ResponseEntity<>(pdfMyViewSelectedMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/reports/myview/requirement/allInd/{userId}")
	public ResponseEntity<?> getAllRequirementIndByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<PdfMyViewRequirementMapper> pdfMyViewRequirementMapper = reportService.getAllRequirementIndByUserId(userId);

			return new ResponseEntity<>(pdfMyViewRequirementMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/reports/org/requirement/allInd/{userId}")
	public ResponseEntity<?> getAllOrgRequirementIndByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<PdfOrgRequirementMapper> pdfOrgRequirementMapper = reportService.getAllOrgRequirementIndByUserId(userId);

			return new ResponseEntity<>(pdfOrgRequirementMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/reports/org/selected/allInd/{userId}")
	public ResponseEntity<?> getAllOrgSelectedIndByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<PdfOrgSelectedMapper> pdfOrgSelectedMapper = reportService.getAllOrgSelectedIndByUserId(userId);

			return new ResponseEntity<>(pdfOrgSelectedMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	/*
	 
	 * @GetMapping("/api/v1/reports/user/today/{userId}") public ResponseEntity<?>
	 * getAllReportsByUserId(@PathVariable("userId") String userId,
	 * 
	 * @RequestParam(value = "type", required = true) String type,
	 * 
	 * @RequestHeader("Authorization") String authorization,
	 * 
	 * @RequestParam(value = "todayDate", required = false) String todayDate,
	 * HttpServletRequest request) {
	 * 
	 * List<MileageMapper> mileageList = null;
	 * 
	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	 * 
	 * if(type.equalsIgnoreCase("mileage")) {
	 * 
	 * mileageList =
	 * reportService.getMileageListByUserIdWithDateRange(userId,todayDate);
	 * Collections.sort(mileageList, (MileageMapper m1, MileageMapper m2) ->
	 * m2.getCreationDate() .compareTo(m1.getCreationDate())); return new
	 * ResponseEntity<>(mileageList, HttpStatus.OK); } } return new
	 * ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	 * 
	 * 
	 * 
	 * }
	 */
}
