package com.app.employeePortal.processApproval.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.leave.entity.LeaveDetails;
import com.app.employeePortal.leave.repository.LeaveDetailsRepository;
import com.app.employeePortal.notification.entity.NotificationDetails;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.processApproval.entity.ApprovalLevel;
import com.app.employeePortal.processApproval.mapper.ApprovalLevelMapper;
import com.app.employeePortal.processApproval.mapper.ProcessApprovalMapper;
import com.app.employeePortal.processApproval.mapper.ProcessApprovalViewMapper;
import com.app.employeePortal.processApproval.repository.ApprovalLevelRepository;
import com.app.employeePortal.processApproval.repository.ProcessApprovalRepository;
import com.app.employeePortal.processApproval.repository.SubProcessApprovalRepository;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.repository.DesignationRepository;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.repository.ApproveTaskLinkRepository;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskInfoRepository;
import com.app.employeePortal.voucher.entity.VoucherDetails;
import com.app.employeePortal.voucher.repository.VoucherRepository;

@Service
@Transactional
public class ProcessApprovalServiceImpl implements ProcessApprovalService {

	@Autowired
	ProcessApprovalRepository processApprovalRepository;
	@Autowired
	SubProcessApprovalRepository subProcessApprovalRepository;
	@Autowired
	ApprovalLevelRepository approvalLevelRepository;
	@Autowired
	DesignationRepository designationRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	TaskInfoRepository taskInfoRepository;
	@Autowired
	ApproveTaskLinkRepository approveTaskLinkRepository;
	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	RoleTypeRepository roleTypeRepository;
	@Autowired
	LeaveDetailsRepository leaveDetailsRepository;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	ContactService contactService;

	@Override
	public ProcessApprovalViewMapper saveProcessApproval(ProcessApprovalMapper processApprovalMapper) {
		
		ProcessApprovalViewMapper resultMapper = new ProcessApprovalViewMapper();

		ApprovalLevel approvalLevel = approvalLevelRepository
				.findBySubProcessApprovalIdAndLiveInd(processApprovalMapper.getSubProcessName(), true);

		if (null != approvalLevel) {

			if (processApprovalMapper.isApprovalIndicator() == false) {

				approvalLevel.setLiveInd(false);
				approvalLevelRepository.save(approvalLevel);
			} else {

				approvalLevel.setLiveInd(false);
				approvalLevelRepository.save(approvalLevel);

				ApprovalLevel dbApprovalLevel = new ApprovalLevel();

				if (processApprovalMapper.getApprovalType().equalsIgnoreCase("standard")) {
//					dbApprovalLevel.setReportingTo(processApprovalMapper.getLevel1());
//					dbApprovalLevel.setReportingTo2(processApprovalMapper.getLevel2());
//					dbApprovalLevel.setReportingTo3(processApprovalMapper.getLevel3());
//					dbApprovalLevel.setReportingTo4(processApprovalMapper.getLevel4());
//					dbApprovalLevel.setReportingTo5(processApprovalMapper.getLevel5());
//					dbApprovalLevel.setThreshold1(processApprovalMapper.getThreshold1());
//					dbApprovalLevel.setThreshold2(processApprovalMapper.getThreshold2());
//					dbApprovalLevel.setThreshold3(processApprovalMapper.getThreshold3());
//					dbApprovalLevel.setThreshold4(processApprovalMapper.getThreshold4());
//					dbApprovalLevel.setThreshold5(processApprovalMapper.getThreshold5());
					
					 if (null != processApprovalMapper.getLevel() && !processApprovalMapper.getLevel().isEmpty()) {

						 int count=1;
		                    for (ApprovalLevelMapper level : processApprovalMapper.getLevel()) {
		                    	if(count==1) {
		                    		System.out.println("count=="+count);
		                    	dbApprovalLevel.setReportingTo(level.getLevel());
		                    	dbApprovalLevel.setRoleType(level.getRoleType());
		                    	dbApprovalLevel.setThreshold1(level.getThreshold());
		                    	}else if(count==2){
		                    		System.out.println("count=="+count);
		                    		dbApprovalLevel.setReportingTo2(level.getLevel());
		                    		dbApprovalLevel.setRoleType2(level.getRoleType());
			                    	dbApprovalLevel.setThreshold2(level.getThreshold());
		                    	}else if(count==3){
		                    		System.out.println("count=="+count);
		                    		dbApprovalLevel.setReportingTo3(level.getLevel());
		                    		dbApprovalLevel.setRoleType3(level.getRoleType());
			                    	dbApprovalLevel.setThreshold3(level.getThreshold());
		                    	}
		                    	count++;
		                    }
					 }

				} else if (processApprovalMapper.getApprovalType().equalsIgnoreCase("exception")) {
					dbApprovalLevel.setDepartmentId(processApprovalMapper.getDepartmentId());
					dbApprovalLevel.setDesignationId(processApprovalMapper.getRoleTypeId());
					dbApprovalLevel.setJobLevel(processApprovalMapper.getJobLevel());

				}
				dbApprovalLevel.setLevelCount(processApprovalMapper.getLevelCount());
				dbApprovalLevel.setApprovalType(processApprovalMapper.getApprovalType());
				dbApprovalLevel.setApprovalIndicator(processApprovalMapper.isApprovalIndicator());
				dbApprovalLevel.setCreationDate(new Date());
				dbApprovalLevel.setLiveInd(true);
				dbApprovalLevel.setSubProcessApprovalId(processApprovalMapper.getSubProcessName());

			 approvalLevelRepository.save(dbApprovalLevel).getId();

				resultMapper = getApproval(processApprovalMapper.getSubProcessName());
				return resultMapper;
			}

		} else {

			ApprovalLevel dbApprovalLevel = new ApprovalLevel();

			if (processApprovalMapper.getApprovalType().equalsIgnoreCase("standard")) {
				if (null != processApprovalMapper.getLevel() && !processApprovalMapper.getLevel().isEmpty()) {

					 int count=1;
	                    for (ApprovalLevelMapper level : processApprovalMapper.getLevel()) {
	                    	if(count==1) {
	                    		System.out.println("count=="+count);
	                    	dbApprovalLevel.setReportingTo(level.getLevel());
	                    	dbApprovalLevel.setRoleType(level.getRoleType());
	                    	dbApprovalLevel.setThreshold1(level.getThreshold());
	                    	}else if(count==2){
	                    		System.out.println("count=="+count);
	                    		dbApprovalLevel.setReportingTo2(level.getLevel());
	                    		dbApprovalLevel.setRoleType2(level.getRoleType());
		                    	dbApprovalLevel.setThreshold2(level.getThreshold());
	                    	}else if(count==3){
	                    		System.out.println("count=="+count);
	                    		dbApprovalLevel.setReportingTo3(level.getLevel());
	                    		dbApprovalLevel.setRoleType3(level.getRoleType());
		                    	dbApprovalLevel.setThreshold3(level.getThreshold());
	                    	}
	                    	count++;
	                    }
				 }

			} else if (processApprovalMapper.getApprovalType().equalsIgnoreCase("exception")) {
				dbApprovalLevel.setDepartmentId(processApprovalMapper.getDepartmentId());
				dbApprovalLevel.setDesignationId(processApprovalMapper.getRoleTypeId());
				dbApprovalLevel.setJobLevel(processApprovalMapper.getJobLevel());
			}
			
			dbApprovalLevel.setLevelCount(processApprovalMapper.getLevelCount());
			dbApprovalLevel.setApprovalType(processApprovalMapper.getApprovalType());
			dbApprovalLevel.setApprovalIndicator(processApprovalMapper.isApprovalIndicator());
			dbApprovalLevel.setCreationDate(new Date());
			dbApprovalLevel.setLiveInd(true);
			dbApprovalLevel.setSubProcessApprovalId(processApprovalMapper.getSubProcessName());

			approvalLevelRepository.save(dbApprovalLevel).getId();

			resultMapper = getApproval(processApprovalMapper.getSubProcessName());
			return resultMapper;

		}
		return null;
	}

