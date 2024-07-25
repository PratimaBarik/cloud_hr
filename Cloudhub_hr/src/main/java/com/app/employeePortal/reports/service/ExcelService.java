package com.app.employeePortal.reports.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.voucher.mapper.VoucherMapper;

public interface ExcelService {
	
	
	public ByteArrayInputStream exportVoucherListToExcel(List<VoucherMapper> voucherList);
	
	public ByteArrayInputStream exportMileageListToExcel(List<MileageMapper> mileageList);
	
	public ByteArrayInputStream exportExpenseListToExcel(List<ExpenseMapper> expenseList);
	
	public ByteArrayInputStream exportEmployeeListToExcel(List<EmployeeMapper> employeeList);
	
	public ByteArrayInputStream exportTaskListToExcel(List<TaskViewMapper> taskList);
	
	public ByteArrayInputStream exportLeaveListToExcel(List<LeavesMapper> leavesList);
	
	public File exportEmployeeListByUserToExcel(String userId, String department, String type, String frequency) throws IOException;

	public ByteArrayInputStream exportRecruitmentListToExcel(List<RecruitmentOpportunityMapper> recruitmentList);

	public ByteArrayInputStream exportSelectedListToExcel(List<RecruitmentOpportunityMapper> selectedList);

	public ByteArrayInputStream exportSalesOpenRecruitmentListToExcel(
			List<RecruitmentOpportunityMapper> recruitmentList);

	public ByteArrayInputStream exportOfferedCandidateListToExcel(List<RecruitmentOpportunityMapper> selectedList);

		

}
