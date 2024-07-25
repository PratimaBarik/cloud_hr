package com.app.employeePortal.reports.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.app.employeePortal.Opportunity.service.OpportunityService;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.partner.service.PartnerService;
import com.app.employeePortal.reports.service.ReportService;
import com.app.employeePortal.task.mapper.TaskViewMapper;

@RestController
@CrossOrigin(maxAge = 3600)
public class CsvController {
	@Autowired
    private TokenProvider jwtTokenUtil;
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
	ReportService reportService;
	@Autowired
	ContactRepository contactRepository;
	
	@GetMapping("/api/v1/csv/export/user/contact/{userId}")
	public ResponseEntity<?> exportContactCsvReportsByUserId(@PathVariable("userId") String userId,
			  HttpServletResponse response) throws FileNotFoundException, IOException {
		
		
	
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
		List<ContactViewMapper>  contactList = contactService. getContactListByUserId(userId,0,contactRepository.findAll().size());
		response.setContentType("text/csv");
		
		String currentDate = dateFormatter.format(new Date());
		String filename = "RDM_contact" + currentDate + ".csv";
		
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=" + filename);
		
		response.setHeader(headerKey, headerValue);
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"ContactId", "FirstName", "MiddleName","LastName","Mobile#","Phone#", "Email","Linkedin","Company","Department","Designation"
		};
		
		String[] nameMapping = {"contactId", "firstName", "middleName","lastName","mobileNumber","phoneNumber", "emailId","linkedinPublicUrl","tagWithCompany","department","designation"
		};
		
		csvWriter.writeHeader(csvHeader);
		
		if(null!=contactList && !contactList.isEmpty()) {
		for (ContactViewMapper contactViewMapper : contactList) {
			csvWriter.write(contactViewMapper, nameMapping);
		  }
		}
		csvWriter.close();
	}catch(Exception e) {
		e.printStackTrace();

	}
	
	return new ResponseEntity<>("successfully export to csv " ,HttpStatus.OK);
	
	}
	
	
	@GetMapping("/api/v1/csv/export/user/{userId}")
	public ResponseEntity<?> exportCsvReportsByUserId(@PathVariable("userId") String userId,
			                                   	@RequestParam(value = "type", required = true) String type,
												@RequestParam(value = "startDate", required = false) String startDate,
												@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
												HttpServletResponse response) throws IOException {
	
		String currentDate = null;
		String filename = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		
		if(type.equalsIgnoreCase("mileage")) {
		try {
		List<MileageMapper>  mileageList = reportService.getMileageListByUserIdWithDateRange(userId, startDate, endDate);
		response.setContentType("text/csv");
		
		 currentDate = dateFormatter.format(new Date());
		 filename = "RDM_Millage" + currentDate + ".csv";
		
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=" + filename);
		
		response.setHeader(headerKey, headerValue);
		
	//	ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"Mileage Id", "Mileage Date","Attributed To",  "From", "To","Distance","Mileage Rate","Remarks"};
		
		String[] nameMapping = {"mileageId", "mileageDate", "clientName","fromLocation","toLocation","distances", "mileageRate","remark"};
		
		csvWriter.writeHeader(csvHeader);
		
		if(null!=mileageList && !mileageList.isEmpty()) {
		for (MileageMapper mileageMapper : mileageList) {
			csvWriter.write(mileageMapper, nameMapping);
		  }
		}
		csvWriter.close();
	}catch(Exception e) {
		e.printStackTrace();

	}
		
	
	return new ResponseEntity<>("successfully millage export to csv " ,HttpStatus.OK);
	
		}else if(type.equalsIgnoreCase("expense")) {
			try {
				List<ExpenseMapper>  list = reportService.getExpenseListByUserIdWithDateRange(userId, startDate, endDate);
				response.setContentType("text/csv");
				
				 currentDate = dateFormatter.format(new Date());
				 filename = "RDM_Expense" + currentDate + ".csv";
				
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=" + filename);
				
				response.setHeader(headerKey, headerValue);
				
		//		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
				
				String[] csvHeader = {"Expense Id","Expense Date", "Attributed To", "Expense Type", "Amount", "Particulars"};
				
				String[] nameMapping = {"expenseId", "expenseDate", "clientName","billType","amount","particular"};
				
				csvWriter.writeHeader(csvHeader);
				
				if(null!=list && !list.isEmpty()) {
				for (ExpenseMapper expenseMapper : list) {
					csvWriter.write(expenseMapper, nameMapping);
				  }
				}
				csvWriter.close();
			}catch(Exception e) {
				e.printStackTrace();

			}
			return new ResponseEntity<>("successfully expense export to csv " ,HttpStatus.OK);
			
		}else if(type.equalsIgnoreCase("task")) {
			try {
				List<TaskViewMapper>  list = reportService.getTaskListByUserIdWithDateRange(userId, startDate, endDate);
				response.setContentType("text/csv");
				
				 currentDate = dateFormatter.format(new Date());
				 filename = "RDM_Task" + currentDate + ".csv";
				
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=" + filename);
				
				response.setHeader(headerKey, headerValue);
				
		//		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
				
				String[] csvHeader =  {"Name","Type", "Submitted By", "Assigned On", "Remarks", "Status"};
				
				String[] nameMapping = {"taskName", "taskType", "submittedBy","assignedOn","submittedBy","taskStatus"};
				
				csvWriter.writeHeader(csvHeader);
				
				if(null!=list && !list.isEmpty()) {
				for (TaskViewMapper mapper : list) {
					csvWriter.write(mapper, nameMapping);
				  }
				}
				csvWriter.close();
			}catch(Exception e) {
				e.printStackTrace();

			}
			return new ResponseEntity<>("successfully task export to csv " ,HttpStatus.OK);

		}else if(type.equalsIgnoreCase("leave")) {

			try {
				List<LeavesMapper>  list = reportService.getLeavesListByUserIdWithDateRange(userId, startDate, endDate);
				response.setContentType("text/csv");
				
				 currentDate = dateFormatter.format(new Date());
				 filename = "RDM_Leave" + currentDate + ".csv";
				
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=" + filename);
				
				response.setHeader(headerKey, headerValue);
				
		//		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
				
				String[] csvHeader =  {"Start Date","End Date", "Cover", "Reason", "Status"};
				
				String[] nameMapping = {"startDate", "endDate", "coverDetails","reason","status"};
				
				csvWriter.writeHeader(csvHeader);
				
				if(null!=list && !list.isEmpty()) {
				for (LeavesMapper mapper : list) {
					csvWriter.write(mapper, nameMapping);
				  }
				}
				csvWriter.close();
			}catch(Exception e) {
				e.printStackTrace();

			}
			return new ResponseEntity<>("successfully leave export to csv " ,HttpStatus.OK);
		}
		return null;
	}
}