	private ProcessApprovalViewMapper getApprovalLevelDetails(String approvalLevelId) {
		ProcessApprovalViewMapper resultMapper = new ProcessApprovalViewMapper();
		ApprovalLevel approvalLevel = approvalLevelRepository.findByIdAndLiveInd(approvalLevelId, true);
		if (null != approvalLevel) {
			resultMapper.setApprovalLevelId(approvalLevelId);
			resultMapper.setLevel1(approvalLevel.getReportingTo());
			resultMapper.setLevel2(approvalLevelId);
			resultMapper.setThreshold1(approvalLevel.getThreshold1());
			resultMapper.setThreshold2(approvalLevel.getThreshold2());
			resultMapper.setThreshold3(approvalLevel.getThreshold3());
			resultMapper.setThreshold4(approvalLevel.getThreshold4());
			resultMapper.setThreshold5(approvalLevel.getThreshold5());
			resultMapper.setLevel4(approvalLevel.getSubProcessApprovalId());
			// resultMapper.
			resultMapper.setApprovalLevelId(approvalLevel.getApprovalType());
		}
		return resultMapper;

	}

	@Override
	public ProcessApprovalViewMapper getApproval(String subProcessName) {

		ApprovalLevel approvalLevel = approvalLevelRepository.findBySubProcessApprovalIdAndLiveInd(subProcessName,
				true);
		ProcessApprovalViewMapper mapper = new ProcessApprovalViewMapper();

		if (null != approvalLevel) {
			mapper.setApprovalIndicator(approvalLevel.isApprovalIndicator());
			if (approvalLevel.getApprovalType().equalsIgnoreCase("exception")) {
				RoleType roleType = roleTypeRepository
						.findByRoleTypeId(approvalLevel.getDesignationId());
				mapper.setApprovalType("Exception");

				if (null != roleType) {
					mapper.setRoleType(roleType.getRoleType());
					mapper.setRoleTypeId(roleType.getRoleTypeId());
				}

				Department department = departmentRepository.getDepartmentDetails(approvalLevel.getDepartmentId());
				if (null != department) {
					mapper.setDepartmentId(department.getDepartment_id());
					mapper.setDepartmentName(department.getDepartmentName());
				}
			} else {
				mapper.setApprovalType("Standard");
				
				int count =approvalLevel.getLevelCount();
				List<ApprovalLevelMapper> result = new ArrayList<>();
				for (int i = 1; i <= count; i++) {
					ApprovalLevelMapper level = new ApprovalLevelMapper();
					if (!StringUtils.isEmpty(approvalLevel.getReportingTo()) && i == 1) {
						String departmentName = "";
							Department department = departmentRepository.getDepartmentDetails(approvalLevel.getReportingTo());
							departmentName = department != null ? department.getDepartmentName() : approvalLevel.getReportingTo();
						
						level.setLevel(departmentName);
						level.setThreshold(approvalLevel.getThreshold1());
						
						if (!StringUtils.isEmpty(approvalLevel.getRoleType())){
						RoleType roleType = roleTypeRepository
								.findByRoleTypeId(approvalLevel.getRoleType());
						level.setRoleType(roleType.getRoleType());
						}
						result.add(level);
					}else if (!StringUtils.isEmpty(approvalLevel.getReportingTo2()) && i == 2) {
						String departmentName = "";
						Department department = departmentRepository.getDepartmentDetails(approvalLevel.getReportingTo2());
						departmentName = department != null ? department.getDepartmentName() : approvalLevel.getReportingTo2();
						level.setLevel(departmentName);
						level.setThreshold(approvalLevel.getThreshold2());
						
						if (!StringUtils.isEmpty(approvalLevel.getRoleType2())){
						RoleType roleType = roleTypeRepository
								.findByRoleTypeId(approvalLevel.getRoleType2());
						level.setRoleType(roleType.getRoleType());
						}
						result.add(level);
					}else if (!StringUtils.isEmpty(approvalLevel.getReportingTo3()) && i == 3) {
						String departmentName = "";
						Department department = departmentRepository.getDepartmentDetails(approvalLevel.getReportingTo3());
						departmentName = department != null ? department.getDepartmentName() : approvalLevel.getReportingTo3();
						level.setLevel(departmentName);
						level.setThreshold(approvalLevel.getThreshold3());
						if (!StringUtils.isEmpty(approvalLevel.getRoleType3())){
						RoleType roleType = roleTypeRepository
								.findByRoleTypeId(approvalLevel.getRoleType3());
						level.setRoleType(roleType.getRoleType());
						}
						result.add(level);
					}
				}
				mapper.setLevel(result);
//				if (!StringUtils.isEmpty(approvalLevel.getReportingTo())) {
//					Department department = departmentRepository.getDepartmentDetails(approvalLevel.getReportingTo());
//					if (null != department) {
//						mapper.setLevel1(department.getDepartmentName());
//					}
//				}
//				if (!StringUtils.isEmpty(approvalLevel.getReportingTo2())) {
//					Department department = departmentRepository.getDepartmentDetails(approvalLevel.getReportingTo2());
//					if (null != department) {
//						mapper.setLevel2(department.getDepartmentName());
//					}
//				}
//				if (!StringUtils.isEmpty(approvalLevel.getReportingTo3())) {
//					Department department = departmentRepository.getDepartmentDetails(approvalLevel.getReportingTo3());
//					if (null != department) {
//						mapper.setLevel3(department.getDepartmentName());
//					}
//				}
//				if (!StringUtils.isEmpty(approvalLevel.getReportingTo4())) {
//					Department department = departmentRepository.getDepartmentDetails(approvalLevel.getReportingTo4());
//					if (null != department) {
//						mapper.setLevel4(department.getDepartmentName());
//					}
//				}
//				if (!StringUtils.isEmpty(approvalLevel.getReportingTo5())) {
//					Department department = departmentRepository.getDepartmentDetails(approvalLevel.getReportingTo5());
//					if (null != department) {
//						mapper.setLevel5(department.getDepartmentName());
//					}
//				}
//				mapper.setThreshold1(approvalLevel.getThreshold1());
//				mapper.setThreshold2(approvalLevel.getThreshold2());
//				mapper.setThreshold3(approvalLevel.getThreshold3());
//				mapper.setThreshold4(approvalLevel.getThreshold4());
//				mapper.setThreshold5(approvalLevel.getThreshold5());
			}
			mapper.setApprovalIndicator(approvalLevel.isApprovalIndicator());
			mapper.setSubProcessName(approvalLevel.getSubProcessApprovalId());
		}
		return mapper;
	}

