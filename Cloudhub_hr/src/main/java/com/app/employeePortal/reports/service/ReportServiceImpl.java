package com.app.employeePortal.reports.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.employee.mapper.EmployeeTableMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.expense.service.ExpenseService;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.leave.service.LeaveService;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.mileage.service.MileageService;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.service.RecruitmentService;
import com.app.employeePortal.reports.entity.PdfMyViewRequirement;
import com.app.employeePortal.reports.entity.PdfMyViewSelected;
import com.app.employeePortal.reports.entity.PdfOrgRequirement;
import com.app.employeePortal.reports.entity.PdfOrgSelected;
import com.app.employeePortal.reports.mapper.PdfMyViewRequirementMapper;
import com.app.employeePortal.reports.mapper.PdfMyViewSelectedMapper;
import com.app.employeePortal.reports.mapper.PdfOrgRequirementMapper;
import com.app.employeePortal.reports.mapper.PdfOrgSelectedMapper;
import com.app.employeePortal.reports.repository.PdfMyViewRequirementRepository;
import com.app.employeePortal.reports.repository.PdfMyViewSelectedRepository;
import com.app.employeePortal.reports.repository.PdfOrgRequirementRepository;
import com.app.employeePortal.reports.repository.PdfOrgSelectedRepository;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.task.service.TaskService;
import com.app.employeePortal.util.Utility;
import com.app.employeePortal.voucher.mapper.VoucherMapper;
import com.app.employeePortal.voucher.service.VoucherService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	MileageService mileageService;
	
	@Autowired
	ExpenseService expenseService;
	
	@Autowired
	VoucherService voucherService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	LeaveService leaveService;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RecruitmentService recruitmentService;
	@Autowired
	PdfMyViewSelectedRepository pdfMyViewSelectedRepository;
	@Autowired
	PdfMyViewRequirementRepository pdfMyViewRequirementRepository;
	@Autowired
	PdfOrgRequirementRepository pdfOrgRequirementRepository;
	@Autowired
	PdfOrgSelectedRepository pdfOrgSelectedRepository;
	private String[] employee_headings = { "Salutation","Name", "Department", "Role","Dial Code", "Mobile #", "Email","Joining Date"};
	@Override
	public List<MileageMapper> getMileageListByUserIdWithDateRange(String userId, String startDate, String endDate) {

		List<MileageMapper> mileageList = mileageService.getMileageListByUserIdWithDateRange(userId, startDate, endDate);
		System.out.println("mileageList.... "  +mileageList);
		
		return mileageList;
	}

	@Override
	public List<ExpenseMapper> getExpenseListByUserIdWithDateRange(String userId, String startDate, String endDate) {
		List<ExpenseMapper> expenseList = expenseService.getExpenseListByUserIdWithDateRange(userId, startDate, endDate);
		System.out.println("expenseList.... "  +expenseList);
		
		return expenseList;
	}

	@Override
	public List<VoucherMapper> getVoucherListByUserIdWithDateRange(String userId, String startDate, String endDate) {

		List<VoucherMapper> voucherList = voucherService.getVoucherListByUserIdWithDateRange(userId, startDate, endDate);
		System.out.println("voucherList.... "  +voucherList);
		
		return voucherList;
	
	}

	@Override
	public List<EmployeeMapper> getEmployeeListByUserIdWithDateRange(String userId, String startDate, String endDate) {
		List<EmployeeMapper> employeeList = employeeService.getEmployeeListByUserIdWithDateRange(userId, startDate, endDate);
		System.out.println("employeeList.... "  +employeeList);
						
			
		return employeeList;
	}

	@Override
	public List<TaskViewMapper> getTaskListByUserIdWithDateRange(String userId, String startDate, String endDate) {

		List<TaskViewMapper> taskList = taskService.getTaskListByUserIdWithDateRange(userId, startDate, endDate);
		System.out.println("taskList.... "  +taskList);
		return taskList;
	
	}

	@Override
	public List<LeavesMapper> getLeavesListByUserIdWithDateRange(String userId, String startDate, String endDate) {

		List<LeavesMapper> leavesList = leaveService.getLeavesListByUserIdWithDateRange(userId, startDate, endDate);
		System.out.println("leavesList.... "  +leavesList);
		return leavesList;
	
	}

	@Override
	public List<MileageMapper> getMileageListByOrgIdWithDateRange(String organizationId, String startDate,
			String endDate) {
		List<MileageMapper> mileageList = mileageService.getMileageListByOrgIdWithDateRange(organizationId, startDate, endDate);
		System.out.println("mileageList.... "  +mileageList);
		
		return mileageList;
	
	}

	@Override
	public List<ExpenseMapper> getExpenseListByOrgIdWithDateRange(String organizationId, String startDate,
			String endDate) {

		List<ExpenseMapper> expenseList = expenseService.getExpenseListByOrgIdWithDateRange(organizationId, startDate, endDate);
		System.out.println("expenseList.... "  +expenseList);
		
		return expenseList;
	
	}

	@Override
	public List<VoucherMapper> getVoucherListByOrgIdWithDateRange(String organizationId, String startDate,
			String endDate) {

		List<VoucherMapper>	voucherList = voucherService.getVoucherListByOrgIdWithDateRange(organizationId, startDate, endDate);
		System.out.println("voucherList.... "  +voucherList);
		
		return voucherList;
	
	}

	@Override
	public List<EmployeeMapper> getEmployeeListByOrgIdWithDateRange(String organizationId, String startDate,
			String endDate) {
		List<EmployeeMapper> employeeList = employeeService.getEmployeeListByOrgIdWithDateRange(organizationId, startDate, endDate);
		System.out.println("employeeList.... "  +employeeList);
		return employeeList;
	}

	@Override
	public List<TaskViewMapper> getTaskListByOrgIdWithDateRange(String organizationId, String startDate, String endDate) {


		List<TaskViewMapper> taskList = taskService.getTaskListByOrgIdWithDateRange(organizationId, startDate, endDate);
		System.out.println("taskList.... "  +taskList);
		return taskList;
	
	
	}

	@Override
	public List<LeavesMapper> getLeavesListByOrgIdWithDateRange(String organizationId, String startDate,
			String endDate) {

		List<LeavesMapper> leavesList = leaveService.getLeavesListByOrgIdWithDateRange(organizationId, startDate, endDate);
		System.out.println("leavesList.... "  +leavesList);
		return leavesList;
	
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOpenRecruitmentByuserIdAndDateRange(String recruiterId , String startDate,String endDate) {
		List<RecruitmentOpportunityMapper> recruitmentList = recruitmentService.getOpenRecruitmentByuserIdAndDateRange(recruiterId, startDate, endDate);
		return recruitmentList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getSelectedRecruitmentByuserIdAndDateRange(String recruiterId, String startDate,String endDate) {
		List<RecruitmentOpportunityMapper> selectedList = recruitmentService.getSelectedRecruitmentByuserIdAndDateRange(recruiterId, startDate, endDate);
		return selectedList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByuserIdAndDateRange(String userId,String startDate, String endDate) {
		List<RecruitmentOpportunityMapper> recruitmentList = recruitmentService.getSalesOpenRecruitmentByuserIdAndDateRange(userId, startDate, endDate);
		return recruitmentList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOfferedCandidateByuserIdAndDateRange(String userId, String startDate,String endDate) {
		List<RecruitmentOpportunityMapper> selectedList = recruitmentService.getOfferedCandidateByuserIdAndDateRange(userId, startDate, endDate);
		return selectedList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByOrgIdAndDateRange(String orgId,String startDate,String endDate) {
		List<RecruitmentOpportunityMapper> recruitmentList = recruitmentService.getSalesOpenRecruitmentByOrgId(orgId);
		return recruitmentList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOfferedCandidateByOrgIdAndDateRange(String orgId,String startDate,String endDate) {
		List<RecruitmentOpportunityMapper> selectedList = recruitmentService.getOfferedCandidateByOrgId(orgId);
		return selectedList;
	}

	@Override
	public PdfMyViewSelectedMapper updateAllSubmittedInd(PdfMyViewSelectedMapper pdfMyViewSelectedMapper) {
		PdfMyViewSelectedMapper pdfMyViewSelectedMapper1 = new PdfMyViewSelectedMapper();
		PdfMyViewSelected pdfMyViewSelected = pdfMyViewSelectedRepository.findByUserId(pdfMyViewSelectedMapper.getUserId());
		if (null != pdfMyViewSelected) {
			pdfMyViewSelected.setCreatedInd(pdfMyViewSelectedMapper.isCreatedInd());
			pdfMyViewSelected.setJobIDInd(pdfMyViewSelectedMapper.isJobIDInd());
			pdfMyViewSelected.setOnboardedInd(pdfMyViewSelectedMapper.isOnboardedInd());
			pdfMyViewSelected.setOrgId(pdfMyViewSelectedMapper.getOrgId());
			pdfMyViewSelected.setRequirementInd(pdfMyViewSelectedMapper.isRequirementInd());
			pdfMyViewSelected.setSelectedInd(pdfMyViewSelectedMapper.isSelectedInd());
			pdfMyViewSelected.setSkillSetInd(pdfMyViewSelectedMapper.isSkillSetInd());
			pdfMyViewSelected.setSponsorInd(pdfMyViewSelectedMapper.isSponsorInd());
			pdfMyViewSelected.setStartDateInd(pdfMyViewSelectedMapper.isStartDateInd());
			pdfMyViewSelected.setSubmittedInd(pdfMyViewSelectedMapper.isSubmittedInd());
			pdfMyViewSelected.setUserId(pdfMyViewSelectedMapper.getUserId());
			pdfMyViewSelected.setUpdationDate(new Date());
			
			pdfMyViewSelectedRepository.save(pdfMyViewSelected);
			pdfMyViewSelectedMapper1= getPdfMyViewSelectedDetailsByPdfMyViewSelectedId(pdfMyViewSelected.getPdfMyViewSelectedId());

		} else {
			PdfMyViewSelected pdfMyViewSelected2 = new PdfMyViewSelected();
			pdfMyViewSelected2.setCreatedInd(pdfMyViewSelectedMapper.isCreatedInd());
			pdfMyViewSelected2.setJobIDInd(pdfMyViewSelectedMapper.isJobIDInd());
			pdfMyViewSelected2.setOnboardedInd(pdfMyViewSelectedMapper.isOnboardedInd());
			pdfMyViewSelected2.setOrgId(pdfMyViewSelectedMapper.getOrgId());
			pdfMyViewSelected2.setRequirementInd(pdfMyViewSelectedMapper.isRequirementInd());
			pdfMyViewSelected2.setSelectedInd(pdfMyViewSelectedMapper.isSelectedInd());
			pdfMyViewSelected2.setSkillSetInd(pdfMyViewSelectedMapper.isSkillSetInd());
			pdfMyViewSelected2.setSponsorInd(pdfMyViewSelectedMapper.isSponsorInd());
			pdfMyViewSelected2.setStartDateInd(pdfMyViewSelectedMapper.isStartDateInd());
			pdfMyViewSelected2.setSubmittedInd(pdfMyViewSelectedMapper.isSubmittedInd());
			pdfMyViewSelected2.setUserId(pdfMyViewSelectedMapper.getUserId());
			pdfMyViewSelected2.setUpdationDate(new Date());
			pdfMyViewSelectedRepository.save(pdfMyViewSelected2);
			pdfMyViewSelectedMapper1= getPdfMyViewSelectedDetailsByPdfMyViewSelectedId(pdfMyViewSelected2.getPdfMyViewSelectedId());

		}
		return pdfMyViewSelectedMapper1;
	}
	
	private PdfMyViewSelectedMapper getPdfMyViewSelectedDetailsByPdfMyViewSelectedId(String pdfMyViewSelectedId) {
		PdfMyViewSelectedMapper pdfMyViewSelectedMapper1 = new PdfMyViewSelectedMapper();
		PdfMyViewSelected pdfMyViewSelected = pdfMyViewSelectedRepository.findByPdfMyViewSelectedId(pdfMyViewSelectedId);
		if(null!=pdfMyViewSelected) {
			pdfMyViewSelectedMapper1.setCreatedInd(pdfMyViewSelected.isCreatedInd());
			pdfMyViewSelectedMapper1.setJobIDInd(pdfMyViewSelected.isJobIDInd());
			pdfMyViewSelectedMapper1.setOnboardedInd(pdfMyViewSelected.isOnboardedInd());
			pdfMyViewSelectedMapper1.setOrgId(pdfMyViewSelected.getOrgId());
			pdfMyViewSelectedMapper1.setPdfMyViewSelectedId(pdfMyViewSelected.getPdfMyViewSelectedId());
			pdfMyViewSelectedMapper1.setRequirementInd(pdfMyViewSelected.isRequirementInd());
			pdfMyViewSelectedMapper1.setSelectedInd(pdfMyViewSelected.isSelectedInd());
			pdfMyViewSelectedMapper1.setSkillSetInd(pdfMyViewSelected.isSkillSetInd());
			pdfMyViewSelectedMapper1.setSponsorInd(pdfMyViewSelected.isSponsorInd());
			pdfMyViewSelectedMapper1.setStartDateInd(pdfMyViewSelected.isStartDateInd());
			pdfMyViewSelectedMapper1.setSubmittedInd(pdfMyViewSelected.isSubmittedInd());
			pdfMyViewSelectedMapper1.setUpdationDate(Utility.getISOFromDate(pdfMyViewSelected.getUpdationDate()));
			pdfMyViewSelectedMapper1.setUserId(pdfMyViewSelected.getUserId());
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(pdfMyViewSelected.getUserId());
			if (null != employeeDetails) {
				String OwnermiddleName = " ";
				String OwnerlastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					OwnerlastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					OwnermiddleName = employeeDetails.getMiddleName();
					pdfMyViewSelectedMapper1.setName(employeeDetails.getFirstName() + " " + OwnermiddleName + " " + OwnerlastName);
				} else {

					pdfMyViewSelectedMapper1.setName(employeeDetails.getFirstName() + " " + OwnerlastName);
				}
			}
		}
		
		return pdfMyViewSelectedMapper1;
	}

	@Override
	public PdfMyViewRequirementMapper updateAllRequirementInd(PdfMyViewRequirementMapper pdfMyViewRequirementMapper) {
		PdfMyViewRequirementMapper pdfMyViewSelectedMapper1 = new PdfMyViewRequirementMapper();
		PdfMyViewRequirement pdfMyViewRequirement = pdfMyViewRequirementRepository.findByUserId(pdfMyViewRequirementMapper.getUserId());
		if (null != pdfMyViewRequirement) {
			pdfMyViewRequirement.setCreatedInd(pdfMyViewRequirementMapper.isCreatedInd());
			pdfMyViewRequirement.setCustomerInd(pdfMyViewRequirementMapper.isCustomerInd());
			pdfMyViewRequirement.setJobIDInd(pdfMyViewRequirementMapper.isJobIDInd());
			pdfMyViewRequirement.setOnboardedInd(pdfMyViewRequirementMapper.isOnboardedInd());
			pdfMyViewRequirement.setOrgId(pdfMyViewRequirementMapper.getOrgId());
			pdfMyViewRequirement.setRequirementInd(pdfMyViewRequirementMapper.isRequirementInd());
			pdfMyViewRequirement.setSelectedInd(pdfMyViewRequirementMapper.isSelectedInd());
			pdfMyViewRequirement.setSkillSetInd(pdfMyViewRequirementMapper.isSkillSetInd());
			pdfMyViewRequirement.setSponsorInd(pdfMyViewRequirementMapper.isSponsorInd());
			pdfMyViewRequirement.setStartDateInd(pdfMyViewRequirementMapper.isStartDateInd());
			pdfMyViewRequirement.setSubmittedInd(pdfMyViewRequirementMapper.isSubmittedInd());
			pdfMyViewRequirement.setUserId(pdfMyViewRequirementMapper.getUserId());
			pdfMyViewRequirement.setUpdationDate(new Date());
			
			pdfMyViewRequirementRepository.save(pdfMyViewRequirement);
			pdfMyViewSelectedMapper1= getPdfMyViewRequirementDetailsByPdfMyViewSelectedId(pdfMyViewRequirement.getPdfMyViewRequirementId());

		} else {
			PdfMyViewRequirement pdfMyViewRequirement2 = new PdfMyViewRequirement();
			pdfMyViewRequirement2.setCreatedInd(pdfMyViewRequirementMapper.isCreatedInd());
			pdfMyViewRequirement2.setCustomerInd(pdfMyViewRequirementMapper.isCustomerInd());
			pdfMyViewRequirement2.setJobIDInd(pdfMyViewRequirementMapper.isJobIDInd());
			pdfMyViewRequirement2.setOnboardedInd(pdfMyViewRequirementMapper.isOnboardedInd());
			pdfMyViewRequirement2.setOrgId(pdfMyViewRequirementMapper.getOrgId());
			pdfMyViewRequirement2.setRequirementInd(pdfMyViewRequirementMapper.isRequirementInd());
			pdfMyViewRequirement2.setSelectedInd(pdfMyViewRequirementMapper.isSelectedInd());
			pdfMyViewRequirement2.setSkillSetInd(pdfMyViewRequirementMapper.isSkillSetInd());
			pdfMyViewRequirement2.setSponsorInd(pdfMyViewRequirementMapper.isSponsorInd());
			pdfMyViewRequirement2.setStartDateInd(pdfMyViewRequirementMapper.isStartDateInd());
			pdfMyViewRequirement2.setSubmittedInd(pdfMyViewRequirementMapper.isSubmittedInd());
			pdfMyViewRequirement2.setUserId(pdfMyViewRequirementMapper.getUserId());
			pdfMyViewRequirement2.setUpdationDate(new Date());
			
			pdfMyViewRequirementRepository.save(pdfMyViewRequirement2);
			pdfMyViewSelectedMapper1= getPdfMyViewRequirementDetailsByPdfMyViewSelectedId(pdfMyViewRequirement2.getPdfMyViewRequirementId());

		}
		return pdfMyViewSelectedMapper1;
	}

	private PdfMyViewRequirementMapper getPdfMyViewRequirementDetailsByPdfMyViewSelectedId(
			String pdfMyViewRequirementId) {
		PdfMyViewRequirementMapper resultMapper = new PdfMyViewRequirementMapper();
		PdfMyViewRequirement pdfMyViewRequirement = pdfMyViewRequirementRepository.findByPdfMyViewRequirementId(pdfMyViewRequirementId);
		if(null!=pdfMyViewRequirement) {
			resultMapper.setCreatedInd(pdfMyViewRequirement.isCreatedInd());
			resultMapper.setCustomerInd(pdfMyViewRequirement.isCustomerInd());
			resultMapper.setJobIDInd(pdfMyViewRequirement.isJobIDInd());
			resultMapper.setOnboardedInd(pdfMyViewRequirement.isOnboardedInd());
			resultMapper.setOrgId(pdfMyViewRequirement.getOrgId());
			resultMapper.setPdfMyViewRequirementId(pdfMyViewRequirementId);
			resultMapper.setRequirementInd(pdfMyViewRequirement.isRequirementInd());
			resultMapper.setSelectedInd(pdfMyViewRequirement.isSelectedInd());
			resultMapper.setSkillSetInd(pdfMyViewRequirement.isSkillSetInd());
			resultMapper.setSponsorInd(pdfMyViewRequirement.isSponsorInd());
			resultMapper.setStartDateInd(pdfMyViewRequirement.isStartDateInd());
			resultMapper.setSubmittedInd(pdfMyViewRequirement.isSubmittedInd());
			resultMapper.setUpdationDate(Utility.getISOFromDate(pdfMyViewRequirement.getUpdationDate()));
			resultMapper.setUserId(pdfMyViewRequirement.getUserId());
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(pdfMyViewRequirement.getUserId());
			if (null != employeeDetails) {
				String OwnermiddleName = " ";
				String OwnerlastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					OwnerlastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					OwnermiddleName = employeeDetails.getMiddleName();
					resultMapper.setName(employeeDetails.getFirstName() + " " + OwnermiddleName + " " + OwnerlastName);
				} else {

					resultMapper.setName(employeeDetails.getFirstName() + " " + OwnerlastName);
				}
			}
		}
		
		return resultMapper;
	}

	@Override
	public PdfOrgRequirementMapper saveOrgRequirementAllInd(PdfOrgRequirementMapper pdfOrgRequirementMapper) {
		PdfOrgRequirementMapper resultMapper = new PdfOrgRequirementMapper();
		PdfOrgRequirement pdfOrgRequirement = pdfOrgRequirementRepository.findByUserId(pdfOrgRequirementMapper.getUserId());
		if (null != pdfOrgRequirement) {
			pdfOrgRequirement.setCreatedInd(pdfOrgRequirementMapper.isCreatedInd());
			pdfOrgRequirement.setJobIDInd(pdfOrgRequirementMapper.isJobIDInd());
			pdfOrgRequirement.setOnboardedInd(pdfOrgRequirementMapper.isOnboardedInd());
			pdfOrgRequirement.setOrgId(pdfOrgRequirementMapper.getOrgId());
			pdfOrgRequirement.setRequirementInd(pdfOrgRequirementMapper.isRequirementInd());
			pdfOrgRequirement.setSelectedInd(pdfOrgRequirementMapper.isSelectedInd());
			pdfOrgRequirement.setSkillSetInd(pdfOrgRequirementMapper.isSkillSetInd());
			pdfOrgRequirement.setSponsorInd(pdfOrgRequirementMapper.isSponsorInd());
			pdfOrgRequirement.setStartDateInd(pdfOrgRequirementMapper.isStartDateInd());
			pdfOrgRequirement.setSubmittedInd(pdfOrgRequirementMapper.isSubmittedInd());
			pdfOrgRequirement.setUserId(pdfOrgRequirementMapper.getUserId());
			pdfOrgRequirement.setUpdationDate(new Date());
			
			pdfOrgRequirementRepository.save(pdfOrgRequirement);
			resultMapper= getPdfOrgRequirementDetailsByPdfOrgRequirmentId(pdfOrgRequirement.getPdfOrgRequirementId());

		} else {
			PdfOrgRequirement pdfOrgRequirement1=new PdfOrgRequirement();
			pdfOrgRequirement1.setCreatedInd(pdfOrgRequirementMapper.isCreatedInd());
			pdfOrgRequirement1.setJobIDInd(pdfOrgRequirementMapper.isJobIDInd());
			pdfOrgRequirement1.setOnboardedInd(pdfOrgRequirementMapper.isOnboardedInd());
			pdfOrgRequirement1.setOrgId(pdfOrgRequirementMapper.getOrgId());
			pdfOrgRequirement1.setRequirementInd(pdfOrgRequirementMapper.isRequirementInd());
			pdfOrgRequirement1.setSelectedInd(pdfOrgRequirementMapper.isSelectedInd());
			pdfOrgRequirement1.setSkillSetInd(pdfOrgRequirementMapper.isSkillSetInd());
			pdfOrgRequirement1.setSponsorInd(pdfOrgRequirementMapper.isSponsorInd());
			pdfOrgRequirement1.setStartDateInd(pdfOrgRequirementMapper.isStartDateInd());
			pdfOrgRequirement1.setSubmittedInd(pdfOrgRequirementMapper.isSubmittedInd());
			pdfOrgRequirement1.setUserId(pdfOrgRequirementMapper.getUserId());
			pdfOrgRequirement1.setUpdationDate(new Date());
			
			pdfOrgRequirementRepository.save(pdfOrgRequirement1);
			resultMapper= getPdfOrgRequirementDetailsByPdfOrgRequirmentId(pdfOrgRequirement1.getPdfOrgRequirementId());

		}
		return resultMapper;
	}

	private PdfOrgRequirementMapper getPdfOrgRequirementDetailsByPdfOrgRequirmentId(String pdfOrgRequirementId) {
		PdfOrgRequirementMapper resultMapper = new PdfOrgRequirementMapper();
		PdfOrgRequirement pdfOrgRequirement = pdfOrgRequirementRepository.getByPdfRequiredmentId(pdfOrgRequirementId);
		if(null!=pdfOrgRequirement) {
			resultMapper.setCreatedInd(pdfOrgRequirement.isCreatedInd());
			resultMapper.setJobIDInd(pdfOrgRequirement.isJobIDInd());
			resultMapper.setOnboardedInd(pdfOrgRequirement.isOnboardedInd());
			resultMapper.setOrgId(pdfOrgRequirement.getOrgId());
			resultMapper.setPdfOrgRequirementId(pdfOrgRequirementId);
			resultMapper.setRequirementInd(pdfOrgRequirement.isRequirementInd());
			resultMapper.setSelectedInd(pdfOrgRequirement.isSelectedInd());
			resultMapper.setSkillSetInd(pdfOrgRequirement.isSkillSetInd());
			resultMapper.setSponsorInd(pdfOrgRequirement.isSponsorInd());
			resultMapper.setStartDateInd(pdfOrgRequirement.isStartDateInd());
			resultMapper.setSubmittedInd(pdfOrgRequirement.isSubmittedInd());
			resultMapper.setUpdationDate(Utility.getISOFromDate(pdfOrgRequirement.getUpdationDate()));
			resultMapper.setUserId(pdfOrgRequirement.getUserId());
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(pdfOrgRequirement.getUserId());
			if (null != employeeDetails) {
				String OwnermiddleName = " ";
				String OwnerlastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					OwnerlastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					OwnermiddleName = employeeDetails.getMiddleName();
					resultMapper.setName(employeeDetails.getFirstName() + " " + OwnermiddleName + " " + OwnerlastName);
				} else {

					resultMapper.setName(employeeDetails.getFirstName() + " " + OwnerlastName);
				}
			}
		}
		
		return resultMapper;
	}

	@Override
	public PdfOrgSelectedMapper saveOrgSelectedAllInd(PdfOrgSelectedMapper pdfOrgSelectedMapper) {
		PdfOrgSelectedMapper resultMapper = new PdfOrgSelectedMapper();
		PdfOrgSelected pdfOrgSelected = pdfOrgSelectedRepository.findByUserId(pdfOrgSelectedMapper.getUserId());
		if (null != pdfOrgSelected) {
			pdfOrgSelected.setCreatedInd(pdfOrgSelectedMapper.isCreatedInd());
			pdfOrgSelected.setCustomerInd(pdfOrgSelectedMapper.isCustomerInd());
			pdfOrgSelected.setJobIDInd(pdfOrgSelectedMapper.isJobIDInd());
			pdfOrgSelected.setOnboardedInd(pdfOrgSelectedMapper.isOnboardedInd());
			pdfOrgSelected.setOrgId(pdfOrgSelectedMapper.getOrgId());
			pdfOrgSelected.setRequirementInd(pdfOrgSelectedMapper.isRequirementInd());
			pdfOrgSelected.setSelectedInd(pdfOrgSelectedMapper.isSelectedInd());
			pdfOrgSelected.setSkillSetInd(pdfOrgSelectedMapper.isSkillSetInd());
			pdfOrgSelected.setSponsorInd(pdfOrgSelectedMapper.isSponsorInd());
			pdfOrgSelected.setStartDateInd(pdfOrgSelectedMapper.isStartDateInd());
			pdfOrgSelected.setSubmittedInd(pdfOrgSelectedMapper.isSubmittedInd());
			pdfOrgSelected.setUserId(pdfOrgSelectedMapper.getUserId());
			pdfOrgSelected.setUpdationDate(new Date());
			
			pdfOrgSelectedRepository.save(pdfOrgSelected);
			resultMapper= getPdfOrgSelectedIdDetailsByPdfOrgSelectedId(pdfOrgSelected.getPdfOrgSelectedId());

		} else {
			PdfOrgSelected pdfOrgSelected1=new PdfOrgSelected();
			
			pdfOrgSelected1.setCreatedInd(pdfOrgSelectedMapper.isCreatedInd());
			pdfOrgSelected1.setCustomerInd(pdfOrgSelectedMapper.isCustomerInd());
			pdfOrgSelected1.setJobIDInd(pdfOrgSelectedMapper.isJobIDInd());
			pdfOrgSelected1.setOnboardedInd(pdfOrgSelectedMapper.isOnboardedInd());
			pdfOrgSelected1.setOrgId(pdfOrgSelectedMapper.getOrgId());
			pdfOrgSelected1.setRequirementInd(pdfOrgSelectedMapper.isRequirementInd());
			pdfOrgSelected1.setSelectedInd(pdfOrgSelectedMapper.isSelectedInd());
			pdfOrgSelected1.setSkillSetInd(pdfOrgSelectedMapper.isSkillSetInd());
			pdfOrgSelected1.setSponsorInd(pdfOrgSelectedMapper.isSponsorInd());
			pdfOrgSelected1.setStartDateInd(pdfOrgSelectedMapper.isStartDateInd());
			pdfOrgSelected1.setSubmittedInd(pdfOrgSelectedMapper.isSubmittedInd());
			pdfOrgSelected1.setUserId(pdfOrgSelectedMapper.getUserId());
			pdfOrgSelected1.setUpdationDate(new Date());
			
			pdfOrgSelectedRepository.save(pdfOrgSelected1);
			resultMapper= getPdfOrgSelectedIdDetailsByPdfOrgSelectedId(pdfOrgSelected1.getPdfOrgSelectedId());

		}
		return resultMapper;
	}

	private PdfOrgSelectedMapper getPdfOrgSelectedIdDetailsByPdfOrgSelectedId(String pdfOrgSelectedId) {
		PdfOrgSelectedMapper resultMapper = new PdfOrgSelectedMapper();
		PdfOrgSelected pdfOrgSelected = pdfOrgSelectedRepository.getByPdfOrgSelectedId(pdfOrgSelectedId);
		if(null!=pdfOrgSelected) {
			resultMapper.setCreatedInd(pdfOrgSelected.isCreatedInd());
			resultMapper.setCustomerInd(pdfOrgSelected.isCustomerInd());
			resultMapper.setJobIDInd(pdfOrgSelected.isJobIDInd());
			resultMapper.setOnboardedInd(pdfOrgSelected.isOnboardedInd());
			resultMapper.setOrgId(pdfOrgSelected.getOrgId());
			resultMapper.setPdfOrgSelectedId(pdfOrgSelectedId);
			resultMapper.setRequirementInd(pdfOrgSelected.isRequirementInd());
			resultMapper.setSelectedInd(pdfOrgSelected.isSelectedInd());
			resultMapper.setSkillSetInd(pdfOrgSelected.isSkillSetInd());
			resultMapper.setSponsorInd(pdfOrgSelected.isSponsorInd());
			resultMapper.setStartDateInd(pdfOrgSelected.isStartDateInd());
			resultMapper.setSubmittedInd(pdfOrgSelected.isSubmittedInd());
			resultMapper.setUpdationDate(Utility.getISOFromDate(pdfOrgSelected.getUpdationDate()));
			resultMapper.setUserId(pdfOrgSelected.getUserId());
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(pdfOrgSelected.getUserId());
			if (null != employeeDetails) {
				String OwnermiddleName = " ";
				String OwnerlastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					OwnerlastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					OwnermiddleName = employeeDetails.getMiddleName();
					resultMapper.setName(employeeDetails.getFirstName() + " " + OwnermiddleName + " " + OwnerlastName);
				} else {

					resultMapper.setName(employeeDetails.getFirstName() + " " + OwnerlastName);
				}
			}
		}
		
		return resultMapper;
	}

	@Override
	public List<PdfMyViewSelectedMapper> getAllSelectededIndByUserId(String userId) {
		List<PdfMyViewSelected> pdfMyViewSelectedList = pdfMyViewSelectedRepository.getSelectedListByUserId(userId);
		List<PdfMyViewSelectedMapper> mapperList = new ArrayList<>();

		if (null != pdfMyViewSelectedList && !pdfMyViewSelectedList.isEmpty()) {
			mapperList = pdfMyViewSelectedList.stream().map(li -> getPdfMyViewSelectedDetailsByPdfMyViewSelectedId(li.getPdfMyViewSelectedId()))
					.collect(Collectors.toList());

		}
		return mapperList;
	}
	
	@Override
	public List<PdfMyViewRequirementMapper> getAllRequirementIndByUserId(String userId) {
		List<PdfMyViewRequirement> pdfMyViewRequirementList = pdfMyViewRequirementRepository.getRequirementListByUserId(userId);
		List<PdfMyViewRequirementMapper> mapperList = new ArrayList<>();

		if (null != pdfMyViewRequirementList && !pdfMyViewRequirementList.isEmpty()) {
			mapperList = pdfMyViewRequirementList.stream().map(li -> getPdfMyViewRequirementDetailsByPdfMyViewSelectedId(li.getPdfMyViewRequirementId()))
					.collect(Collectors.toList());

		}
		return mapperList;
	}
	
	@Override
	public List<PdfOrgRequirementMapper> getAllOrgRequirementIndByUserId(String userId) {
		List<PdfOrgRequirement> pdfOrgRequirementList = pdfOrgRequirementRepository.getRequirementListByUserId(userId);
		List<PdfOrgRequirementMapper> mapperList = new ArrayList<>();

		if (null != pdfOrgRequirementList && !pdfOrgRequirementList.isEmpty()) {
			mapperList = pdfOrgRequirementList.stream().map(li -> getPdfOrgRequirementDetailsByPdfOrgRequirmentId(li.getPdfOrgRequirementId()))
					.collect(Collectors.toList());

		}
		return mapperList;
	}
	
	@Override
	public List<PdfOrgSelectedMapper> getAllOrgSelectedIndByUserId(String userId) {
		List<PdfOrgSelected> pdfOrgSelectedList= pdfOrgSelectedRepository.getSelectedListByUserId(userId);
		List<PdfOrgSelectedMapper> mapperList = new ArrayList<>();

		if (null != pdfOrgSelectedList && !pdfOrgSelectedList.isEmpty()) {
			mapperList = pdfOrgSelectedList.stream().map(li -> getPdfOrgSelectedIdDetailsByPdfOrgSelectedId(li.getPdfOrgSelectedId()))
					.collect(Collectors.toList());

		}
		return mapperList;
	}
	
	@Override
	public ByteArrayInputStream exportEmployeeByOrgListToExcel(List<EmployeeTableMapper> employeeList) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Users");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < employee_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(employee_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != employeeList && !employeeList.isEmpty()) {
			for (EmployeeTableMapper employee : employeeList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(employee.getSalutation());
				row.createCell(1).setCellValue(employeeService.getEmployeeFullName( employee.getEmployeeId()));
				row.createCell(2).setCellValue(employee.getDepartment());
				row.createCell(3).setCellValue(employee.getRole());
				row.createCell(4).setCellValue(employee.getCountryDialCode());
				row.createCell(5).setCellValue(employee.getMobileNo());
				row.createCell(6).setCellValue(employee.getEmailId());
				if(null!=employee.getDateOfJoining()&&!employee.getDateOfJoining().isEmpty()) {
						try {
						row.createCell(7).setCellValue(Utility.getLocalDateByDate(Utility.getDateFromISOString(employee.getDateOfJoining())).toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
				row.createCell(7).setCellValue(" ");
				}
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < employee_headings.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());

	}
}

