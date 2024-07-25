package com.app.employeePortal.reports.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.service.RecruitmentService;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.util.Utility;
import com.app.employeePortal.voucher.mapper.VoucherMapper;

@Service
public class ExcelServiceImpl implements ExcelService {
	
	@Autowired
	RecruitmentService recruitmentService;

	
	private static String[] employee_heading = {"Employee Id", "First Name", "Middle Name", "Last Name", "Email",
			                                    "Mobile No", "Department", "Designation","Date of joining"}; 
	private static String[] voucher_heading = {"Voucher Id","Voucher Date", "Submitted By",  "Amount", "Status"};
	
	private static String[] mileage_heading = {"Mileage Id", "Mileage Date","Attributed To",  "From", "To","Distance","Mileage Rate","Remarks"};

	private static String[] expense_heading = {"Expense Id","Expense Date", "Attributed To", "Expense Type", "Amount", "Particulars"};

	private static String[] task_heading = {"Name","Type", "Submitted By", "Assigned On", "Remarks", "Status"};

	private static String[] leaves_heading = {"Start Date","End Date", "Cover", "Reason", "Status"};
	
	private static String[] selected_heading = {"Job Id","Requirement", "Customer", "Sponsor", "Created On", "Start Date", "Skill Set", "Submitted", "Selected", "Onboarded","Report Type"};
	