	@Override
	public String ProcessApprove(String userId, String subProcessName, TaskDetails taskDetails) {
		String taskId = createApprovalTask(userId, subProcessName, taskDetails);

		return taskId;
	}

	private String createApprovalTask(String userId, String subProcessName, TaskDetails taskDetails) {
		// EmployeeDetails employeeDetails =
		// employeeRepository.getEmployeesByuserId(processApprovalMapper.getUserId());

		ApprovalLevel approvalLevel = approvalLevelRepository.findBySubProcessApprovalIdAndLiveInd(subProcessName,
				true);
		if (approvalLevel != null) {
			List<EmployeeDetails> employeeList = new ArrayList<>();
			if(approvalLevel.getApprovalType().equalsIgnoreCase("exception")) {
				employeeList = employeeRepository.
						getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getDepartmentId(),approvalLevel.getDesignationId());
			}else {
				EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
				EmployeeDetails EmployeeDetail1 = employeeRepository.
						getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
				EmployeeDetails EmployeeDetail2 = employeeRepository.
						getEmployeeDetailsByEmployeeId(EmployeeDetail1.getReportingManager());
				if(approvalLevel.getReportingTo().equalsIgnoreCase("ReportingManager")) {
					employeeList.add(EmployeeDetail1);
					System.out.println("Manager............");
//			 employeeList = employeeRepository
//					.getEmployeeListByDepartmentId(approvalLevel.getReportingTo());
				}else if(approvalLevel.getReportingTo().equalsIgnoreCase("ReportingManager+1")) {
					System.out.println("Manager +1............");
					employeeList.add(EmployeeDetail2);
				}else {
					employeeList = employeeRepository.
					getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo3(),approvalLevel.getRoleType());
				}
			}
			// System.out.println(userList.toString());

//							taskDetails.setTask_type(subProcessName+"Approval");
//							taskDetails.setTask_name(subProcessName+" Approval");
			taskDetails.setCurrentLevel(1);
//			taskDetails.setFilterTaskInd(true);
			taskDetails.setApproved_ind("Pending");
			taskDetailsRepository.save(taskDetails);

			employeeList.forEach(temp -> {
				/* insert to ApproveTaskLink */
				EmployeeTaskLink approveTaskLink = new EmployeeTaskLink();
				approveTaskLink.setTask_id(taskDetails.getTask_id());
				approveTaskLink.setEmployee_id(temp.getUserId());
				approveTaskLink.setApproveStatus("Pending");
				approveTaskLink.setFilterTaskInd(true);
				approveTaskLink.setCreation_date(new Date());
				approveTaskLink.setLive_ind(true);
				approveTaskLink.setSubProcessApprovalId(approvalLevel.getId());
				approveTaskLink.setLevel(1);
				employeeTaskRepository.save(approveTaskLink);

				EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(userId, true);
				String fullName = "";

				if (null != details) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(details.getLastName())) {

						lastName = details.getLastName();
					}

					if (details.getMiddleName() != null && details.getMiddleName().length() > 0) {

						middleName = details.getMiddleName();
						fullName = (details.getFirstName() + " " + middleName + " " + lastName);
					} else {

						fullName = (details.getFirstName() + " " + lastName);
					}
				}

				/* insert to Notification */
				NotificationDetails notification = new NotificationDetails();
				notification.setNotificationType(subProcessName);
				notification.setUser_id(userId);
				notification.setAssignedBy(fullName);
				notification.setMessage(fullName + " requested approval for " + notification.getNotificationType());
				notification.setAssignedTo(temp.getUserId());
				notification.setNotificationDate(new Date());
				notification.setOrg_id(notification.getOrg_id());
				notification.setMessageReadInd(false);
				notificationRepository.save(notification);

				EmployeeDetails details2 = employeeRepository.getEmployeeDetailsByEmployeeId(temp.getUserId(), true);
				String myvar = "<div style=' display: block; margin-top: 100px; '>"
						+ "    <div style='  text-align: center;'> </div>"
						+ "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
						+ "        <div class='box-2' style='  text-align: center;'>"
						+ "            <h1 style='text-align: center; padding: 10px;'>Hello " + details2.getFirstName()
						+ " </h1>"
						+ "            <p style='text-align: center;'>fullName + \" requested approval for \" + notification.getNotificationType());"
						+ "            </div>" + "        </div>" + "    </div>" + "</div>";

