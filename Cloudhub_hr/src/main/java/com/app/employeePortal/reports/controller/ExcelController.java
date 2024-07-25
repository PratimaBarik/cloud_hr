package com.app.employeePortal.reports.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.service.OpportunityService;
import com.app.employeePortal.RoleTypeExternal.mapper.RoleTypeExternalMapper;
import com.app.employeePortal.RoleTypeExternal.service.RoleTypeExternalService;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.category.mapper.CustomerTypeMapper;
import com.app.employeePortal.category.mapper.IdProofTypeMapper;
import com.app.employeePortal.category.mapper.InvestorCategoryMapper;
import com.app.employeePortal.category.mapper.ItemTaskMapper;
import com.app.employeePortal.category.mapper.LeadsCategoryMapper;
import com.app.employeePortal.category.mapper.LobMapper;
import com.app.employeePortal.category.mapper.NavResponseMapper;
import com.app.employeePortal.category.mapper.PaymentMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtRespMapper;
import com.app.employeePortal.category.mapper.RegionsMapper;
import com.app.employeePortal.category.mapper.RoleMapper;
import com.app.employeePortal.category.mapper.ServiceLineRespMapper;
import com.app.employeePortal.category.mapper.ShipByMapper;
import com.app.employeePortal.category.service.CategoryService;
import com.app.employeePortal.category.service.CustomerTypeService;
import com.app.employeePortal.category.service.IdProofService;
import com.app.employeePortal.category.service.InvestorCategoryService;
import com.app.employeePortal.category.service.ItemTaskService;
import com.app.employeePortal.category.service.LeadsCategoryService;
import com.app.employeePortal.category.service.LobService;
import com.app.employeePortal.category.service.NavService;
import com.app.employeePortal.category.service.PaymentService;
import com.app.employeePortal.category.service.PerformanceManagementService;
import com.app.employeePortal.category.service.RegionsService;
import com.app.employeePortal.category.service.ServiceLineService;
import com.app.employeePortal.category.service.ShipByService;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.document.mapper.DocumentTypeMapper;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.education.mapper.EducationTypeMapper;
import com.app.employeePortal.education.service.EducationService;
import com.app.employeePortal.employee.mapper.EmployeeTableMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.event.service.EventService;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.expense.service.ExpenseService;
import com.app.employeePortal.leads.mapper.LeadsViewMapper;
import com.app.employeePortal.leads.service.LeadsService;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.location.mapper.LocationDetailsViewDTO;
import com.app.employeePortal.location.service.LocationDetailsService;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.mileage.service.MileageService;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.repository.PartnerDetailsRepository;
import com.app.employeePortal.partner.service.PartnerService;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.service.RecruitmentService;
import com.app.employeePortal.registration.mapper.CountryMapper;
import com.app.employeePortal.registration.mapper.CurrencyMapper;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.mapper.DesignationMapper;
import com.app.employeePortal.registration.service.CountryService;
import com.app.employeePortal.registration.service.DepartmentService;
import com.app.employeePortal.reports.service.ExcelService;
import com.app.employeePortal.reports.service.ReportService;
import com.app.employeePortal.sector.mapper.SectorMapper;
import com.app.employeePortal.sector.service.SectorService;
import com.app.employeePortal.source.mapper.SourceMapper;
import com.app.employeePortal.source.service.SourceService;
import com.app.employeePortal.task.mapper.TaskMapper;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.task.service.TaskService;
import com.app.employeePortal.voucher.mapper.VoucherMapper;

@RestController
@CrossOrigin(maxAge = 3600)
public class ExcelController {
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	ExcelService excelService;
	@Autowired
	ReportService reportService;

	@Autowired
	CandidateService candidateService;

	@Autowired
	PartnerService partnerService;

	@Autowired
	OpportunityService opportunityService;

	@Autowired
	ContactService contactService;

	@Autowired
	CustomerService customerService;

