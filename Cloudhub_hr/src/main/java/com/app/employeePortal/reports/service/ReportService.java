package com.app.employeePortal.reports.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.employee.mapper.EmployeeTableMapper;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.reports.mapper.PdfMyViewRequirementMapper;
import com.app.employeePortal.reports.mapper.PdfMyViewSelectedMapper;
import com.app.employeePortal.reports.mapper.PdfOrgRequirementMapper;
import com.app.employeePortal.reports.mapper.PdfOrgSelectedMapper;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.voucher.mapper.VoucherMapper;

public interface ReportService {

	List<MileageMapper> getMileageListByUserIdWithDateRange(String userId, String startDate, String endDate);

	List<ExpenseMapper> getExpenseListByUserIdWithDateRange(String userId, String startDate, String endDate);

	List<VoucherMapper> getVoucherListByUserIdWithDateRange(String userId, String startDate, String endDate);

	List<EmployeeMapper> getEmployeeListByUserIdWithDateRange(String userId, String startDate, String endDate);

	List<TaskViewMapper> getTaskListByUserIdWithDateRange(String userId, String startDate, String endDate);

	List<LeavesMapper> getLeavesListByUserIdWithDateRange(String userId, String startDate, String endDate);

	List<MileageMapper> getMileageListByOrgIdWithDateRange(String organizationId, String startDate, String endDate);

	List<ExpenseMapper> getExpenseListByOrgIdWithDateRange(String organizationId, String startDate, String endDate);

	List<VoucherMapper> getVoucherListByOrgIdWithDateRange(String organizationId, String startDate, String endDate);

	List<EmployeeMapper> getEmployeeListByOrgIdWithDateRange(String organizationId, String startDate, String endDate);

	List<TaskViewMapper> getTaskListByOrgIdWithDateRange(String organizationId, String startDate, String endDate);

	List<LeavesMapper> getLeavesListByOrgIdWithDateRange(String organizationId, String startDate, String endDate);

	List<RecruitmentOpportunityMapper> getOpenRecruitmentByuserIdAndDateRange(String userId, String startDate,
			String endDate);

	List<RecruitmentOpportunityMapper> getSelectedRecruitmentByuserIdAndDateRange(String userId, String startDate,
			String endDate);

	List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByuserIdAndDateRange(String userId, String startDate,
			String endDate);

	List<RecruitmentOpportunityMapper> getOfferedCandidateByuserIdAndDateRange(String userId, String startDate,
			String endDate);

	//List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByOrgId(String orgId);

	//List<RecruitmentOpportunityMapper> getOfferedCandidateByOrgId(String orgId);

	List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByOrgIdAndDateRange(String orgId, String startDate,
			String endDate);

	List<RecruitmentOpportunityMapper> getOfferedCandidateByOrgIdAndDateRange(String orgId, String startDate,
			String endDate);
	
	PdfMyViewSelectedMapper updateAllSubmittedInd(PdfMyViewSelectedMapper pdfMyViewSelectedMapper);

	PdfMyViewRequirementMapper updateAllRequirementInd(PdfMyViewRequirementMapper pdfMyViewRequirementMapper);

	PdfOrgRequirementMapper saveOrgRequirementAllInd(PdfOrgRequirementMapper pdfOrgRequirementMapper);

	PdfOrgSelectedMapper saveOrgSelectedAllInd(PdfOrgSelectedMapper pdfOrgSelectedMapper);

	List<PdfMyViewSelectedMapper> getAllSelectededIndByUserId(String userId);

	List<PdfMyViewRequirementMapper> getAllRequirementIndByUserId(String userId);

	List<PdfOrgRequirementMapper> getAllOrgRequirementIndByUserId(String userId);

	List<PdfOrgSelectedMapper> getAllOrgSelectedIndByUserId(String userId);

	ByteArrayInputStream exportEmployeeByOrgListToExcel(List<EmployeeTableMapper> employeeList);

//	List<MileageMapper> getMileageListByUserIdWithDateRange(String userId, String todayDate);
}