	private static String[] recruitment_heading = {"Requirement","Job ID","Created On","Start Date","Sponsor","Skill Set",
													"Submitted","Selected","Onboarded"};
	private static String[] selected_list_heading = {"Job ID","Requirement","Customer","Sponsor","Created On","Start Date",
													"Skill Set","Submitted","Selected","Onboarded"};
	@Override
	public ByteArrayInputStream exportVoucherListToExcel(List<VoucherMapper> voucherList) {


		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("voucher");

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
		for (int i = 0; i < voucher_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(voucher_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if(null!=voucherList && !voucherList.isEmpty()) {
			for (VoucherMapper voucher : voucherList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(voucher.getVoucherId());
				row.createCell(1).setCellValue(voucher.getVoucherDate());
				row.createCell(2).setCellValue(voucher.getSubmittedBy());
				row.createCell(3).setCellValue(voucher.getAmount());
				row.createCell(4).setCellValue(voucher.getStatus());
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < voucher_heading.length; i++) {
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

	@Override
	public ByteArrayInputStream exportMileageListToExcel(List<MileageMapper> mileageList) {



		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("mileage");

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
		for (int i = 0; i < mileage_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(mileage_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if(null!=mileageList && !mileageList.isEmpty()) {
			for (MileageMapper mileage : mileageList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mileage.getMileageId());
				row.createCell(1).setCellValue(mileage.getMileageDate());
				row.createCell(2).setCellValue(mileage.getClientName());
				row.createCell(3).setCellValue(mileage.getFromLocation());
				row.createCell(4).setCellValue(mileage.getToLocation());
				row.createCell(5).setCellValue(mileage.getDistances());
				row.createCell(6).setCellValue(mileage.getMileageRate());
				row.createCell(7).setCellValue(mileage.getRemark());
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < mileage_heading.length; i++) {
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

	@Override
	public ByteArrayInputStream exportExpenseListToExcel(List<ExpenseMapper> expenseList) {


		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("expense");

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
		for (int i = 0; i < expense_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(expense_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if(null!=expenseList && !expenseList.isEmpty()) {
			for (ExpenseMapper expense : expenseList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(expense.getExpenseId());
				row.createCell(1).setCellValue(expense.getExpenseDate());
				row.createCell(2).setCellValue(expense.getClientName());
				row.createCell(3).setCellValue(expense.getExpenseType());
				row.createCell(4).setCellValue(expense.getAmount());
				row.createCell(5).setCellValue(expense.getParticular());
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < expense_heading.length; i++) {
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

	@Override
	public ByteArrayInputStream exportEmployeeListToExcel(List<EmployeeMapper> employeeList) {


		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("employee");

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
		for (int i = 0; i < employee_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(employee_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if(null!=employeeList && !employeeList.isEmpty()) {
			for (EmployeeMapper employee : employeeList) {
				Row row = sheet.createRow(rowNum++);
                
				row.createCell(0).setCellValue(employee.getEmployeeId());
				row.createCell(1).setCellValue(employee.getFirstName());
				row.createCell(2).setCellValue(employee.getMiddleName());
				row.createCell(3).setCellValue(employee.getLastName());
				row.createCell(4).setCellValue(employee.getEmailId());
				row.createCell(5).setCellValue(employee.getMobileNo());
				row.createCell(6).setCellValue(employee.getDepartment());
				row.createCell(7).setCellValue(employee.getDesignation());
				row.createCell(8).setCellValue(employee.getDateOfJoining());
				
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < employee_heading.length; i++) {
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

	@Override
	public ByteArrayInputStream exportTaskListToExcel(List<TaskViewMapper> taskList) {



		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("task");

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
		for (int i = 0; i < task_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(task_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if(null!=taskList && !taskList.isEmpty()) {
			for (TaskViewMapper task : taskList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(task.getTaskName());
				row.createCell(1).setCellValue(task.getTaskType());
				row.createCell(2).setCellValue(task.getSubmittedBy());
				row.createCell(3).setCellValue(task.getAssignedOn());
				row.createCell(4).setCellValue(task.getSubmittedBy());
				row.createCell(5).setCellValue(task.getTaskStatus());
				
				
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < task_heading.length; i++) {
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

	@Override
	public ByteArrayInputStream exportLeaveListToExcel(List<LeavesMapper> leavesList) {



		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("leaves");

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
		for (int i = 0; i < leaves_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(leaves_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if(null!=leavesList && !leavesList.isEmpty()) {
			for (LeavesMapper leaves : leavesList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(leaves.getStartDate());
				row.createCell(1).setCellValue(leaves.getEndDate());
				row.createCell(2).setCellValue(leaves.getCoverDetails());
				row.createCell(3).setCellValue(leaves.getReason());
				row.createCell(4).setCellValue(leaves.getStatus());
				
				
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < leaves_heading.length; i++) {
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
@Override	
public File exportEmployeeListByUserToExcel(String userId,String department,String type, String frequency) throws IOException {
	System.out.println("Stated i.......1");
	Date newDate = new Date();
	String sEndDate = (Utility.getISOFromDate(newDate));
	String sStartDate = null;
	if(frequency.equalsIgnoreCase("Daily")) {
		 sStartDate = Utility.getMinusDate(newDate,1);
	}	
	if(frequency.equalsIgnoreCase("Weekly")) {
		 sStartDate = Utility.getMinusDate(newDate,7);
	}
	if(frequency.equalsIgnoreCase("Monthly")) {
		LocalDate today = LocalDate.now();
		sStartDate = Utility.getISOFromDate(Utility.getUtilDateByLocalDate(Utility.getStartDateOfMonth(today)));
		sEndDate = Utility.getISOFromDate(Utility.getDateAfterEndDate(Utility.getUtilDateByLocalDate(Utility.getEndDateOfMonth(today))));
	}
	if(frequency.equalsIgnoreCase("Quarterly")) {
		 sStartDate = Utility.getMinusDate(newDate,90);
	}

	List<RecruitmentOpportunityMapper> requirementList = new ArrayList<>();
	
	//	List<RecruitmentOpportunityMapper>  partnerList = recruitmentService. getSalesOpenByuserIdAndDateRange(userId);
	 if(type.equalsIgnoreCase("Requirement")) {
		 System.out.println("in recruitment loop");
		 if(department.equalsIgnoreCase("Recruiter")) {
			 requirementList = recruitmentService.getOpenRecruitmentByuserIdAndDateRange(userId, sStartDate, sEndDate);
		 }else {
			 System.out.println("startDate>>>"+sStartDate+"||"+"sEndDate>>"+sEndDate);
			 System.out.println("printing sales report>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 requirementList =recruitmentService.getSalesOpenRecruitmentByuserIdAndDateRange(userId, sStartDate, sEndDate);
		 System.out.println("requirementList>>>>>>>>>>>>>>>>>>>>>>"+requirementList.toString());
		 }
	 }else if(type.equalsIgnoreCase("selected")) {
		 if(department.equalsIgnoreCase("Recruiter")) {
			 requirementList = recruitmentService.getSelectedRecruitmentByuserIdAndDateRange(userId, sStartDate, sEndDate);
		 }else {
		  requirementList =recruitmentService.getOfferedCandidateByuserIdAndDateRange(userId, sStartDate, sEndDate);	
		 }
	 }
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("employee");

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
		for (int i = 0; i < selected_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(selected_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if(null!=requirementList && !requirementList.isEmpty()) {
			for (RecruitmentOpportunityMapper mapper : requirementList) {
				Row row = sheet.createRow(rowNum++);
	
				row.createCell(0).setCellValue(mapper.getJobOrder());
				row.createCell(1).setCellValue(mapper.getRequirementName());
				row.createCell(2).setCellValue(mapper.getCustomerName());
				row.createCell(3).setCellValue(mapper.getSponserName());
				row.createCell(4).setCellValue(mapper.getCreationDate());
				row.createCell(5).setCellValue(mapper.getAvilableDate());
				row.createCell(6).setCellValue(mapper.getSkillName());
				row.createCell(7).setCellValue(mapper.getOffered());
				row.createCell(8).setCellValue(mapper.getClosedPosition());
				row.createCell(9).setCellValue(mapper.getOnBoardNo());
				row.createCell(10).setCellValue(type);
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < selected_heading.length; i++) {
					sheet.autoSizeColumn(i);
				}
				
				/*create a temp file for Excel to add the datas*/
				
				File temp = File.createTempFile("Recruit", ".xls");
				FileOutputStream fos = new FileOutputStream(temp);
				workbook.write(fos);
				fos.close();	
		       
		  return temp;      
		}
	@Override
	public ByteArrayInputStream exportSalesOpenRecruitmentListToExcel(List<RecruitmentOpportunityMapper> recruitmentList) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("recruitment");
	
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
		for (int i = 0; i < recruitment_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(recruitment_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}
	
		int rowNum = 1;
		if(null!=recruitmentList && !recruitmentList.isEmpty()) {
			for (RecruitmentOpportunityMapper recruitment : recruitmentList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(recruitment.getRequirementName());
				row.createCell(1).setCellValue(recruitment.getJobOrder());
				row.createCell(2).setCellValue(recruitment.getCreationDate());
				row.createCell(3).setCellValue(recruitment.getAvilableDate());
				row.createCell(4).setCellValue(recruitment.getSponserName());
				row.createCell(5).setCellValue(recruitment.getSkillName());
				row.createCell(6).setCellValue(recruitment.getOffered());
				row.createCell(7).setCellValue(recruitment.getClosedPosition());
				row.createCell(8).setCellValue(recruitment.getOnBoardNo());
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < recruitment_heading.length; i++) {
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
	
	@Override
	public ByteArrayInputStream exportOfferedCandidateListToExcel(List<RecruitmentOpportunityMapper> selectedList) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("selected");
	
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
		for (int i = 0; i < selected_list_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(selected_list_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}
	
		int rowNum = 1;
		if(null!=selectedList && !selectedList.isEmpty()) {
			for (RecruitmentOpportunityMapper selected : selectedList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(selected.getJobOrder());
				row.createCell(1).setCellValue(selected.getRequirementName());
				row.createCell(2).setCellValue(selected.getCustomerName());
				row.createCell(3).setCellValue(selected.getSponserName());
				row.createCell(4).setCellValue(selected.getCreationDate());
				row.createCell(5).setCellValue(selected.getAvilableDate());
				row.createCell(6).setCellValue(selected.getSkillName());
				row.createCell(7).setCellValue(selected.getOffered());
				row.createCell(8).setCellValue(selected.getClosedPosition());
				row.createCell(9).setCellValue(selected.getOnBoardNo());
			}
		}
		// Resize all columns to fit the content size
				for (int i = 0; i < recruitment_heading.length; i++) {
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

		@Override
		public ByteArrayInputStream exportRecruitmentListToExcel(List<RecruitmentOpportunityMapper> recruitmentList) {
	XSSFWorkbook workbook = new XSSFWorkbook();
			
			// Create a blank sheet
			XSSFSheet sheet = workbook.createSheet("recruitment");
		
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
			for (int i = 0; i < recruitment_heading.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(recruitment_heading[i]);
				cell.setCellStyle(headerCellStyle);
			}
		
			int rowNum = 1;
			if(null!=recruitmentList && !recruitmentList.isEmpty()) {
				for (RecruitmentOpportunityMapper recruitment : recruitmentList) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(recruitment.getRequirementName());
					row.createCell(1).setCellValue(recruitment.getJobOrder());
					row.createCell(2).setCellValue(recruitment.getCreationDate());
					row.createCell(3).setCellValue(recruitment.getAvilableDate());
					row.createCell(4).setCellValue(recruitment.getSponserName());
					row.createCell(5).setCellValue(recruitment.getSkillName());
					row.createCell(6).setCellValue(recruitment.getOffered());
					row.createCell(7).setCellValue(recruitment.getClosedPosition());
					row.createCell(8).setCellValue(recruitment.getOnBoardNo());
				}
			}
			// Resize all columns to fit the content size
					for (int i = 0; i < recruitment_heading.length; i++) {
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
	
		@Override
		public ByteArrayInputStream exportSelectedListToExcel(List<RecruitmentOpportunityMapper> selectedList) {
	XSSFWorkbook workbook = new XSSFWorkbook();
			
			// Create a blank sheet
			XSSFSheet sheet = workbook.createSheet("selected");
		
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
			for (int i = 0; i < selected_list_heading.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(selected_list_heading[i]);
				cell.setCellStyle(headerCellStyle);
			}
		
			int rowNum = 1;
			if(null!=selectedList && !selectedList.isEmpty()) {
				for (RecruitmentOpportunityMapper selected : selectedList) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(selected.getJobOrder());
					row.createCell(1).setCellValue(selected.getRequirementName());
					row.createCell(2).setCellValue(selected.getCustomerName());
					row.createCell(3).setCellValue(selected.getSponserName());
					row.createCell(4).setCellValue(selected.getCreationDate());
					row.createCell(5).setCellValue(selected.getAvilableDate());
					row.createCell(6).setCellValue(selected.getSkillName());
					row.createCell(7).setCellValue(selected.getOffered());
					row.createCell(8).setCellValue(selected.getClosedPosition());
					row.createCell(9).setCellValue(selected.getOnBoardNo());
				}
			}
			// Resize all columns to fit the content size
					for (int i = 0; i < recruitment_heading.length; i++) {
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