				String subject = subProcessName + " approve request.....";
				String from = "engage@tekorero.com";
				String to = details2.getEmailId();
				try {

					emailService.sendMail(from, to, subject, myvar);
				} catch (Exception e) {

					e.printStackTrace();
				}

			});

		}

		return taskDetails.getTask_id();

	}

	@Override
	public void approveStageApprovalProcess(String taskId, String userId, String taskType) {
		TaskDetails task = taskDetailsRepository.getTaskDetailsById(taskId);
		System.out.println("taskLinkooo=="+taskId+"||"+userId);
		EmployeeTaskLink taskLink = employeeTaskRepository.getEmployeeTaskLinkAndApproveStatus(userId,taskId,"Pending");
		System.out.println("taskLinkpp=="+taskLink.toString());

		taskLink.setApproveStatus("Approved");
		// approve by set
		// level set
		taskLink.setApprovedBy(userId);
		taskLink.setLevel(task.getCurrentLevel());
		taskLink.setApprovedDate(new Date());
		employeeTaskRepository.save(taskLink);

		// check for the status of other users having same task

		List<EmployeeTaskLink> approvalList = employeeTaskRepository.getEmpListByTaskId(taskId);


		approvalList.forEach(approveTaskLink->{
			
			if(approveTaskLink.getApprovedBy()==null) {
			approveTaskLink.setApproveStatus("Approved");
			if(userId!=null) {
			approveTaskLink.setApprovedBy(userId);
			}
			if(task.getCurrentLevel()!=0) {
			approveTaskLink.setLevel(task.getCurrentLevel());
			}
			approveTaskLink.setApprovedDate(new Date());
			}
			employeeTaskRepository.save(approveTaskLink);
		});

		boolean allSameStatus = approvalList.stream().allMatch(x -> x.getApproveStatus().equals("Approved"));
		int currentLevel = task.getCurrentLevel();

		if (allSameStatus) { // if true that means all user has approved the task we need to send to next
								// level

			System.out.println("inside if....................");


//			String subProcessId = approvalList.get(0).getSubProcessApprovalId();

//			System.out.println("subProcessId......." + subProcessId);

				System.out.println("inside if.of processApproval...................");
				
					ApprovalLevel approvalLevel = approvalLevelRepository
							.findBySubProcessApprovalIdAndLiveInd(taskType, true);
					
					int levelCount = approvalLevel.getLevelCount();

					if (approvalLevel != null && currentLevel <= levelCount) {
						task.setStart_date(new Date());
						task.setEnd_date(new Date());
						System.out.println("dateeeee" + new Date());
						if (currentLevel == levelCount) {
							
							if (taskType.equalsIgnoreCase("leave")) {
							LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);

							if (null != leaveDetails) {
								leaveDetails.setStatus("Approved");
								leaveDetailsRepository.save(leaveDetails);
							}
							
						} else if (taskType.equalsIgnoreCase("mileage")
								|| taskType.equalsIgnoreCase("expense")) {

							VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
							if (null != voucherDetails) {
								voucherDetails.setStatus("Approved");
								voucherRepository.save(voucherDetails);
							}
						}
								
							System.out.println("level count..............." + levelCount);
							task.setApproved_ind("Approved");
							task.setApproved_date(new Date());
							taskDetailsRepository.save(task);
							//List<EmployeeTaskLink> employeeTaskLink = employeeTaskRepository.findByLevel(currentLevel);
							/* insert to Notification */
									NotificationDetails notification = new NotificationDetails();
									notification.setNotificationType(taskType + " Approve");
									TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
									notification.setUser_id(taskDetailss.getUser_id());
									EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
									EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(userId);
									notification.setAssignedBy(employee2.getFirstName());
									notification.setAssignedTo(employee.getUserId());
									notification.setNotificationDate(new Date());
									notification.setMessage("" + employee2.getFirstName() + " has approved your " +taskType);
									notification.setOrg_id(employee.getOrgId());
									notification.setMessageReadInd(false);
									notificationRepository.save(notification);   

					}else {
							System.out.println("inside if.of processApproval....and level count...............");
							List<EmployeeDetails> userList = new ArrayList<>();

							switch (currentLevel + 1) {
							case 2:
//								if(approvalLevel.getThreshold2()>=)
//								userList = employeeRepository
//								.getEmployeeListByDepartmentId(approvalLevel.getReportingTo2());
								if(approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager+1")) {
									EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
								EmployeeDetails EmployeeDetail1 = employeeRepository.
										getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
								EmployeeDetails EmployeeDetail2 = employeeRepository.
										getEmployeeDetailsByEmployeeId(EmployeeDetail1.getReportingManager());
								userList.add(EmployeeDetail2);
								System.out.println("Manager +1............");
								System.out.println("userList 111=="+userList.toString());
								}
								break;
							case 3:
								userList = employeeRepository
								.getEmployeeListByDepartmentId(approvalLevel.getReportingTo3());
								break;
							case 4:
								userList = employeeRepository
								.getEmployeeListByDepartmentId(approvalLevel.getReportingTo4());
								break;
							case 5:
								userList = employeeRepository
								.getEmployeeListByDepartmentId(approvalLevel.getReportingTo5());
								break;

							}
							System.out.println("userList 22=="+userList.toString());
							if (userList != null) {
//								userList.forEach(employeeDetails -> {
									for (EmployeeTaskLink employeeDetails : approvalList) {
					
										/* insert to ApproveTaskLink */
										EmployeeTaskLink approveTaskLink = new EmployeeTaskLink();
										approveTaskLink.setTask_id(taskId);
										approveTaskLink.setLive_ind(true);
										approveTaskLink.setFilterTaskInd(true);
										approveTaskLink.setCreation_date(new Date());
										approveTaskLink.setEmployee_id(employeeDetails.getEmployee_id());
										approveTaskLink.setApproveStatus("Pending");
										approveTaskLink.setSubProcessApprovalId(taskType);
										employeeTaskRepository.save(approveTaskLink);

									}

								}
							}
							task.setCurrentLevel(currentLevel + 1);
							taskDetailsRepository.save(task);

					} // if(approvalLevel!=null && currentLevel<=levelCount)			

		} // allSameStatus true if

	} // if task

	
	@Override
	public void expenseTaskApprove(String taskId, String userId, String taskType) {

		TaskDetails task = taskDetailsRepository.getTaskDetailsById(taskId);
		System.out.println("taskLinkooo=="+taskId+"||"+userId);
		EmployeeTaskLink taskLink = employeeTaskRepository.getEmployeeTaskLinkAndApproveStatus(userId,taskId,"Pending");
		System.out.println("taskLinkpp=="+taskLink.toString());

		taskLink.setApproveStatus("Approved");
		// approve by set
		// level set
		taskLink.setApprovedBy(userId);
//		taskLink.setLevel(task.getCurrentLevel());
		taskLink.setApprovedDate(new Date());
		taskLink.setLevel(task.getCurrentLevel());
		employeeTaskRepository.save(taskLink);

		// check for the status of other users having same task
		ApprovalLevel approvalLevel = approvalLevelRepository.getApprovalLevelDetail(taskLink.getSubProcessApprovalId());
		if(approvalLevel.getApprovalType().equalsIgnoreCase("exception")) {
		List<EmployeeTaskLink> approvalList = employeeTaskRepository.getEmpListByTaskId(taskId);
		approvalList.forEach(approveTaskLink->{
			if(approveTaskLink.getApprovedBy()==null) {
			approveTaskLink.setApproveStatus("Approved");
			if(userId!=null) {
			approveTaskLink.setApprovedBy(userId);
			}
			if(task.getCurrentLevel()!=0) {
			approveTaskLink.setLevel(task.getCurrentLevel());
			}
			approveTaskLink.setApprovedDate(new Date());
			}
			employeeTaskRepository.save(approveTaskLink);
		});
		VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
		if (null != voucherDetails) {
			voucherDetails.setStatus("Approved");
			voucherRepository.save(voucherDetails);
		}
			
		task.setApproved_ind("Approved");
		task.setApproved_date(new Date());
		task.setCurrentLevel(1);
		taskDetailsRepository.save(task);
		}else {
		
		int currentLevel = task.getCurrentLevel();
		
		if (approvalLevel != null && currentLevel <= approvalLevel.getLevelCount()) {
			if (currentLevel == approvalLevel.getLevelCount()) {
				task.setApproved_ind("Approved");
				task.setTask_status("Completed");
				task.setComplitionInd(true);
				task.setApproved_date(new Date());
				task.setCurrentLevel(currentLevel);
				taskDetailsRepository.save(task);
				VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
				if (null != voucherDetails) {
					voucherDetails.setStatus("Approved");
					voucherRepository.save(voucherDetails);
				}
			}else {
		List<EmployeeDetails> userList = new ArrayList<>();
		EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(task.getUser_id());
//					int levelCount = approvalLevel.getLevelCount();
					VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
//					int swCase = task.getCurrentLevel() ;
							switch (currentLevel) {
							case 1:
								
										if(approvalLevel.getThreshold1()>voucherDetails.getAmount()) {
										if (null != voucherDetails) {
											voucherDetails.setStatus("Approved");
											voucherRepository.save(voucherDetails);
										}
									
											
//										System.out.println("level count..............." + levelCount);
										task.setApproved_ind("Approved");
										task.setApproved_date(new Date());
										task.setCurrentLevel(1);
										taskDetailsRepository.save(task);
										//List<EmployeeTaskLink> employeeTaskLink = employeeTaskRepository.findByLevel(currentLevel);
										/* insert to Notification */
												NotificationDetails notification = new NotificationDetails();
												notification.setNotificationType(taskType + " Approve");
												TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
												notification.setUser_id(taskDetailss.getUser_id());
												EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
												EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(userId);
												notification.setAssignedBy(employee2.getFirstName());
												notification.setAssignedTo(employee.getUserId());
												notification.setNotificationDate(new Date());
												notification.setMessage("" + employee2.getFirstName() + " has approved your " +taskType);
												notification.setOrg_id(employee.getOrgId());
												notification.setMessageReadInd(false);
												notificationRepository.save(notification);   
								}else {
								
								if (approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager+1")) {
									if (!approvalLevel.getReportingTo().equalsIgnoreCase(approvalLevel.getReportingTo2())) {

//										EmployeeDetails EmployeeDetails = employeeRepository
//												.getEmployeeDetailsByEmployeeId(userId);
										EmployeeDetails reportingManager = employeeRepository
												.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
										EmployeeDetails reportingManager1 = employeeRepository
												.getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
										if (null != reportingManager1) {
											if (!reportingManager.getUserId().equals(reportingManager1.getUserId())) {
												userList.add(reportingManager1);
												System.out.println("Manager +1............");
												System.out.println("userList 111==" + userList.toString());
											} else {
												currentLevel = currentLevel + 1;
											}
										} else {
											currentLevel = currentLevel + 1;
										}
									} else {
										currentLevel = currentLevel + 1;
									}
								} else if (approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager")) {
//									EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
									EmployeeDetails reportingManager = employeeRepository
											.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
									if (!approvalLevel.getReportingTo().equalsIgnoreCase(approvalLevel.getReportingTo2())) {
										userList.add(reportingManager);
									} else {
										currentLevel = currentLevel + 1;
									}
								} else {
									List<EmployeeDetails> depRoleEmPList = employeeRepository
											.getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo3(),
													approvalLevel.getRoleType());
									userList.addAll(depRoleEmPList);
								}
								}
							case 2:
								
							if (currentLevel == 2) { 
									if(approvalLevel.getThreshold2()>voucherDetails.getAmount()) {
									if (null != voucherDetails) {
										voucherDetails.setStatus("Approved");
										voucherRepository.save(voucherDetails);
									}
										
//									System.out.println("level count..............." + levelCount);
									task.setApproved_ind("Approved");
									task.setApproved_date(new Date());
									taskDetailsRepository.save(task);
									//List<EmployeeTaskLink> employeeTaskLink = employeeTaskRepository.findByLevel(currentLevel);
									/* insert to Notification */
											NotificationDetails notification = new NotificationDetails();
											notification.setNotificationType(taskType + " Approve");
											TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
											notification.setUser_id(taskDetailss.getUser_id());
											EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
											EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(userId);
											notification.setAssignedBy(employee2.getFirstName());
											notification.setAssignedTo(employee.getUserId());
											notification.setNotificationDate(new Date());
											notification.setMessage("" + employee2.getFirstName() + " has approved your " +taskType);
											notification.setOrg_id(employee.getOrgId());
											notification.setMessageReadInd(false);
											notificationRepository.save(notification);   
							}else {
								
								boolean flag = false;
								if (approvalLevel.getReportingTo3().equalsIgnoreCase("ReportingManager+1")) {
									if (!approvalLevel.getReportingTo2().equalsIgnoreCase(approvalLevel.getReportingTo3())) {
//									EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
									EmployeeDetails reportingManager = employeeRepository
											.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
									EmployeeDetails reportingManager1 = employeeRepository
											.getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
									if(null != reportingManager1) {
									if (!reportingManager.getUserId().equals(reportingManager1.getUserId())) {
									userList.add(reportingManager1);
									System.out.println("Manager +1............");
									System.out.println("userList 111==" + userList.toString());
									}else {
										flag = true;
									}
									}else {
										flag = true;
									}
									}else {
										flag = true;
									}
								} else if (approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager")) {
									if (!approvalLevel.getReportingTo2().equalsIgnoreCase(approvalLevel.getReportingTo3())) {
//									EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
									EmployeeDetails reportingManager = employeeRepository
											.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
									userList.add(reportingManager);
									}else {
										flag = true;
									}
								} else {
									List<EmployeeDetails> depRoleEmPList = employeeRepository
											.getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo3(),
													approvalLevel.getRoleType());
									userList.addAll(depRoleEmPList);
								}
								
								if(flag) {
									if (null != voucherDetails) {
										voucherDetails.setStatus("Approved");
										voucherRepository.save(voucherDetails);
									}
								
										
//									System.out.println("level count..............." + levelCount);
									task.setApproved_ind("Approved");
									task.setApproved_date(new Date());
									task.setCurrentLevel(1);
									taskDetailsRepository.save(task);
									//List<EmployeeTaskLink> employeeTaskLink = employeeTaskRepository.findByLevel(currentLevel);
									/* insert to Notification */
											NotificationDetails notification = new NotificationDetails();
											notification.setNotificationType(taskType + " Approve");
											TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
											notification.setUser_id(taskDetailss.getUser_id());
											EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
											EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(userId);
											notification.setAssignedBy(employee2.getFirstName());
											notification.setAssignedTo(employee.getUserId());
											notification.setNotificationDate(new Date());
											notification.setMessage("" + employee2.getFirstName() + " has approved your " +taskType);
											notification.setOrg_id(employee.getOrgId());
											notification.setMessageReadInd(false);
											notificationRepository.save(notification);   
								}
							}

							}
						}
			
							
							System.out.println("userList 22=="+userList.toString());
							if (userList != null) {
//								userList.forEach(employeeDetails -> {
								for (EmployeeDetails employeeDetails : userList) {
									
										/* insert to ApproveTaskLink */
										EmployeeTaskLink approveTaskLink = new EmployeeTaskLink();
										approveTaskLink.setTask_id(taskId);
										approveTaskLink.setLive_ind(true);
										approveTaskLink.setFilterTaskInd(true);
										approveTaskLink.setCreation_date(new Date());
										approveTaskLink.setEmployee_id(employeeDetails.getEmployeeId());
										approveTaskLink.setApproveStatus("Pending");
										approveTaskLink.setSubProcessApprovalId(approvalLevel.getId());
										approveTaskLink.setLevel(currentLevel + 1);
										employeeTaskRepository.save(approveTaskLink);

									}

								}
							task.setCurrentLevel(currentLevel + 1);
							taskDetailsRepository.save(task);			
					} //current level <= app level count
					} //approval type
		}
	}
	
	
	@Override
	public void approveLeaveProcess(String taskId, String userId, String taskType) {
		TaskDetails task = taskDetailsRepository.getTaskDetailsById(taskId);
		System.out.println("taskLinkooo=="+taskId+"||"+userId);
		EmployeeTaskLink taskLink = employeeTaskRepository.getEmployeeTaskLinkAndApproveStatus(userId,taskId,"Pending");
		if(null!=taskLink) {
		System.out.println("taskLinkpp=="+taskLink.toString());

		taskLink.setApproveStatus("Approved");
		// approve by set
		// level set
		taskLink.setApprovedBy(userId);
//		taskLink.setLevel(task.getCurrentLevel());
		taskLink.setApprovedDate(new Date());
		taskLink.setLevel(task.getCurrentLevel());
		employeeTaskRepository.save(taskLink);

		// check for the status of other users having same task
		ApprovalLevel approvalLevel = approvalLevelRepository.getApprovalLevelDetail(taskLink.getSubProcessApprovalId());
		if(approvalLevel.getApprovalType().equalsIgnoreCase("exception")) {
		List<EmployeeTaskLink> approvalList = employeeTaskRepository.getEmpListByTaskId(taskId);
		approvalList.forEach(approveTaskLink->{
			if(approveTaskLink.getApprovedBy()==null) {
			approveTaskLink.setApproveStatus("Approved");
			if(userId!=null) {
			approveTaskLink.setApprovedBy(userId);
			}
			if(task.getCurrentLevel()!=0) {
			approveTaskLink.setLevel(task.getCurrentLevel());
			}
			approveTaskLink.setApprovedDate(new Date());
			}
			employeeTaskRepository.save(approveTaskLink);
		});
		
		LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);
		if (null != leaveDetails) {
			leaveDetails.setStatus("Approved");
			leaveDetailsRepository.save(leaveDetails);
			}
		
		task.setApproved_ind("Approved");
		task.setTask_status("Completed");
		task.setComplitionInd(true);
		task.setApproved_date(new Date());
//		task.setCurrentLevel(currentLevel);
		taskDetailsRepository.save(task);

		}else {
		
		int currentLevel = task.getCurrentLevel();
		
					int levelCount = approvalLevel.getLevelCount();

					if (approvalLevel != null && currentLevel <= levelCount) {
						if (currentLevel == levelCount) {
										LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);
										if (null != leaveDetails) {
											leaveDetails.setStatus("Approved");
											leaveDetailsRepository.save(leaveDetails);
										}
									
											
//										System.out.println("level count..............." + levelCount);
										task.setApproved_ind("Approved");
										task.setTask_status("Completed");
										task.setComplitionInd(true);
										task.setApproved_date(new Date());
										task.setCurrentLevel(currentLevel);
										taskDetailsRepository.save(task);
										//List<EmployeeTaskLink> employeeTaskLink = employeeTaskRepository.findByLevel(currentLevel);
										/* insert to Notification */
												NotificationDetails notification = new NotificationDetails();
												notification.setNotificationType(taskType + " Approve");
												TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
												notification.setUser_id(taskDetailss.getUser_id());
												EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
												EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(userId);
												notification.setAssignedBy(employee2.getFirstName());
												notification.setAssignedTo(employee.getUserId());
												notification.setNotificationDate(new Date());
												notification.setMessage("" + employee2.getFirstName() + " has approved your " +taskType);
												notification.setOrg_id(employee.getOrgId());
												notification.setMessageReadInd(false);
												notificationRepository.save(notification);   
								}else {
									int swCase = task.getCurrentLevel() +1;
									List<EmployeeDetails> userList = new ArrayList<>();

									switch (swCase) {
									
									case 2:
//										if(approvalLevel.getThreshold2()>=)
//										userList = employeeRepository
//										.getEmployeeListByDepartmentId(approvalLevel.getReportingTo2());
										if(approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager+1")) {
											if (!approvalLevel.getReportingTo().equalsIgnoreCase(approvalLevel.getReportingTo2())) {
											EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(task.getUser_id());
										EmployeeDetails reportingManager = employeeRepository.
												getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
										EmployeeDetails reportingManager1 = employeeRepository.
												getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
										if (null != reportingManager1) {
											if (!reportingManager.getUserId().equals(reportingManager1.getUserId())) {
										userList.add(reportingManager1);
										System.out.println("Manager +1............");
										System.out.println("userList 111=="+userList.toString());
											}else {
												swCase = swCase + 1;
											}
										}else {
											swCase = swCase + 1;
										}
									}else {
										swCase = swCase + 1;
									}
										} else if(approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager")) {
											EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(task.getUser_id());
											EmployeeDetails reportingManager = employeeRepository.
													getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
											if (!approvalLevel.getReportingTo().equalsIgnoreCase(approvalLevel.getReportingTo2())) {
											userList.add(reportingManager);
											}else {
												swCase = swCase + 1;
											}
										}else {
											List<EmployeeDetails> depRoleEmPList = employeeRepository.
													getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo3(),approvalLevel.getRoleType2());
											userList.addAll(depRoleEmPList);
										}
										
									case 3:
									if (swCase == 3) { 
										if( swCase <= levelCount) {								
										boolean flag = false;
										if(approvalLevel.getReportingTo3().equalsIgnoreCase("ReportingManager+1")) {
											if (!approvalLevel.getReportingTo2().equalsIgnoreCase(approvalLevel.getReportingTo3())) {
											EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(task.getUser_id());
										EmployeeDetails reportingManager = employeeRepository.
												getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
										EmployeeDetails reportingManager1 = employeeRepository.
												getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
										if(null != reportingManager1) {
											if (!reportingManager.getUserId().equals(reportingManager1.getUserId())) {
										userList.add(reportingManager1);
										System.out.println("Manager +1............");
										System.out.println("userList 111=="+userList.toString());
											}else {
												flag = true;
											}
											}else {
												flag = true;
											}
											}else {
												flag = true;
											}
										} else if(approvalLevel.getReportingTo3().equalsIgnoreCase("ReportingManager")) {
											if (!approvalLevel.getReportingTo2().equalsIgnoreCase(approvalLevel.getReportingTo3())) {
											EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(task.getUser_id());
											EmployeeDetails reportingManager = employeeRepository.
													getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
											userList.add(reportingManager);
											}else {
												flag = true;
											}
										}else {
											List<EmployeeDetails> depRoleEmPList = employeeRepository.
													getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo3(),approvalLevel.getRoleType3());
											userList.addAll(depRoleEmPList);
										}
										if(flag) {
											LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);
											if (null != leaveDetails) {
												leaveDetails.setStatus("Approved");
												leaveDetailsRepository.save(leaveDetails);
												}
										}
									}else {
										LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);
										if (null != leaveDetails) {
											leaveDetails.setStatus("Approved");
											leaveDetailsRepository.save(leaveDetails);
											}
										 }
									}
										break;
									case 4:
										userList = employeeRepository
										.getEmployeeListByDepartmentId(approvalLevel.getReportingTo4());
										break;
									case 5:
										userList = employeeRepository
										.getEmployeeListByDepartmentId(approvalLevel.getReportingTo5());
										break;

									}
									System.out.println("userList 22=="+userList.toString());
									if (userList != null) {
//										userList.forEach(employeeDetails -> {
										for (EmployeeDetails employeeDetails : userList) {	
												/* insert to ApproveTaskLink */
												EmployeeTaskLink approveTaskLink = new EmployeeTaskLink();
												approveTaskLink.setTask_id(taskId);
												approveTaskLink.setLive_ind(true);
												approveTaskLink.setFilterTaskInd(true);
												approveTaskLink.setCreation_date(new Date());
												approveTaskLink.setEmployee_id(employeeDetails.getEmployeeId());
												approveTaskLink.setApproveStatus("Pending");
												approveTaskLink.setLevel(swCase);
												approveTaskLink.setSubProcessApprovalId(approvalLevel.getId());
												employeeTaskRepository.save(approveTaskLink);
											}

										}
									
									task.setCurrentLevel(swCase);
									taskDetailsRepository.save(task);
								}//current level < app level count
					}//current level <= app level count
		}// approval type
		}
		
	}
	
	
	@Override
	public void approveMileageTaskProcess(String taskId, String userId, String taskType) {
		TaskDetails task = taskDetailsRepository.getTaskDetailsById(taskId);
		System.out.println("taskLinkooo==" + taskId + "||" + userId);
		EmployeeTaskLink taskLink = employeeTaskRepository.getEmployeeTaskLinkAndApproveStatus(userId, taskId,
				"Pending");
		System.out.println("taskLinkpp==" + taskLink.toString());

		taskLink.setApproveStatus("Approved");
		// approve by set
		// level set
		taskLink.setApprovedBy(userId);
//		taskLink.setLevel(task.getCurrentLevel());
		taskLink.setApprovedDate(new Date());
		taskLink.setLevel(task.getCurrentLevel());
		employeeTaskRepository.save(taskLink);

		// check for the status of other users having same task
		ApprovalLevel approvalLevel = approvalLevelRepository
				.getApprovalLevelDetail(taskLink.getSubProcessApprovalId());
		if (approvalLevel.getApprovalType().equalsIgnoreCase("exception")) {
			List<EmployeeTaskLink> approvalList = employeeTaskRepository.getEmpListByTaskId(taskId);
			approvalList.forEach(approveTaskLink -> {
				if (approveTaskLink.getApprovedBy() == null) {
					approveTaskLink.setApproveStatus("Approved");
					if (userId != null) {
						approveTaskLink.setApprovedBy(userId);
					}
					if (task.getCurrentLevel() != 0) {
						approveTaskLink.setLevel(task.getCurrentLevel());
					}
					approveTaskLink.setApprovedDate(new Date());
				}
				employeeTaskRepository.save(approveTaskLink);
			});

			VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
			if (null != voucherDetails) {
				voucherDetails.setStatus("Approved");
				voucherRepository.save(voucherDetails);
			}

			task.setApproved_ind("Approved");
			task.setTask_status("Completed");
			task.setComplitionInd(true);
			task.setApproved_date(new Date());
//		task.setCurrentLevel(currentLevel);
			taskDetailsRepository.save(task);

		} else {

			int currentLevel = task.getCurrentLevel();

			int levelCount = approvalLevel.getLevelCount();

			if (approvalLevel != null && currentLevel <= levelCount) {
				if (currentLevel == levelCount) {
					VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
					if (null != voucherDetails) {
						voucherDetails.setStatus("Approved");
						voucherRepository.save(voucherDetails);
					}

//										System.out.println("level count..............." + levelCount);
					task.setApproved_ind("Approved");
					task.setTask_status("Completed");
					task.setComplitionInd(true);
					task.setApproved_date(new Date());
					task.setCurrentLevel(currentLevel);
					taskDetailsRepository.save(task);
					// List<EmployeeTaskLink> employeeTaskLink =
					// employeeTaskRepository.findByLevel(currentLevel);
					/* insert to Notification */
					NotificationDetails notification = new NotificationDetails();
					notification.setNotificationType(taskType + " Approve");
					TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
					notification.setUser_id(taskDetailss.getUser_id());
					EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
					EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(userId);
					notification.setAssignedBy(employee2.getFirstName());
					notification.setAssignedTo(employee.getUserId());
					notification.setNotificationDate(new Date());
					notification.setMessage("" + employee2.getFirstName() + " has approved your " + taskType);
					notification.setOrg_id(employee.getOrgId());
					notification.setMessageReadInd(false);
					notificationRepository.save(notification);
				} else {

					List<EmployeeDetails> userList = new ArrayList<>();

					switch (currentLevel + 1) {

					case 2:
//										if(approvalLevel.getThreshold2()>=)
//										userList = employeeRepository
//										.getEmployeeListByDepartmentId(approvalLevel.getReportingTo2());
						if (approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager+1")) {
							EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
							EmployeeDetails reportingManager = employeeRepository
									.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
							EmployeeDetails reportingManager1 = employeeRepository
									.getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
							userList.add(reportingManager1);
							System.out.println("Manager +1............");
							System.out.println("userList 111==" + userList.toString());
						} else if (approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager")) {
							EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
							EmployeeDetails reportingManager = employeeRepository
									.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
							userList.add(reportingManager);
						} else {
							List<EmployeeDetails> depRoleEmPList = employeeRepository
									.getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo2(),
											approvalLevel.getRoleType2());
							userList.addAll(depRoleEmPList);
						}
						break;
					case 3:
						if (approvalLevel.getReportingTo3().equalsIgnoreCase("ReportingManager+1")) {
							EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
							EmployeeDetails reportingManager = employeeRepository
									.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
							EmployeeDetails reportingManager1 = employeeRepository
									.getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
							userList.add(reportingManager1);
							System.out.println("Manager +1............");
							System.out.println("userList 111==" + userList.toString());
						} else if (approvalLevel.getReportingTo3().equalsIgnoreCase("ReportingManager")) {
							EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
							EmployeeDetails reportingManager = employeeRepository
									.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
							userList.add(reportingManager);
						} else {
							List<EmployeeDetails> depRoleEmPList = employeeRepository
									.getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo3(),
											approvalLevel.getRoleType3());
							userList.addAll(depRoleEmPList);
						}

						break;
					case 4:
						userList = employeeRepository.getEmployeeListByDepartmentId(approvalLevel.getReportingTo4());
						break;
					case 5:
						userList = employeeRepository.getEmployeeListByDepartmentId(approvalLevel.getReportingTo5());
						break;

					}
					System.out.println("userList 22==" + userList.toString());
					if (userList != null) {
						userList.forEach(employeeDetails -> {
							/* insert to ApproveTaskLink */
							EmployeeTaskLink approveTaskLink = new EmployeeTaskLink();
							approveTaskLink.setTask_id(taskId);
							approveTaskLink.setLive_ind(true);
							approveTaskLink.setFilterTaskInd(true);
							approveTaskLink.setCreation_date(new Date());
							approveTaskLink.setEmployee_id(employeeDetails.getEmployeeId());
							approveTaskLink.setApproveStatus("Pending");
							approveTaskLink.setLevel(currentLevel + 1);
							approveTaskLink.setSubProcessApprovalId(approvalLevel.getId());
							employeeTaskRepository.save(approveTaskLink);

						});

					}

					task.setCurrentLevel(currentLevel + 1);
					taskDetailsRepository.save(task);
				} // current level < app level count
			} // current level <= app level count
		} // approval type
	}
	
	
	@Override
	public void approveContactUserCreateProcess(String taskId, String userId, String taskType) {
		TaskDetails task = taskDetailsRepository.getTaskDetailsById(taskId);
		System.out.println("taskLinkooo==" + taskId + "||" + userId);
		EmployeeTaskLink taskLink = employeeTaskRepository.getEmployeeTaskLinkAndApproveStatus(userId, taskId,
				"Pending");
		System.out.println("taskLinkpp==" + taskLink.toString());

		taskLink.setApproveStatus("Approved");
		// approve by set
		// level set
		taskLink.setApprovedBy(userId);
//		taskLink.setLevel(task.getCurrentLevel());
		taskLink.setApprovedDate(new Date());
		taskLink.setLevel(task.getCurrentLevel());
		employeeTaskRepository.save(taskLink);

		// check for the status of other users having same task
		ApprovalLevel approvalLevel = approvalLevelRepository
				.getApprovalLevelDetail(taskLink.getSubProcessApprovalId());
		if (approvalLevel.getApprovalType().equalsIgnoreCase("exception")) {
			List<EmployeeTaskLink> approvalList = employeeTaskRepository.getEmpListByTaskId(taskId);
			approvalList.forEach(approveTaskLink -> {
				if (approveTaskLink.getApprovedBy() == null) {
					approveTaskLink.setApproveStatus("Approved");
					if (userId != null) {
						approveTaskLink.setApprovedBy(userId);
					}
					if (task.getCurrentLevel() != 0) {
						approveTaskLink.setLevel(task.getCurrentLevel());
					}
					approveTaskLink.setApprovedDate(new Date());
				}
				employeeTaskRepository.save(approveTaskLink);
			});
			System.out.println("Type==="+approvalLevel.getApprovalType());
			contactService.contactConvertToUser(taskId);
		} else {
			System.out.println("Type==="+approvalLevel.getApprovalType());
			int currentLevel = task.getCurrentLevel();
			int swCase = task.getCurrentLevel() +1;
			
			int levelCount = approvalLevel.getLevelCount();

			if (approvalLevel != null && currentLevel <= levelCount) {
				if (currentLevel == levelCount) {
					task.setApproved_ind("Approved");
					task.setTask_status("Completed");
					task.setComplitionInd(true);
					task.setApproved_date(new Date());
					task.setCurrentLevel(currentLevel);
					taskDetailsRepository.save(task);
					
					contactService.contactConvertToUser(taskId);
				} else {
					System.out.println("currentLevel==="+currentLevel +"levelCount==="+levelCount);
					System.out.println("Type==="+approvalLevel.getApprovalType());
					List<EmployeeDetails> userList = new ArrayList<>();

					switch (swCase) {

					case 2:
//				if(!StringUtils.isEmpty(approvalLevel.getReportingTo2())){
						if (approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager+1")) {
							if (!approvalLevel.getReportingTo().equalsIgnoreCase(approvalLevel.getReportingTo2())) {

								EmployeeDetails EmployeeDetails = employeeRepository
										.getEmployeeDetailsByEmployeeId(task.getUser_id());
								EmployeeDetails reportingManager = employeeRepository
										.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
								EmployeeDetails reportingManager1 = employeeRepository
										.getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
								if (null != reportingManager1) {
									if (!reportingManager.getUserId().equals(reportingManager1.getUserId())) {
										userList.add(reportingManager1);
										System.out.println("Manager +1............");
										System.out.println("userList 111==" + userList.toString());
									} else {
										swCase = swCase + 1;
									}
								} else {
									swCase = swCase + 1;
								}
							} else {
								swCase = swCase + 1;
							}
						} else if (approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager")) {
							EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(task.getUser_id());
							EmployeeDetails reportingManager = employeeRepository
									.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
							if (!approvalLevel.getReportingTo().equalsIgnoreCase(approvalLevel.getReportingTo2())) {
								userList.add(reportingManager);
							} else {
								swCase = swCase + 1;
							}
						} else {
							List<EmployeeDetails> depRoleEmPList = employeeRepository
									.getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo2(),
											approvalLevel.getRoleType2());
							userList.addAll(depRoleEmPList);
						}
//				}else {
//					swCase = swCase + 1;
//				}
//						break;
					case 3:
						 if (swCase == 3) { 
							if( swCase <= levelCount) {								
							boolean flag = false;
						if (approvalLevel.getReportingTo3().equalsIgnoreCase("ReportingManager+1")) {
							if (!approvalLevel.getReportingTo2().equalsIgnoreCase(approvalLevel.getReportingTo3())) {
							EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(task.getUser_id());
							EmployeeDetails reportingManager = employeeRepository
									.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
							EmployeeDetails reportingManager1 = employeeRepository
									.getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
							if(null != reportingManager1) {
							if (!reportingManager.getUserId().equals(reportingManager1.getUserId())) {
							userList.add(reportingManager1);
							System.out.println("Manager +1............");
							System.out.println("userList 111==" + userList.toString());
							}else {
								flag = true;
							}
							}else {
								flag = true;
							}
							}else {
								flag = true;
							}
						} else if (approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager")) {
							if (!approvalLevel.getReportingTo2().equalsIgnoreCase(approvalLevel.getReportingTo3())) {
							EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(task.getUser_id());
							EmployeeDetails reportingManager = employeeRepository
									.getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
							userList.add(reportingManager);
							}else {
								flag = true;
							}
						} else {
							List<EmployeeDetails> depRoleEmPList = employeeRepository
									.getActiveEmployeesByDepartmentAndRoleType(approvalLevel.getReportingTo3(),
											approvalLevel.getRoleType3());
							userList.addAll(depRoleEmPList);
						}
						
						if(flag) {
							contactService.contactConvertToUser(taskId);
						}
					}else {
								contactService.contactConvertToUser(taskId);
						 }
						 }

						break;
					}
					System.out.println("userList 22==" + userList.toString());
					if (userList != null) {
						for (EmployeeDetails employeeDetails : userList) {
							
							/* insert to ApproveTaskLink */
							EmployeeTaskLink approveTaskLink = new EmployeeTaskLink();
							approveTaskLink.setTask_id(taskId);
							approveTaskLink.setLive_ind(true);
							approveTaskLink.setFilterTaskInd(true);
							approveTaskLink.setCreation_date(new Date());
							approveTaskLink.setEmployee_id(employeeDetails.getEmployeeId());
							approveTaskLink.setApproveStatus("Pending");
							approveTaskLink.setLevel(swCase);
							approveTaskLink.setSubProcessApprovalId(approvalLevel.getId());
							employeeTaskRepository.save(approveTaskLink);

						}

					}

					task.setCurrentLevel(swCase);
					taskDetailsRepository.save(task);
				} 
			} 
		} 
	}
}
