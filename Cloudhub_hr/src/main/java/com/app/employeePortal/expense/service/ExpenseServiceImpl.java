package com.app.employeePortal.expense.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.api.service.CurrencyExchangeService;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.expense.entity.ExpenseDetails;
import com.app.employeePortal.expense.entity.ExpenseInfo;
import com.app.employeePortal.expense.entity.ExpenseNotesLink;
import com.app.employeePortal.expense.entity.ExpenseType;
import com.app.employeePortal.expense.entity.ExpenseTypeDelete;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.expense.repository.ExpenseInfoRepository;
import com.app.employeePortal.expense.repository.ExpenseNotesLinkRepository;
import com.app.employeePortal.expense.repository.ExpenseRepository;
import com.app.employeePortal.expense.repository.ExpenseTypeDeleteRepository;
import com.app.employeePortal.expense.repository.ExpenseTypeRepository;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.processApproval.service.ProcessApprovalService;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskInfo;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskInfoRepository;
import com.app.employeePortal.util.Utility;
import com.app.employeePortal.voucher.entity.VoucherDetails;
import com.app.employeePortal.voucher.entity.VoucherExpenseLink;
import com.app.employeePortal.voucher.entity.VoucherInfo;
import com.app.employeePortal.voucher.mapper.VoucherMapper;
import com.app.employeePortal.voucher.repository.VoucherExpenseRepository;
import com.app.employeePortal.voucher.repository.VoucherInfoRepository;
import com.app.employeePortal.voucher.repository.VoucherRepository;
import com.app.employeePortal.voucher.service.VoucherService;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	ExpenseRepository expenseRepository;

	@Autowired
	ExpenseInfoRepository expenseInfoRepository;

	@Autowired
	VoucherInfoRepository voucherInfoRepository;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	VoucherExpenseRepository voucherExpenseRepository;
	@Autowired
	TaskInfoRepository taskInfoRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;

	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService empService;
	@Autowired
	CurrencyExchangeService currencyExchangeService;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	ExpenseTypeRepository expenseTypeRepository;
	@Autowired
	ExpenseTypeDeleteRepository expenseTypeDeleteRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired EmailService emailService;
	@Autowired
	VoucherService voucherService;
	@Autowired
	ProcessApprovalService processApprovalService;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	ExpenseNotesLinkRepository expenseNotesLinkRepository;
	private String[] expense_headings = {"Name"};

	@Override
	public VoucherMapper saveToExpenseProcess(List<ExpenseMapper> expenseList, String userId, String orgId) {

		String voucherId = null;
		String exchangeCurrency = null;
//		EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(userId, true);
//
//		if (null != details.getCurrency()) {
//			exchangeCurrency = details.getCurrency();
//		} else {
//			exchangeCurrency = "INR";
//		}

//		public void addValueToCurrency(Map<String, List<Double>> currencyMap, String currencyCode, double value) {
//	        currencyMap.computeIfAbsent(currencyCode, k -> new ArrayList<>()).add(value);
//	    }


	 Map<String, List<Double>> currencyMap = new HashMap<>();
		
		if (null != expenseList && !expenseList.isEmpty()) {
			double voucher_amount = 0;

			/* insert to voucher-info */

			VoucherInfo voucherInfo = new VoucherInfo();
			voucherInfo.setCreation_date(new Date());
			VoucherInfo info = voucherInfoRepository.save(voucherInfo);

			voucherId = info.getVoucher_id();

			for (ExpenseMapper expenseMapper : expenseList) {
				ExpenseInfo expenseInfo = new ExpenseInfo();
				expenseInfo.setCreation_date(new Date());

				ExpenseInfo expenseInfoo = expenseInfoRepository.save(expenseInfo);
				String expenseId = expenseInfoo.getExpense_id();

				if (null != expenseId) {
					exchangeCurrency = expenseMapper.getCurrency();
					ExpenseDetails expenseDetails = new ExpenseDetails();
					expenseDetails.setExpense_id(expenseId);
					// expenseDetails.setAdjusted_amount(expenseMapper.getAdjustedAmount());
					expenseDetails.setAmount(expenseMapper.getAmount());
					expenseDetails.setExpense_type(expenseMapper.getExpenseType());
					expenseDetails.setClient_name(expenseMapper.getClientName());
					expenseDetails.setCreation_date(new Date());
					expenseDetails.setCurrency(expenseMapper.getCurrency());
					expenseDetails.setMoreInfo(expenseMapper.getMoreInfo());

					try {
						expenseDetails.setExpense_date(Utility.getDateFromISOString(expenseMapper.getExpenseDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					expenseDetails.setOrganization_id(orgId);
					expenseDetails.setUser_id(userId);
					expenseDetails.setParticular(expenseMapper.getParticular());
					expenseDetails.setProject_name(expenseMapper.getProjectName());
					expenseDetails.setLive_ind(true);

					if (!StringUtils.isEmpty(expenseMapper.getDocumentId())) {
						expenseDetails.setDocumentId(expenseMapper.getDocumentId());

						// update document resource type

					}

					 expenseRepository.save(expenseDetails);
					//String expenseDetailsId = expenseDetailss.getExpense_details_id();


					VoucherExpenseLink voucherExpenseLink = new VoucherExpenseLink();
					voucherExpenseLink.setExpense_id(expenseId);
					voucherExpenseLink.setVoucher_id(voucherId);
					voucherExpenseLink.setCreation_date(new Date());
					voucherExpenseLink.setLive_ind(true);
					voucherExpenseRepository.save(voucherExpenseLink);

					// currencyExchangeService.getExchangePrice(expenseMapper.getCurrency(),
					// exchangeCurrency, expenseMapper.getAmount(), Utility.removeTime(new Date()));
					// voucher_amount =
					// voucher_amount+currencyExchangeService.getExchangePrice(expenseMapper.getCurrency(),
					// exchangeCurrency, expenseMapper.getAmount(), Utility.removeTime(new
					// Date()));;
					
					currencyMap.computeIfAbsent(expenseMapper.getCurrency(), k -> new ArrayList<>()).add(expenseMapper.getAmount());
					
					voucher_amount = voucher_amount + expenseMapper.getAmount();
					System.out.println("xpnse====" + voucher_amount);
				}
			}

			/* insert to task info */
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setCreation_date(new Date());
			TaskInfo task = taskInfoRepository.save(taskInfo);

			String taskId = task.getTask_id();

			if (null != taskId) {

				/* insert to task details table */

				TaskDetails taskDetails = new TaskDetails();
				taskDetails.setTask_id(taskId);
				taskDetails.setCreation_date(new Date());
				taskDetails.setPriority("Medium");
				taskDetails.setTask_type("expense");
				taskDetails.setTask_name("expense");
				taskDetails.setTask_status("To Start");
				taskDetails.setUser_id(userId);
				taskDetails.setOrganization_id(orgId);
				taskDetails.setLiveInd(true);
				// taskDetails.setCompletion_ind(false);
				try {
					taskDetails.setStart_date(new Date());
					taskDetails.setEnd_date(new Date());

				} catch (Exception e) {
					e.printStackTrace();
				}

				taskDetailsRepository.save(taskDetails);
				
				/* insert to employee task link table */
				processApprovalService.ProcessApprove( userId, "Expense", taskDetails);

			

			/* insert to employee task link table */

	/*sb		String hrId = empService.getHREmployeeId(orgId);
			String adminId = empService.getAdminIdByOrgId(orgId);
			String reportingManagerId = null;

			if (null != details) {
				if (!StringUtils.isEmpty(details.getReportingManager())) {
					reportingManagerId = details.getReportingManager();
				} else if (StringUtils.isEmpty(details.getReportingManager()) && !StringUtils.isEmpty(hrId)) {
					reportingManagerId = hrId;

				} else if (StringUtils.isEmpty(hrId)) {
					reportingManagerId = adminId;
				}

			}

			EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
			employeeTaskLink.setTask_id(taskId);
			employeeTaskLink.setCreation_date(new Date());
			employeeTaskLink.setLive_ind(true);
			employeeTaskLink.setEmployee_id(reportingManagerId);
			employeeTaskRepository.save(employeeTaskLink); sb*/
			
			
			
			String totalAmount = "";

			
			/* insert to voucher details */
			VoucherDetails voucherDetails = new VoucherDetails();
			voucherDetails.setUser_id(userId);
			voucherDetails.setOrganization_id(orgId);
			voucherDetails.setStatus("Pending");
			voucherDetails.setAmount(voucher_amount);
			voucherDetails.setCreation_date(new Date());
			voucherDetails.setSubmitted_by(userId);
			voucherDetails.setCurrency(exchangeCurrency);
			voucherDetails.setVoucher_date(new Date());
			voucherDetails.setVoucher_type("Expense");
			voucherDetails.setLive_ind(true);
			voucherDetails.setVoucher_id(voucherId);
			voucherDetails.setTask_id(taskId);
			voucherDetails.setVoucherName(expenseList.get(0).getVoucherName());
			
			// Calculate and print the total for each currency
	        for (Map.Entry<String, List<Double>> entry : currencyMap.entrySet()) {
	            String currencyCode = entry.getKey();
	            List<Double> values = entry.getValue();
	            double total = calculateTotal(values);
	            totalAmount= totalAmount+total+" "+currencyCode+"+ ";
	            
	            System.out.println("Total " + currencyCode + ": " + total);
	            System.out.println("totalAmount " + ": " + totalAmount);
	        }
	        voucherDetails.setTotalAmount(totalAmount);
			voucherRepository.save(voucherDetails);
			}
		}
		VoucherMapper resultMapper=voucherService.getVoucherDetailsById(voucherId);
		
		return resultMapper;
	}

	private double calculateTotal(List<Double> values) {
		double total = 0.0;
        for (double value : values) {
            total += value;
        }
        return total;
	}

	@Override
	public ExpenseMapper getExpenseRelatedDetails(String expenseId) {

		ExpenseDetails expenseDetails = expenseRepository.getExpenseDetailsById(expenseId);
		ExpenseMapper expenseMapper = new ExpenseMapper();

		if (null != expenseDetails) {

			expenseMapper.setExpenseId(expenseId);
			expenseMapper.setAdjustedAmount(expenseDetails.getAdjusted_amount());
			expenseMapper.setAmount(expenseDetails.getAmount());
			//expenseMapper.setExpenseType(expenseDetails.getExpense_type());
			expenseMapper.setClientName(expenseDetails.getClient_name());
			expenseMapper.setCurrency(expenseDetails.getCurrency());
			expenseMapper.setExpenseDate(Utility.getISOFromDate(expenseDetails.getExpense_date()));
			expenseMapper.setParticular(expenseDetails.getParticular());
			expenseMapper.setProjectName(expenseDetails.getProject_name());
			expenseMapper.setUserId(expenseDetails.getUser_id());
			expenseMapper.setOrgId(expenseDetails.getOrganization_id());
			expenseMapper.setCreationDate(Utility.getISOFromDate(expenseDetails.getCreation_date()));
			expenseMapper.setDocumentId(expenseDetails.getDocumentId());
			expenseMapper.setMoreInfo(expenseDetails.getMoreInfo());
			

			if (expenseDetails.getExpense_type() != null && expenseDetails.getExpense_type().trim().length() > 0) {
				ExpenseType expenseType = expenseTypeRepository.findByExpenseTypeId(expenseDetails.getExpense_type());
				if (null!=expenseType) {
					expenseMapper.setExpenseType(expenseType.getExpenseType());
					expenseMapper.setExpenseTypeId(expenseType.getExpenseTypeId());
				}
			}
			else {
				expenseMapper.setExpenseType(expenseDetails.getExpense_type());
			}

			VoucherExpenseLink voucherExpenseLink = voucherExpenseRepository.getByExpenseId(expenseDetails.getExpense_id());
	 		if(null!=voucherExpenseLink) {
	 			expenseMapper.setVoucherId(voucherExpenseLink.getVoucher_id());
	 			VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherExpenseLink.getVoucher_id());
				if(null!=voucherDetails) {
					expenseMapper.setStatus(voucherDetails.getStatus());
				}
	 		}
		}

		return expenseMapper;

	}

	@Override
	public List<ExpenseMapper> getExpenseDetailsListByUserId(String userId) {
		return expenseRepository.getExpenseListByUserId(userId).stream().map(li->getExpenseRelatedDetailsMapper(li))
		.sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate()))
		.collect(Collectors.toList());
	}

	@Override
	public List<ExpenseMapper> getExpenseDetailsListByOrganizationId(String organizationId) {
		
		return expenseRepository.getExpenseListByOrganizationId(organizationId).stream()
				.map(li->getExpenseRelatedDetailsMapper(li))
				.sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate()))
				.collect(Collectors.toList());
	}

	@Override
	public List<ExpenseMapper> getExpenseListByVoucherId(String voucherId) {
		return voucherExpenseRepository.getExpenseListByVoucherId(voucherId).stream()
				.map(li->getExpenseRelatedDetails(li.getExpense_id()))
				.sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate()))
				.collect(Collectors.toList());
	}
	
	public ExpenseMapper getExpenseRelatedDetailsByVoucherIdAndExpenseId(String expenseId,String voucherId) {

		ExpenseDetails expenseDetails = expenseRepository.getExpenseDetailsById(expenseId);
		ExpenseMapper expenseMapper = new ExpenseMapper();

		if (null != expenseDetails) {

			expenseMapper.setExpenseId(expenseId);
			expenseMapper.setAdjustedAmount(expenseDetails.getAdjusted_amount());
			expenseMapper.setAmount(expenseDetails.getAmount());
			//expenseMapper.setExpenseType(expenseDetails.getExpense_type());
			expenseMapper.setClientName(expenseDetails.getClient_name());
			expenseMapper.setCurrency(expenseDetails.getCurrency());
			expenseMapper.setExpenseDate(Utility.getISOFromDate(expenseDetails.getExpense_date()));
			expenseMapper.setParticular(expenseDetails.getParticular());
			expenseMapper.setProjectName(expenseDetails.getProject_name());
			expenseMapper.setUserId(expenseDetails.getUser_id());
			expenseMapper.setOrgId(expenseDetails.getOrganization_id());
			expenseMapper.setCreationDate(Utility.getISOFromDate(expenseDetails.getCreation_date()));
			expenseMapper.setDocumentId(expenseDetails.getDocumentId());

			if (expenseDetails.getExpense_type() != null && expenseDetails.getExpense_type().trim().length() > 0) {
				ExpenseType expenseType = expenseTypeRepository.findByExpenseTypeId(expenseDetails.getExpense_type());

				expenseMapper.setExpenseType(expenseType.getExpenseType());
				expenseMapper.setExpenseTypeId(expenseType.getExpenseTypeId());
			}
			else {
				expenseMapper.setExpenseType(expenseDetails.getExpense_type());
			}
			
			VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherId);
			if(null!=voucherDetails) {
				expenseMapper.setStatus(voucherDetails.getStatus());
			}
			
		}

		return expenseMapper;

	}

	@Override
	public ExpenseMapper updateExpenseDetails(ExpenseMapper expenseMapper) {
		ExpenseMapper resultMapper = null;

		if (null != expenseMapper.getExpenseId()) {

			ExpenseDetails expenseDetails = expenseRepository.getExpenseDetailsById(expenseMapper.getExpenseId());

			// if(expenseMapper.getAdjustedAmount()!=0)
			// expenseDetails.setAdjusted_amount(expenseMapper.getAdjustedAmount());
			if (null!=expenseDetails) {
				
				if (expenseMapper.getExpenseType() != null)
					expenseDetails.setExpense_type(expenseMapper.getExpenseType());
				if (expenseMapper.getClientName() != null)
					expenseDetails.setClient_name(expenseMapper.getClientName());
				if (expenseMapper.getCurrency() != null)
					expenseDetails.setCurrency(expenseMapper.getCurrency());
				if (expenseMapper.getExpenseDate() != null)
					try {
						expenseDetails.setExpense_date(Utility.getDateFromISOString(expenseMapper.getExpenseDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				if (expenseMapper.getParticular() != null)
					expenseDetails.setParticular(expenseMapper.getParticular());
				if (expenseMapper.getProjectName() != null)
					expenseDetails.setProject_name(expenseMapper.getProjectName());

			
			
				if(0!=expenseMapper.getAmount()) {
					 System.out.println("expenseMapper.getAmount()======"+expenseMapper.getAmount());
					 VoucherExpenseLink voucherExpenseLink = voucherExpenseRepository.getByExpenseId(expenseMapper.getExpenseId());
					 		if(null!=voucherExpenseLink) {
					 			 System.out.println("voucherMileageLink.getVoucher_id()======"+voucherExpenseLink.getVoucher_id());
					 VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherExpenseLink.getVoucher_id());
					 				if(null!=voucherDetails) {
					 					 System.out.println("voucherDetails.getAmount()======"+voucherDetails.getAmount());
					 					double final_amount=0;
					 					double voucher_amount = voucherDetails.getAmount()-(expenseDetails.getAmount());
					 					 System.out.println("voucher_amount======"+voucher_amount);
					 					final_amount = voucher_amount + (expenseMapper.getAmount());
					 					System.out.println("final_amount======"+final_amount);
					 					voucherDetails.setAmount(final_amount);
					 					voucherRepository.save(voucherDetails);
					 				}
					 			}
				 			}
				
				
			
					if (expenseMapper.getAmount() != 0)
						expenseDetails.setAmount(expenseMapper.getAmount());
			expenseRepository.save(expenseDetails);
			resultMapper = getExpenseRelatedDetails(expenseMapper.getExpenseId());
			}
		}

		return resultMapper;
	}

	@Override
	public List<ExpenseMapper> getExpenseListByUserIdWithDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		List<ExpenseMapper> resultList = new ArrayList<ExpenseMapper>();
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<ExpenseDetails> expenseList = expenseRepository.getExpensesByEmployeeIdWithDateRange(userId, start_date,
				end_date);

		if (null != expenseList && !expenseList.isEmpty()) {

				expenseList.forEach(expenseDetails->{
				ExpenseMapper expenseMapper = new ExpenseMapper();
				expenseMapper.setAmount(expenseDetails.getAmount());
				// expenseMapper.setCreationDate(Utility.getISOFromDate(expenseDetails.getCreation_date()));
				expenseMapper.setExpenseType(expenseDetails.getExpense_type());
				expenseMapper.setClientName(expenseDetails.getClient_name());
				expenseMapper.setCurrency(expenseDetails.getCurrency());
				expenseMapper.setExpenseDate(Utility.getISOFromDate(expenseDetails.getExpense_date()));
				expenseMapper.setExpenseId(expenseDetails.getExpense_id());
				expenseMapper.setParticular(expenseDetails.getParticular());
				resultList.add(expenseMapper);
			});

		}
		return resultList;
	}

	@Override
	public List<ExpenseMapper> getExpenseListByOrgIdWithDateRange(String orgId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
			start_date = Utility.getDateFromISOString(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return expenseRepository.getExpensesByOrgIdWithDateRange(orgId, start_date,end_date)
		.stream().map(one->getExpenseRelatedDetailsMapper(one))
		.collect(Collectors.toList());
	}

	@Override
	public String saveExpenseType(ExpenseMapper expenseMapper) {
		String expenseTypeId = null;

		if (expenseMapper != null) {
			ExpenseType expenseType = new ExpenseType();

			expenseType.setExpenseType(expenseMapper.getExpenseType());
			expenseType.setCreationDate(new Date());
			expenseType.setUserId(expenseMapper.getUserId());
			expenseType.setOrgId(expenseMapper.getOrgId());
			expenseType.setEditInd(expenseMapper.isEditInd());
			expenseType.setLiveInd(true);

			ExpenseType dbExpenseType = expenseTypeRepository.save(expenseType);
			expenseTypeId = dbExpenseType.getExpenseTypeId();
			
			ExpenseTypeDelete expenseTypeDelete=new ExpenseTypeDelete();
			expenseTypeDelete.setExpenseTypeId(expenseTypeId);
			expenseTypeDelete.setUserId(expenseMapper.getUserId());
			expenseTypeDelete.setOrgId(expenseMapper.getOrgId());
			expenseTypeDelete.setUpdationDate(new Date());
			expenseTypeDelete.setUpdatedBy(expenseMapper.getUserId());
			expenseTypeDeleteRepository.save(expenseTypeDelete);
		}

		return expenseTypeId;
	}

	@Override
	public List<ExpenseMapper> getExpenseTypeByOrgId(String orgId) {
		List<ExpenseMapper> resultList = new ArrayList<ExpenseMapper>();
		List<ExpenseType> expenseTypeList = expenseTypeRepository.findByOrgIdAndLiveInd(orgId,true);

		if (null != expenseTypeList && !expenseTypeList.isEmpty()) {
				expenseTypeList.stream().map(expenseType->{
				ExpenseMapper expenseMapper = new ExpenseMapper();
				expenseMapper.setExpenseTypeId(expenseType.getExpenseTypeId());
				expenseMapper.setExpenseType(expenseType.getExpenseType());
				expenseMapper.setEditInd(expenseType.isEditInd());
				expenseMapper.setCreationDate(Utility.getISOFromDate(expenseType.getCreationDate()));
				
				resultList.add(expenseMapper);
				return resultList;
				}).collect(Collectors.toList());		
			}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<ExpenseTypeDelete> expenseTypeDelete = expenseTypeDeleteRepository.findByOrgId(orgId);
		if (null != expenseTypeDelete && !expenseTypeDelete.isEmpty()) {
			Collections.sort(expenseTypeDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(expenseTypeDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(expenseTypeDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					resultList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					resultList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}

		
		return resultList;
	}

	@Override
	public ExpenseMapper updateExpenseType(String expenseTypeId, ExpenseMapper expenseMapper) {
		ExpenseMapper resultMapper = null;

		if (null != expenseMapper.getExpenseTypeId()) {

			ExpenseType expenseType = expenseTypeRepository.findByExpenseTypeId(expenseMapper.getExpenseTypeId());

			if (null != expenseType.getExpenseType()) {
				expenseType.setExpenseType(expenseMapper.getExpenseType());
				expenseType.setEditInd(expenseMapper.isEditInd());
				expenseTypeRepository.save(expenseType);
				
				ExpenseTypeDelete expenseTypeDelete=expenseTypeDeleteRepository.findByExpenseTypeId(expenseMapper.getExpenseTypeId());
				if (null !=expenseTypeDelete) {
				expenseTypeDelete.setUpdationDate(new Date());
				expenseTypeDelete.setUpdatedBy(expenseMapper.getUserId());
				expenseTypeDeleteRepository.save(expenseTypeDelete);
				}else {
					ExpenseTypeDelete expenseTypeDelete1=new ExpenseTypeDelete();
					expenseTypeDelete1.setExpenseTypeId(expenseTypeId);
					expenseTypeDelete1.setUserId(expenseMapper.getUserId());
					expenseTypeDelete1.setOrgId(expenseMapper.getOrgId());
					expenseTypeDelete1.setUpdationDate(new Date());
					expenseTypeDelete1.setUpdatedBy(expenseMapper.getUserId());
					expenseTypeDeleteRepository.save(expenseTypeDelete1);
				}
					
			}
			resultMapper = getExpenseById(expenseMapper.getExpenseTypeId());
		}
		return resultMapper;
	}

	@Override
	public ExpenseMapper getExpenseById(String expenseTypeId) {
		ExpenseType expenseType = expenseTypeRepository.findByExpenseTypeId(expenseTypeId);
		ExpenseMapper expenseMapper = new ExpenseMapper();

		if (null != expenseType) {
			expenseMapper.setExpenseTypeId(expenseType.getExpenseTypeId());

			expenseMapper.setExpenseType(expenseType.getExpenseType());
			expenseMapper.setOrgId(expenseType.getOrgId());
			expenseMapper.setUserId(expenseType.getUserId());
			expenseMapper.setEditInd(expenseType.isEditInd());
			expenseMapper.setCreationDate(Utility.getISOFromDate(expenseType.getCreationDate()));

			List<ExpenseTypeDelete> list=expenseTypeDeleteRepository.findByOrgId(expenseType.getOrgId());
			if(null!=list&&!list.isEmpty()) {
				Collections.sort(list,(p1,p2)->p2.getUpdationDate().compareTo(p1.getUpdationDate()));
				
				expenseMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				expenseMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return expenseMapper;
	}

	@Override
	public List<ExpenseMapper> getExpenseTypeByNameByOrgLevel(String name, String orgId) {
	return	expenseTypeRepository.findByExpenseTypeContainingAndOrgId(name,orgId)
		.stream().map(one->getExpenseBMapper(one))
		.collect(Collectors.toList());
	}

	@Override
	public boolean checkExpenseNameInExpenseTypeByOrgLevel(String expenseType, String orgId) {
		List<ExpenseType> expenseTypes = expenseTypeRepository.findByExpenseTypeAndLiveIndAndOrgId(expenseType,true,orgId);
		if (!expenseTypes.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteExpenseTypeById(String expenseTypeId) {
		if (null != expenseTypeId) {
			ExpenseType expenseType = expenseTypeRepository.findByExpenseTypeId(expenseTypeId);
			
			ExpenseTypeDelete expenseTypeDelete = expenseTypeDeleteRepository.findByExpenseTypeId(expenseTypeId);
			if(null!=expenseTypeDelete) {
				expenseTypeDelete.setUpdationDate(new Date());
				expenseTypeDelete.setUpdatedBy(expenseType.getUserId());
				expenseTypeDeleteRepository.save(expenseTypeDelete);
			}
			expenseType.setLiveInd(false);
			expenseTypeRepository.save(expenseType);
		}

	}
	
	@Override
	public ExpenseMapper getExpenseRelatedDetailsMapper(ExpenseDetails expenseDetails) {
		ExpenseMapper expenseMapper = new ExpenseMapper();
		if (null != expenseDetails) {

			expenseMapper.setExpenseId(expenseDetails.getExpense_id());
			expenseMapper.setAdjustedAmount(expenseDetails.getAdjusted_amount());
			expenseMapper.setAmount(expenseDetails.getAmount());
			expenseMapper.setExpenseType(expenseDetails.getExpense_type());
			expenseMapper.setClientName(expenseDetails.getClient_name());
			expenseMapper.setCurrency(expenseDetails.getCurrency());
			expenseMapper.setExpenseDate(Utility.getISOFromDate(expenseDetails.getExpense_date()));
			expenseMapper.setParticular(expenseDetails.getParticular());
			expenseMapper.setProjectName(expenseDetails.getProject_name());
			expenseMapper.setUserId(expenseDetails.getUser_id());
			expenseMapper.setOrgId(expenseDetails.getOrganization_id());
			expenseMapper.setCreationDate(Utility.getISOFromDate(expenseDetails.getCreation_date()));
			expenseMapper.setDocumentId(expenseDetails.getDocumentId());

			if (expenseDetails.getExpense_type() != null && expenseDetails.getExpense_type().trim().length() > 0) {
				ExpenseType expenseType = expenseTypeRepository.findByExpenseTypeId(expenseDetails.getExpense_type());

				expenseMapper.setExpenseType(expenseType.getExpenseType());
				expenseMapper.setExpenseTypeId(expenseType.getExpenseTypeId());
			}
		}

		return expenseMapper;

	}
	
	
	@Override
	public ExpenseMapper getExpenseBMapper(ExpenseType expenseType) {
		ExpenseMapper expenseMapper = new ExpenseMapper();
		if (null != expenseType) {
			expenseMapper.setExpenseTypeId(expenseType.getExpenseTypeId());

			expenseMapper.setExpenseType(expenseType.getExpenseType());
			expenseMapper.setOrgId(expenseType.getOrgId());
			expenseMapper.setUserId(expenseType.getUserId());
			expenseMapper.setEditInd(expenseType.isEditInd());
			expenseMapper.setCreationDate(Utility.getISOFromDate(expenseType.getCreationDate()));
		}
		return expenseMapper;
	}
	
	@Override
	public String deleteExpense(String expenseId) {
	String message = null;
		ExpenseDetails expenseDetails = expenseRepository.getExpenseDetailsById(expenseId);

		if (null!=expenseDetails) {
			expenseDetails.setLive_ind(false);
			expenseRepository.save(expenseDetails);
			VoucherExpenseLink voucherExpenseLink = voucherExpenseRepository.getByExpenseId(expenseDetails.getExpense_id());
	 		if(null!=voucherExpenseLink) {
	 			voucherExpenseLink.setLive_ind(false);
	 			voucherExpenseRepository.save(voucherExpenseLink);
	 			
	 			List<VoucherExpenseLink> voucherExpenseLink1 = voucherExpenseRepository.getExpenseListByVoucherId(voucherExpenseLink.getVoucher_id());
	 			if (voucherExpenseLink1.size()==0) {
	 				VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherExpenseLink.getVoucher_id());
					if(null!=voucherDetails) {
						voucherDetails.setLive_ind(false);
						voucherRepository.save(voucherDetails);
						TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(voucherDetails.getTask_id());
							if(null!=taskDetails) {
								taskDetails.setLiveInd(false);
								taskDetailsRepository.save(taskDetails);
							}
						}
	 			}
	 		}
	 		message = "Expense Deleted Successfully";
		}

		return message;
	}

	@Override
	public String deleteVoucher(String voucherId) {
		String message = null;
		VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherId);
		if(null!=voucherDetails) {
			voucherDetails.setLive_ind(false);
			voucherRepository.save(voucherDetails);
			
			List<VoucherExpenseLink> voucherExpenseLink1 = voucherExpenseRepository.getExpenseListByVoucherId(voucherId);
			if (null!=voucherExpenseLink1 && !voucherExpenseLink1.isEmpty()) {
				for(VoucherExpenseLink voucherExpenseLink : voucherExpenseLink1) {
				
					voucherExpenseLink.setLive_ind(false);
					voucherExpenseRepository.save(voucherExpenseLink);
					
					ExpenseDetails expenseDetails = expenseRepository.getExpenseDetailsById(voucherExpenseLink.getExpense_id());

					if (null!=expenseDetails) {
						expenseDetails.setLive_ind(false);
						expenseRepository.save(expenseDetails);
					}
				}
 			}
			TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(voucherDetails.getTask_id());
			if(null!=taskDetails) {
			taskDetails.setLiveInd(false);
			taskDetailsRepository.save(taskDetails);
			}
		message = "Voucher Deleted Successfully";
		}
		return message;
	}

	@Override
	public List<VoucherMapper> getExpenseStatusListByUserId(String userId, String status, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creation_date").descending());
		List<VoucherMapper> resulList=new ArrayList<>();
			Page<VoucherDetails> list = voucherRepository.getVoucherListByUserIdAndVoucherTypeAndStatus(userId,"Expense",status,paging);
			if(null!=list && !list.isEmpty()) {
				 list.stream().map(voucherDetails->{
					VoucherMapper voucherMapper = voucherService.getVoucherDetailsById(voucherDetails.getVoucher_id());
					voucherMapper.setPageCount(list.getTotalPages());
					voucherMapper.setDataCount(list.getSize());
					voucherMapper.setListCount(list.getTotalElements());
			        
					resulList.add(voucherMapper);
					return resulList;
				}).collect(Collectors.toList());
			
		}
		return resulList;
	}

	@Override
	public String saveExpenseNotes(NotesMapper notesMapper) {

		String expenseNotesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			expenseNotesId = note.getNotes_id();

			/* insert to leads-notes-link */  

			ExpenseNotesLink leaveNotesLink = new ExpenseNotesLink();
			leaveNotesLink.setExpenseId(notesMapper.getExpenseId());
			leaveNotesLink.setNoteId(expenseNotesId);
			leaveNotesLink.setCreationDate(new Date());

			expenseNotesLinkRepository.save(leaveNotesLink);

		}
		return expenseNotesId;

	}

	@Override
	public List<NotesMapper> getNoteListByExpenseId(String expenseId) {
		List<ExpenseNotesLink> leadsNotesLinkList = expenseNotesLinkRepository.getNotesIdByExpenseId(expenseId);
		if (leadsNotesLinkList != null && !leadsNotesLinkList.isEmpty()) {
			return leadsNotesLinkList.stream().map(leadsNotesLink->{
				NotesMapper notesMapper = getNotes(leadsNotesLink.getNoteId());
				return notesMapper;
			}).collect(Collectors.toList());
		}
		return null;
	}
	private NotesMapper getNotes(String id) {
		Notes notes = notesRepository.findByNoteId(id);
		NotesMapper notesMapper = new NotesMapper();
		if(null!=notes) {
		notesMapper.setNotesId(notes.getNotes_id());
		notesMapper.setNotes(notes.getNotes());
		notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
		if (!StringUtils.isEmpty(notes.getUserId())) {
			EmployeeDetails employeeDetails = employeeRepository.getEmployeesByuserId(notes.getUserId());
			String fullName = "";
			String middleName = "";
			String lastName = "";
			if (null != employeeDetails.getMiddleName()) {

				middleName = employeeDetails.getMiddleName();
			}
			if (null != employeeDetails.getLastName()) {
				lastName = employeeDetails.getLastName();
			}
			fullName = employeeDetails.getFirstName() + " " + middleName + " " + lastName;
			notesMapper.setOwnerName(fullName);
		}
		}
		return notesMapper;

	}

//	@Override
//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper) {
//
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//		return resultMapper;
//	}
	
	@Override
	public void deleteExpenseNotesById(String notesId) {
		ExpenseNotesLink notesList = expenseNotesLinkRepository.findByNoteId(notesId);
		if (null != notesList) {
			
			Notes notes = notesRepository.findByNoteId(notesId);
			if (null!=notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}		
	}

	@Override
	public HashMap getExpenseTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<ExpenseType> list = expenseTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("ExpenseTypeCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportExpenseListToExcel(List<ExpenseMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

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
		for (int i = 0; i < expense_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(expense_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (ExpenseMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getExpenseType());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < expense_headings.length; i++) {
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