	@Autowired
	EmployeeService employeeService;
	@Autowired
	RecruitmentService recruitmentService;
	@Autowired
	PartnerDetailsRepository partnerDetailsRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	LocationDetailsService locationService;
	@Autowired
	LeadsService leadsService;
	@Autowired
	MileageService mileageService;
	@Autowired
	SectorService sectorService;
	@Autowired
	SourceService sourceService;
	@Autowired
	RoleTypeExternalService roleTypeExternalService;
	@Autowired
	TaskService taskService;
	@Autowired
	EventService eventService;
	@Autowired
	EducationService educationService;
	@Autowired
	DocumentService documentService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ExpenseService expenseService;
	@Autowired
	ShipByService shipByService;
	@Autowired
	PerformanceManagementService performanceManagementService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	NavService navService;
	@Autowired
	LeadsCategoryService leadsCategoryService;
	@Autowired
	ItemTaskService itemTaskService;
	@Autowired
	IdProofService idProofTypeService;
	@Autowired
	InvestorCategoryService investorCategoryService;
	@Autowired
	CustomerTypeService customerTypeService;
	@Autowired
	CountryService countryService;
	@Autowired
	LobService lobService;
	@Autowired
	RegionsService regionService;
	@Autowired
	ServiceLineService serviceLineService;
	@GetMapping("/api/v1/excel/export/user/{userId}")
	public void exportExcelReportsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
			HttpServletResponse response) {

		ByteArrayInputStream stream = null;
		String fileName = null;
		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		if (type.equalsIgnoreCase("mileage")) {
			List<MileageMapper> mileageList = reportService.getMileageListByUserIdWithDateRange(userId, startDate,
					endDate);
			if (null != mileageList && !mileageList.isEmpty()) {
				stream = excelService.exportMileageListToExcel(mileageList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Mileages.xlsx ";
			}
		} else if (type.equalsIgnoreCase("expense")) {
			List<ExpenseMapper> expenseList = reportService.getExpenseListByUserIdWithDateRange(userId, startDate,
					endDate);
			if (null != expenseList && !expenseList.isEmpty()) {
				stream = excelService.exportExpenseListToExcel(expenseList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Expenses.xlsx ";
			}
		} else if (type.equalsIgnoreCase("voucher")) {
			List<VoucherMapper> voucherList = reportService.getVoucherListByUserIdWithDateRange(userId, startDate,
					endDate);
			if (null != voucherList && !voucherList.isEmpty()) {
				stream = excelService.exportVoucherListToExcel(voucherList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Vocuhers.xlsx ";
			}

		} else if (type.equalsIgnoreCase("task")) {
			List<TaskViewMapper> taskList = reportService.getTaskListByUserIdWithDateRange(userId, startDate, endDate);
			if (null != taskList && !taskList.isEmpty()) {
				stream = excelService.exportTaskListToExcel(taskList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Tasks.xlsx ";
			}
		} else if (type.equalsIgnoreCase("leave")) {
			List<LeavesMapper> leavesList = reportService.getLeavesListByUserIdWithDateRange(userId, startDate,
					endDate);
			if (null != leavesList && !leavesList.isEmpty()) {
				stream = excelService.exportLeaveListToExcel(leavesList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Leaves.xlsx ";
			}
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		try {
			IOUtils.copy(stream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");
	}

	@GetMapping("/api/v1/excel/export/organization/{organizationId}")
	public void exportExcelReportsByOrgId(@PathVariable("organizationId") String organizationId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
			HttpServletResponse response) {

		ByteArrayInputStream stream = null;
		String fileName = null;
		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		if (type.equalsIgnoreCase("mileage")) {
			List<MileageMapper> mileageList = reportService.getMileageListByOrgIdWithDateRange(organizationId,
					startDate, endDate);
			if (null != mileageList && !mileageList.isEmpty()) {
				stream = excelService.exportMileageListToExcel(mileageList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Mileages.xlsx ";
			}
		} else if (type.equalsIgnoreCase("expense")) {
			List<ExpenseMapper> expenseList = reportService.getExpenseListByOrgIdWithDateRange(organizationId,
					startDate, endDate);
			if (null != expenseList && !expenseList.isEmpty()) {
				stream = excelService.exportExpenseListToExcel(expenseList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Expenses.xlsx ";
			}
		} else if (type.equalsIgnoreCase("voucher")) {
			List<VoucherMapper> voucherList = reportService.getVoucherListByOrgIdWithDateRange(organizationId,
					startDate, endDate);
			if (null != voucherList && !voucherList.isEmpty()) {
				stream = excelService.exportVoucherListToExcel(voucherList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Vocuhers.xlsx ";
			}

		} else if (type.equalsIgnoreCase("task")) {
			List<TaskViewMapper> taskList = reportService.getTaskListByOrgIdWithDateRange(organizationId, startDate,
					endDate);
			if (null != taskList && !taskList.isEmpty()) {
				stream = excelService.exportTaskListToExcel(taskList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Tasks.xlsx ";
			}
		} else if (type.equalsIgnoreCase("leave")) {
			List<LeavesMapper> leavesList = reportService.getLeavesListByOrgIdWithDateRange(organizationId, startDate,
					endDate);
			if (null != leavesList && !leavesList.isEmpty()) {
				stream = excelService.exportLeaveListToExcel(leavesList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Leaves.xlsx ";
			}
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		try {
			IOUtils.copy(stream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/user/contact/{userId}")
	public void exportContactExcelReportsByUserId(@PathVariable("userId") String userId, HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<ContactViewMapper> partnerList = contactService.getContactListByUserId(userId, 0,
				contactRepository.findAll().size());

		String fileName = "CRM_" + currentdate.format(new Date()) + "_contact.xlsx ";

		ByteArrayInputStream stream = contactService.exportEmployeeListToExcel(partnerList);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		try {
			IOUtils.copy(stream, response.getOutputStream());
			/*
			 * outputStream.flush(); outputStream.close();
			 */
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/user/candidate/{userId}")
	public void exportCandidateExcelReportsByUserId(@PathVariable("userId") String userId,
			HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<CandidateViewMapper> candidateList = candidateService.getCandidateListByUserId(userId);

		String fileName = "RDM_" + currentdate.format(new Date()) + "_candidate.xlsx ";

		ByteArrayInputStream stream = candidateService.exportCandidateListToExcel(candidateList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/user/opportunity/{userId}")
	public void exportOpportunityExcelReportsByUserId(@PathVariable("userId") String userId,
			HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<OpportunityViewMapper> opportunityList = opportunityService.getOpportunityDetailsListByUserId(userId);

		String fileName = "CRM_" + currentdate.format(new Date()) + "_opportunity.xlsx ";

		ByteArrayInputStream stream = opportunityService.exportCandidateListToExcel(opportunityList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/user/partner/{userId}")
	public void exportPartnerExcelReportsByUserId(@PathVariable("userId") String userId, HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<PartnerMapper> partnerList = partnerService.getPartnerDetailsListByUserId(userId, 0,
				partnerDetailsRepository.findAll().size());

		String fileName = "RDM_" + currentdate.format(new Date()) + "_Partner.xlsx ";

		ByteArrayInputStream stream = partnerService.exportEmployeeListToExcel(partnerList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/user/customer/{userId}")
	public void exportCustomerExcelReportsByUserId(@PathVariable("userId") String userId,
			HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<CustomerResponseMapper> customerList = customerService.getCustomerDetailsByuserId(userId);

		String fileName = "CRM_" + currentdate.format(new Date()) + "_Customer.xlsx ";

		ByteArrayInputStream stream = customerService.exportEmployeeListToExcel(customerList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	/*
	 * @GetMapping("/api/v1/excel/export/user/partnerContactLink/{userId}") public
	 * void exportPartnerContactExcelReportsByUserId(@PathVariable("userId") String
	 * userId, HttpServletResponse response) {
	 * 
	 * 
	 * 
	 * SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");
	 * 
	 * OutputStream outputStream = null;
	 * 
	 * 
	 * List<PartnerMapper> partnerList =
	 * partnerService.getAllPartnerContatList(userId);
	 * 
	 * String fileName = "RDM_"+currentdate.format(new
	 * Date())+"_PartnerContact.xlsx ";
	 * 
	 * ByteArrayInputStream stream =
	 * partnerService.exportEmployeeListToExcel(partnerList);
	 * response.setContentType("application/vnd.ms-excel");
	 * response.setHeader("Content-Disposition",
	 * "attachment; filename="+fileName+"");
	 * 
	 * try { IOUtils.copy(stream, response.getOutputStream());
	 * 
	 * response.flushBuffer(); } catch (IOException e) { e.printStackTrace(); }
	 * System.out.println("excel report downloaded successfully...........");
	 * 
	 * 
	 * }
	 */

	@GetMapping("/api/v1/excel/export/user/employee/{userId}")
	public void exportEmployeeExcelReportsByUserId(@PathVariable("userId") String userId,
			HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<EmployeeViewMapper> employeeList = employeeService.getEmployeeListByUserId(userId);

		String fileName = "RDM_" + currentdate.format(new Date()) + "_employee.xlsx ";

		ByteArrayInputStream stream = employeeService.exportEmployeeListToExcel(employeeList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/recruitment/summary/{oppId}")
	public void exportRecruitmentSummary(@PathVariable("oppId") String oppId, HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<RecruitmentOpportunityMapper> summaryList = recruitmentService.getRecruitmentSummary(oppId);

		String fileName = "RDM_" + currentdate.format(new Date()) + "_summary.xlsx ";

		ByteArrayInputStream stream = recruitmentService.exportRecruitmentSummary(summaryList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");
	}

	@GetMapping("/api/v1/excel/export/recruitment/org/reports/{orgId}/{type}/{startDate}/{endDate}")
	public void exportExcelRecruitmentReportsByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
			HttpServletResponse response) {

		ByteArrayInputStream stream = null;
		String fileName = null;
		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		if (type.equalsIgnoreCase("recruitment")) {
			List<RecruitmentOpportunityMapper> recruitmentList = reportService
					.getSalesOpenRecruitmentByOrgIdAndDateRange(orgId, startDate, endDate);
			if (null != recruitmentList && !recruitmentList.isEmpty()) {
				stream = excelService.exportRecruitmentListToExcel(recruitmentList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Recruitment.xlsx ";
			}
		} else if (type.equalsIgnoreCase("selected")) {
			List<RecruitmentOpportunityMapper> selectedList = reportService
					.getOfferedCandidateByOrgIdAndDateRange(orgId, startDate, endDate);
			if (null != selectedList && !selectedList.isEmpty()) {
				stream = excelService.exportSelectedListToExcel(selectedList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Selected.xlsx ";
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		try {
			IOUtils.copy(stream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");
	}

	@GetMapping("/api/v1/excel/export/reports/user/sales/{userId}/{type}/{startDate}/{endDate}")
	public void exportExcelSalesReportsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
			HttpServletResponse response) {

		ByteArrayInputStream stream = null;
		String fileName = null;
		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		if (type.equalsIgnoreCase("recruitment")) {
			List<RecruitmentOpportunityMapper> recruitmentList = reportService
					.getSalesOpenRecruitmentByuserIdAndDateRange(userId, startDate, endDate);
			if (null != recruitmentList && !recruitmentList.isEmpty()) {
				stream = excelService.exportSalesOpenRecruitmentListToExcel(recruitmentList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Recruitment.xlsx ";
			}
		} else if (type.equalsIgnoreCase("selected")) {
			List<RecruitmentOpportunityMapper> selectedList = reportService
					.getOfferedCandidateByuserIdAndDateRange(userId, startDate, endDate);
			if (null != selectedList && !selectedList.isEmpty()) {
				stream = excelService.exportOfferedCandidateListToExcel(selectedList);
				fileName = "Korero_" + currentdate.format(new Date()) + "_Selected.xlsx ";
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		try {
			IOUtils.copy(stream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");
	}

	@GetMapping("/api/v1/excel/export/user/location/{userId}")
	public void exportLocationExcelReportsByUserId(@PathVariable("userId") String userId,
			HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<LocationDetailsViewDTO> locationList = locationService.getLocationDetailsListByUserId(userId);

		String fileName = "CRM_" + currentdate.format(new Date()) + "_location.xlsx ";

		ByteArrayInputStream stream = locationService.exportLocationListToExcel(locationList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/user/leads/{userId}")
	public void exportLeadsExcelReportsByUserId(@PathVariable("userId") String userId, HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<LeadsViewMapper> leadsList = leadsService.getLeadsDetailsByUserId(userId);

		String fileName = "CRM_" + currentdate.format(new Date()) + "_leads.xlsx ";

		ByteArrayInputStream stream = leadsService.exportLeadsListToExcel(leadsList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/org/employee_list/{orgId}")
	public void exportEmployee(@PathVariable("orgId") String orgId, HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<EmployeeTableMapper> employeeList = employeeService.getEmployeesByOrgId(orgId);

		String fileName = "CRM_" + currentdate.format(new Date()) + "_employee.xlsx ";

		ByteArrayInputStream stream = reportService.exportEmployeeByOrgListToExcel(employeeList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");
	}

	@GetMapping("/api/v1/excel/export/org/leads_list/{orgId}")
	public void exportLeads(@PathVariable("orgId") String orgId, HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<LeadsViewMapper> leadsList = leadsService.getLeadsDetailsByOrgId(orgId);

		String fileName = "CRM_" + currentdate.format(new Date()) + "_leads.xlsx ";

		ByteArrayInputStream stream = leadsService.exportLeadsListToExcel(leadsList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/organisation/location/{orgId}")
	public void exportLocationExcelReports(@PathVariable("orgId") String orgId, HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<LocationDetailsViewDTO> locationList = locationService.getLocationDetailsList(orgId);

		String fileName = "CRM_" + currentdate.format(new Date()) + "_location.xlsx ";

		ByteArrayInputStream stream = locationService.exportLocationListToExcel(locationList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/mileage/organization/{organizationId}{status}")
	public void exportMileageExcelReportsByOrgId(@PathVariable("organizationId") String organizationId,
			@RequestParam(value = "status") String status, @RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate, HttpServletRequest request, HttpServletResponse response) {

		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		OutputStream outputStream = null;

		List<VoucherMapper> mileageList = mileageService.getMileageListByOrgIdWithDateRangeAndStatus(organizationId,
				startDate, endDate, status);

		String fileName = "CRM_" + currentdate.format(new Date()) + "_mileage.xlsx ";

		ByteArrayInputStream stream = mileageService.exportMileage(mileageList);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		try {
			IOUtils.copy(stream, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");
	}

	@GetMapping("/api/v1/excel/export/catagory/All/{orgId}")
	public void exportCatagoryExcelReportsByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "type", required = true) String type, HttpServletResponse response) {
		ByteArrayInputStream stream = null;
		String fileName = null;
		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		if (type.equalsIgnoreCase("customerType")) {
			List<CustomerTypeMapper> list = customerTypeService.getCustomerTypeByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = customerTypeService.exportCustomerTypeListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_CustomerType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("department")) {
			List<DepartmentMapper> list = departmentService.getDepartmentByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = departmentService.exportDepartmentListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Department.xlsx ";
			}
		} else if (type.equalsIgnoreCase("designation")) {
			List<DesignationMapper> list = departmentService.getDesignationByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = departmentService.exportDesignationListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Designation.xlsx ";
			}
		} else if (type.equalsIgnoreCase("documentType")) {
			List<DocumentTypeMapper> list = documentService.getDocumentTypesByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = documentService.exportDocumentListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_DocumentType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("educationType")) {
			List<EducationTypeMapper> list = educationService.getEducationTypesByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = educationService.exportEducationListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_EducationType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("eventType")) {
			List<EventMapper> list = eventService.getEventTypeByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = eventService.exportEventTypeListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_EventType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("expenseType")) {
			List<ExpenseMapper> list = expenseService.getExpenseTypeByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = expenseService.exportExpenseListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_ExpenseType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("idProofType")) {
			List<IdProofTypeMapper> list = idProofTypeService.getIdProofTypesByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = idProofTypeService.exportIdProofTypeListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_IdProofType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("investorCategory")) {
			List<InvestorCategoryMapper> list = investorCategoryService.getInvestorCategoryByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = investorCategoryService.exportInvestorCategoryListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_InvestorCategory.xlsx ";
			}
		} else if (type.equalsIgnoreCase("itemTask")) {
			List<ItemTaskMapper> list = itemTaskService.getItemTaskMapperByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = itemTaskService.exportItemTaskListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_ItemTask.xlsx ";
			}
		} else if (type.equalsIgnoreCase("libraryType")) {
			List<RoleMapper> list = categoryService.getAllLibraryType(orgId);
			if (null != list && !list.isEmpty()) {
				stream = categoryService.exportLibraryTypeListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_LibraryType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("nav")) {
			List<NavResponseMapper> list = navService.getNavByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = navService.exportNavListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Nav.xlsx ";
			}
		} else if (type.equalsIgnoreCase("payment")) {
			List<PaymentMapper> list = paymentService.getPaymentCategoryByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = paymentService.exportPaymentListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Payment.xlsx ";
			}
		} else if (type.equalsIgnoreCase("performanceManagement")) {
			List<PerformanceMgmtRespMapper> list = performanceManagementService.getPerformanceManagementByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = performanceManagementService.exportPerformanceManagementListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_PerformanceManagement.xlsx ";
			}
		} else if (type.equalsIgnoreCase("roleType")) {
			List<RoleMapper> list = categoryService.getRoleListByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = categoryService.exportRoleTypeListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_RoleType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("roleTypeExternal")) {
			List<RoleTypeExternalMapper> list = roleTypeExternalService.getRoleListByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = roleTypeExternalService.exportRoleTypeExternalListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_RoleTypeExternal.xlsx ";
			}
		} else if (type.equalsIgnoreCase("sector")) {
			List<SectorMapper> list = sectorService.getSectorTypeByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = sectorService.exportSectorListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Sector.xlsx ";
			}
		} else if (type.equalsIgnoreCase("shipBy")) {
			List<ShipByMapper> list = shipByService.getShipByByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = shipByService.exportShipByListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_ShipBy.xlsx ";
			}
		} else if (type.equalsIgnoreCase("source")) {
			List<SourceMapper> list = sourceService.getSourceByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = sourceService.exportSourceListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Source.xlsx ";
			}
		} else if (type.equalsIgnoreCase("taskType")) {
			List<TaskMapper> list = taskService.getAllTaskTypeTaskcheckList(orgId);
			if (null != list && !list.isEmpty()) {
				stream = taskService.exportTaskTypeListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_TaskType.xlsx ";
			}
		} else if (type.equalsIgnoreCase("leadsCategory")) {
			List<LeadsCategoryMapper> list = leadsCategoryService.getLeadsCategoryListByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = leadsCategoryService.exportleadsCategoryListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_LeadsCategory.xlsx ";
			}
		} else if (type.equalsIgnoreCase("lob")) {
			List<LobMapper> list = lobService.getLobMapperByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = lobService.exportLobListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_LOB.xlsx ";
			}
		} else if (type.equalsIgnoreCase("region")) {
			List<RegionsMapper> list = regionService.getRegionsByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = regionService.exportRegionsListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Regions.xlsx ";
			}
		}
		else if (type.equalsIgnoreCase("serviceLine")) {
			List<ServiceLineRespMapper> list = serviceLineService.getServiceLineByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				stream = serviceLineService.exportServiceLineListToExcel(list);
				fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_ServiceLine.xlsx ";
			}
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		try {
			IOUtils.copy(stream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/country/All/{orgId}")
	public void exportCountryExcelReports(@PathVariable("orgId") String orgId, HttpServletResponse response) {
		ByteArrayInputStream stream = null;
		String fileName = null;
		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		List<CountryMapper> list = countryService.countryLists(orgId);
		if (null != list && !list.isEmpty()) {
			stream = countryService.exportCountryListToExcel(list);
			fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Country.xlsx ";
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		try {
			IOUtils.copy(stream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}

	@GetMapping("/api/v1/excel/export/currency/All/{orgId}")
	public void exportCurrencyExcelReports(@PathVariable("orgId") String orgId,HttpServletResponse response) {
		ByteArrayInputStream stream = null;
		String fileName = null;
		SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");

		List<CurrencyMapper> list = countryService.getAllCurrencyListForCatagory(orgId);
		if (null != list && !list.isEmpty()) {
			stream = countryService.exportCurrencyListToExcel(list);
			fileName = "ENTERPRISEINABOX_" + currentdate.format(new Date()) + "_Currency.xlsx ";
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		try {
			IOUtils.copy(stream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel report downloaded successfully...........");

	}
}
