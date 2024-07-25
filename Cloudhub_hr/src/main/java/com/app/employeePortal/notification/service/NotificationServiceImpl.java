package com.app.employeePortal.notification.service;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.notification.entity.NotificationConfigs;
import com.app.employeePortal.notification.entity.NotificationDetails;
import com.app.employeePortal.notification.entity.NotificationRule;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.mapper.NotificationConfigRequest;
import com.app.employeePortal.notification.mapper.NotificationConfigResponse;
import com.app.employeePortal.notification.mapper.NotificationMapper;
import com.app.employeePortal.notification.mapper.NotificationRuleMapper;
import com.app.employeePortal.notification.repository.NotificationConfigRepository;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.repository.NotificationRuleRepository;
import com.app.employeePortal.util.Utility;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;


@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	NotificationRuleRepository notificationRuleRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	NotificationConfigRepository notiConfigRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	EmailService emailService;
	
	final Configuration configuration;
	
	public NotificationServiceImpl(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public List<NotificationMapper> getTodaysNotificationsByUserId(String assignedTo) throws Exception {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(new Date()));
			start_date = Utility.removeTime(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notificationRepository.getTodayNotificationUser(assignedTo,start_date,end_date).stream()
				.filter(s->s.getNotificationDate().before(new Date()))
				.map(one->getNotificationResponse(one))
				.sorted((c1, c2) -> c2.getNotificationDate().compareTo(c1.getNotificationDate()))
				.collect(Collectors.toList());
//		List<NotificationDetails> notificationList =notificationRepository.getNotificationUser(assignedTo);
//		System.out.println("user org notification list .... "   +  notificationList);
//		
//	//	List<NotificationApproverDetails> approverNotificationList =notiApprovalRepo.getNotificationApprovalList(userId, paging);
//	//	System.out.println("approver notification list .... "   +  approverNotificationList);
//		
//		List<NotificationMapper> responseList = new ArrayList<NotificationMapper>();
//		
//		if (null != notificationList && !notificationList.isEmpty()) {
//			for (NotificationDetails notificationDetails : notificationList) {
//				
//
//				NotificationMapper notificationMapper = getNotificationResponseByNotificationId(notificationDetails.getNotificationId());
//				
//				
//				System.out.println("noticationMapperrr"+notificationMapper);
//				
//				if(Utility.removeTime(notificationDetails.getNotificationDate()).equals(Utility.removeTime(new Date()))) {
//					
//					notificationMapper.setAssignedTo(assignedTo);
//					responseList.add(notificationMapper);
//				}
//				
//			}
//
//		
//		
///*		}if(null !=approverNotificationList && !approverNotificationList.isEmpty()) {
//			
//			for(NotificationApproverDetails notificationDetails  : approverNotificationList) {
//				
//		NotificationMapper approverNotifucation = getNotificationResponseByNotificationId(notificationDetails.getNotification_id());
//		
//		System.out.println("approverNotificationMapperrr"+approverNotifucation);
//					
//	
//   if(Utility.removeTime(Utility.getDateFromISOString(approverNotifucation.getNotificationDate())).equals(Utility.removeTime(new Date()))) {
//			
//
//	        approverNotifucation.setUserId(userId);
//			responseList.add(approverNotifucation);
//		}
//		
//		 
//		
//			}*/
//			
//		}
//		
//		
//		
//		
//		return responseList;
	}
	
	
	@Override
	public NotificationMapper getNotificationResponseByNotificationId(String notificationId) {
    NotificationDetails notificationDetails = notificationRepository.findByNotificationId(notificationId);
	
		
		NotificationMapper notificationMapper= new NotificationMapper();

		if (null != notificationDetails) {

			notificationMapper.setNotificationMessage(notificationDetails.getMessage());
			//notificationMapper.setNotificationHeading(notificationDetails.getMessage_heading());
			//notificationMapper.setNotificationOwner(notificationDetails.getRecipient_id());
			notificationMapper.setNotificationId(notificationDetails.getNotificationId());
			notificationMapper.setNotificationDate(Utility.getISOFromDate(notificationDetails.getNotificationDate()));
			notificationMapper.setNotificationType(notificationDetails.getNotificationType());
			notificationMapper.setNotificationReadInd(notificationDetails.isMessageReadInd());
			notificationMapper.setNotificationTime("");

		}

		return notificationMapper;
	}

	@Override
	public List<NotificationMapper> getPreviousNotificationsByUserId(String assignedTo) throws Exception {
		return notificationRepository.getNotificationUser(assignedTo).stream()
		.filter(s->s.getNotificationDate().before(new Date()))
		.map(one->getNotificationResponse(one))
		.sorted((c1, c2) -> c2.getNotificationDate().compareTo(c1.getNotificationDate()))
		.collect(Collectors.toList());
//	//	Pageable paging =  new PageRequest(pageNo, pageSize);
//		List<NotificationDetails> notificationList = notificationRepository.getNotificationUser(assignedTo);
//		System.out.println("user  notification list .... "yy   +  notificationList);
//
//		
//	//	List<NotificationExecutorDetails> ExecutorNotificationList =notiExecutorRepo.getNotificationExecutorList(userId, paging);
//	//	System.out.println("executor notification list .... "   +  ExecutorNotificationList);
//		
//		List<NotificationMapper> responseList = new ArrayList<NotificationMapper>();
//		
//		if (null != notificationList && !notificationList.isEmpty()) {
//			for (NotificationDetails notificationDetails : notificationList) {
//				NotificationMapper notificationMapper = getNotificationResponseByNotificationId(notificationDetails.getNotificationId());
//			
//				if(Utility.removeTime(notificationDetails.getNotificationDate()).before(Utility.removeTime(new Date()))) {
//					
//					notificationMapper.setAssignedTo(assignedTo);
//					responseList.add(notificationMapper);
//				}
//				
//			}
//
///*		}if(null !=ExecutorNotificationList && !ExecutorNotificationList.isEmpty()) {
//			
//			for(NotificationExecutorDetails notificationDetails : ExecutorNotificationList) {
//				
//		NotificationMapper executorNotification = getNotificationResponseByNotificationId(notificationDetails.getNotification_id());
//		
//		System.out.println("ExecutorNotification........... "+executorNotification+"notificatinId"+executorNotification.getNotificationId());
//		
//		if(Utility.removeTime(Utility.getDateFromISOString(executorNotification.getNotificationDate())).before(Utility.removeTime(new Date()))) {
//			
//			System.out.println("inside daye is not emptyy");
//			
//			executorNotification.setUserId(userId);
//			responseList.add(executorNotification);
//		}
//		
//		
//			}*/
//		}
//		
//		return responseList;
	}


	@Override
	public String patchNotification(String notificationId, NotificationMapper notificationMapper) {
		 if (null != notificationId) {
	           // NotificationDetails notificationDetails = notificationDao.getNotifications(notificationId);
	            NotificationDetails notificationDetails = notificationRepository.findByNotificationId(notificationId);

	            notificationDetails.setLiveInd(false);
	           // notificationDao.updateNotificationTable(notificationDetails);
	            notificationRepository.save(notificationDetails);


	            NotificationDetails newNotificationDetailsData = new NotificationDetails();

	            if (null != notificationDetails) {


	                if (notificationMapper.getNotificationDate() != null) {


	                    try {
							newNotificationDetailsData.setNotificationDate(Utility.getDateFromISOString(notificationMapper
							        .getNotificationDate()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

	                } else {
	                    newNotificationDetailsData.setNotificationDate(notificationDetails.getNotificationDate());

	                }

	                if (notificationMapper.getNotificationMessage() != null) {


	                    newNotificationDetailsData.setMessage(notificationMapper.getNotificationMessage());
	                } else {
	                    newNotificationDetailsData.setMessage(notificationDetails.getMessage());

	                }
	                if (notificationMapper.getNotificationMessage() != null) {


	                    newNotificationDetailsData.setMessage(notificationMapper.getNotificationMessage());

	                } else {

	                    newNotificationDetailsData.setMessage(notificationDetails.getMessage());

	                }
	                if (notificationMapper.getNotificationType() != null) {

	                    newNotificationDetailsData.setNotificationType(notificationDetails.getNotificationType());

	                } else {

	                    newNotificationDetailsData.setNotificationType(notificationDetails.getNotificationType());

	                }
	                newNotificationDetailsData.setMessageReadInd(true);

	                newNotificationDetailsData.setNotificationId(notificationId);
	                newNotificationDetailsData.setLiveInd(true);
	               // newNotificationDetailsData.setCreator_id(notificationDetails.getCreator_id());
	               // newNotificationDetailsData.setRecipient_id(notificationDetails.getRecipient_id());
	                newNotificationDetailsData.setCreationDate(notificationDetails.getCreationDate());
	               // newNotificationDetailsData.setModification_date(new Date());
	               // newNotificationDetailsData.setModified_by(notificationMapper.getUserId());
	                newNotificationDetailsData= notificationRepository.save(newNotificationDetailsData);

	                String newTaskDetailsId = newNotificationDetailsData.getNotificationId();


	            }
	        }
	        return notificationId;
	    }


	@Override
	public NotificationRuleMapper updateNotifiactionRule(NotificationRuleMapper notificationRuleMapper) {
		NotificationRuleMapper resultMapper=new NotificationRuleMapper();
		
		NotificationRule notificationRule=notificationRuleRepository.getNotificationByOrgId(notificationRuleMapper.getOrgId());
		
		if(null != notificationRule) {
			notificationRule.setEmailInd(notificationRuleMapper.isEmailInd());
			notificationRule.setSmsInd(notificationRuleMapper.isSmsInd());
			notificationRule.setWhatsappInd(notificationRuleMapper.isWhatsappInd());
			notificationRule.setInappInd(notificationRuleMapper.isInappInd());
			notificationRule.setUpdatedDate(new Date());
			notificationRule.setOrgId(notificationRuleMapper.getOrgId());
			notificationRule.setUserId(notificationRuleMapper.getUserId());
			notificationRuleRepository.save(notificationRule);
		}else {
            NotificationRule newNotificationRule= new NotificationRule();
            newNotificationRule.setEmailInd(notificationRuleMapper.isEmailInd());
			newNotificationRule.setSmsInd(notificationRuleMapper.isSmsInd());
			newNotificationRule.setWhatsappInd(notificationRuleMapper.isWhatsappInd());
			newNotificationRule.setInappInd(notificationRuleMapper.isInappInd());
			newNotificationRule.setOrgId(notificationRuleMapper.getOrgId());
			newNotificationRule.setUserId(notificationRuleMapper.getUserId());
			newNotificationRule.setUpdatedDate(new Date());
			notificationRuleRepository.save(newNotificationRule);
		}
		resultMapper=getNotificationRuleByOrgId(notificationRuleMapper.getOrgId());
		return resultMapper;
	}
	@Override
    public NotificationRuleMapper getNotificationRuleByOrgId(String orgId) {
		NotificationRuleMapper resultMapper = new NotificationRuleMapper();
        
		NotificationRule notificationRule = notificationRuleRepository.getNotificationByOrgId(orgId);
        
        if(null != notificationRule) {
        	resultMapper.setNotificationRuleId(notificationRule.getNotificationRuleId());
        	resultMapper.setOrgId(notificationRule.getOrgId());
        	resultMapper.setUserId(notificationRule.getUserId());
            resultMapper.setEmailInd(notificationRule.isEmailInd());
            resultMapper.setSmsInd(notificationRule.isSmsInd());
            resultMapper.setWhatsappInd(notificationRule.isWhatsappInd());
            resultMapper.setInappInd(notificationRule.isInappInd());
            resultMapper.setUpdatedDate(Utility.getISOFromDate(notificationRule.getUpdatedDate()));
            
            EmployeeDetails employeeDetails = employeeRepository
                    .getEmployeeDetailsByEmployeeId(notificationRule.getUserId());
            if (null != employeeDetails) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                    lastName = employeeDetails.getLastName();
                }

                if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                    middleName = employeeDetails.getMiddleName();
                    resultMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
                } else {

                    resultMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
                }
            }
        }
        return resultMapper;
	
	}


	@Override
	public boolean getInappNotificationRule(String orgId) {
		boolean b = false;
		NotificationRule notificationRule=notificationRuleRepository.getNotificationByOrgId(orgId);
		if(null != notificationRule) {
			System.out.println("inaapInd=="+notificationRule.isInappInd());
			b=notificationRule.isInappInd();
		}
		return b;
	}
	
	
	private NotificationMapper getNotificationResponse(NotificationDetails notificationDetails) {	
		NotificationMapper notificationMapper= new NotificationMapper();

		if (null != notificationDetails) {

			notificationMapper.setNotificationMessage(notificationDetails.getMessage());
			//notificationMapper.setNotificationHeading(notificationDetails.getMessage_heading());
			//notificationMapper.setNotificationOwner(notificationDetails.getRecipient_id());
			notificationMapper.setNotificationId(notificationDetails.getNotificationId());
			notificationMapper.setNotificationDate(Utility.getISOFromDate(notificationDetails.getNotificationDate()));
			notificationMapper.setNotificationType(notificationDetails.getNotificationType());
			notificationMapper.setNotificationReadInd(notificationDetails.isMessageReadInd());
			notificationMapper.setNotificationTime("");
		}

		return notificationMapper;
	}


	@Override
	public NotificationConfigResponse updateNotifiactionConfig(NotificationConfigRequest mapper, String userId,
			String orgId) {
		NotificationConfigResponse response = new NotificationConfigResponse();
		NotificationConfigs config = notiConfigRepository.findByNameAndOrgId(mapper.getName(),orgId);
		if (null != config) {
			config.setReportingManager(mapper.isReportingManager());
			config.setReportingManager1(mapper.isReportingManager1());
			config.setAdmin(mapper.isAdmin());
			config.setUpdatedDate(new Date());
			config.setUserId(userId);
			config.setName(mapper.getName());
			if (mapper.getType().contains("Create")) {
				config.setCreateInd(true);
			} else {
				config.setCreateInd(false);
			}
			if (mapper.getType().contains("Update")) {
				config.setUpdateInd(true);
			} else {
				config.setUpdateInd(false);
			}
			if (mapper.getType().contains("Delete")) {
				config.setDeleteInd(true);
			} else {
				config.setDeleteInd(false);
			}
			
			NotificationConfigs updateconfig = notiConfigRepository.save(config);
			response = getNotifiactionConfig(updateconfig);
		}else {
			NotificationConfigs newConfig = new NotificationConfigs();
			newConfig.setReportingManager(mapper.isReportingManager());
			newConfig.setReportingManager1(mapper.isReportingManager1());
			newConfig.setAdmin(mapper.isAdmin());
			newConfig.setUpdatedDate(new Date());
			newConfig.setCreationDate(new Date());
			newConfig.setUserId(userId);
			newConfig.setOrgId(orgId);
			newConfig.setName(mapper.getName());
			if (mapper.getType().contains("Create")) {
				newConfig.setCreateInd(true);
			} else {
				newConfig.setCreateInd(false);
			}
			if (mapper.getType().contains("Update")) {
				newConfig.setUpdateInd(true);
			} else {
				newConfig.setUpdateInd(false);
			}
			if (mapper.getType().contains("Delete")) {
				newConfig.setDeleteInd(true);
			} else {
				newConfig.setDeleteInd(false);
			}
			
//			newConfig.setName(mapper.getName());
			NotificationConfigs updateconfig = notiConfigRepository.save(newConfig);
			response = getNotifiactionConfig(updateconfig);
			}
		return response;
	}


	private NotificationConfigResponse getNotifiactionConfig(NotificationConfigs updateconfig) {
		NotificationConfigResponse response = new NotificationConfigResponse();
		List<String> typeInds= new ArrayList<>();
		response.setReportingManager(updateconfig.isReportingManager());
		response.setReportingManager1(updateconfig.isReportingManager1());
		response.setAdmin(updateconfig.isAdmin());
		response.setUpdatedDate(Utility.getISOFromDate(updateconfig.getUpdatedDate()));
		
		if (updateconfig.isCreateInd()) {
			typeInds.add("Create");
		}
		if (updateconfig.isUpdateInd()) {
			typeInds.add("Update");
		}
		if (updateconfig.isDeleteInd()) {
			typeInds.add("Delete");
		}
		
		response.setType(typeInds);
		response.setName(updateconfig.getName());
		response.setUserName(employeeService.getEmployeeFullName(updateconfig.getUserId()));
		return response;
	}


	@Override
	public NotificationConfigResponse getNotifiactionConfig(String orgId, String name) {
		NotificationConfigResponse response = new NotificationConfigResponse();
		NotificationConfigs config = notiConfigRepository.findByNameAndOrgId(name,orgId);
		if (null != config) {
			response = getNotifiactionConfig(config);
		}
		return  response;
		        
	}
	
	@Async
	@Override
	public void createNotification(String userId,String notificationType, String msg,String processNmae, String type ) {
		EmployeeDetails emp = employeeRepository.getEmployeesByuserId(userId);
		String name = employeeService.getEmployeeFullNameByObject(emp);
		EmployeeDetails reportingManager1 = employeeRepository.getEmployeesByuserId(emp.getReportingManager());
		List<EmployeeDetails> adminEmpList = employeeRepository.getActiveEmployeesByOrgIdAndRole(emp.getOrgId(),"ADMIN");
		NotificationRule rule = notificationRuleRepository.getNotificationByOrgId(emp.getOrgId());
		NotificationConfigs notificationConfigs;
		if(type.equalsIgnoreCase("create")) {
			notificationConfigs = notiConfigRepository.
					findByNameAndCreateIndAndOrgId(processNmae,true , emp.getOrgId());
		}else if(type.equalsIgnoreCase("update")){
			notificationConfigs = notiConfigRepository.
					findByNameAndUpdateIndAndOrgId(processNmae,true , emp.getOrgId());
		}else{
			notificationConfigs = notiConfigRepository.
					findByNameAndDeleteIndAndOrgId(processNmae,true, emp.getOrgId());
		}
		if (null != rule && null != notificationConfigs) {
			if(rule.isInappInd()){
						 
					List<String> assignToUserIds = new ArrayList<>();
					
					if (notificationConfigs.isReportingManager()) {
						assignToUserIds.add(emp.getReportingManager());
				        		}

				    if (notificationConfigs.isReportingManager1()) {
				    	String reportingManager1Id = reportingManager1.getReportingManager();
				    	assignToUserIds.add(reportingManager1Id);
				    }

				    if (notificationConfigs.isAdmin()) {
				    	List<String> adminIds = adminEmpList
				    			.stream().map(EmployeeDetails::getUserId).collect(Collectors.toList());	    			
				    	assignToUserIds.addAll(adminIds);				    	
				    }
				    for (String assignToUserId : assignToUserIds) {
			    		addToNotiTable(userId, notificationType, assignToUserId,name,emp.getOrgId(), msg);
					}
				}
			if(rule.isEmailInd()){
				List<String> toEmailIds = new ArrayList<>();
				
				if (notificationConfigs.isReportingManager()) {
					toEmailIds.add(reportingManager1.getEmailId());
			        		}

			    if (notificationConfigs.isReportingManager1()) {
			    	String reportingManager1Email = employeeRepository.getEmployeesByuserId(reportingManager1.getReportingManager()).getEmailId();
			    	toEmailIds.add(reportingManager1Email);
			    }

			    if (notificationConfigs.isAdmin()) {
			    	List<String> adminEmailIds = adminEmpList
			    			.stream().map(EmployeeDetails::getEmailId).collect(Collectors.toList());	    			
			    	toEmailIds.addAll(adminEmailIds);
			    }
			    	for (String to : toEmailIds) {
			    		String text = "<div style=' display: block; margin-top: 100px; '>"
								+ "    <div style='  text-align: center;'> </div>"
								+ "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
								+ "        <div class='box-2' style='  text-align: center;'>"
								+ "            <h1 style='text-align: center; padding: 10px;'>"
								+ " </h1>"
								+ "            <p style='text-align: center;'>"+msg
								+ "            </div>" + "        </div>" + "    </div>" + "</div>";
						
			    		try {
							emailService.sendMail( "support@innoverenit.com",  to,  "Notification...", text);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					}
				}
			}
	
	@Async
	@Override
	public void createNotificationForAll(String orgId,String userId, String notificationType, String msg,String processNmae, String type ) {
		List<EmployeeDetails> allUserList = employeeRepository.getEmployeesByOrgId(orgId);
//		NotificationRule rule = notificationRuleRepository.getNotificationByOrgId(orgId);
//		NotificationConfigs notificationConfigs = notiConfigRepository.
//				findByNameAndTypeAndOrgId(processNmae, type , orgId);
//		if (null != rule && null != notificationConfigs) {
//			if(rule.isInappInd()){
		if (null != allUserList && !allUserList.isEmpty()) {
//				    	List<String> assignToUserIds = allUserList
//				    			.stream().map(EmployeeDetails::getUserId).collect(Collectors.toList());	    			
				    
//				    for (EmployeeDetails user : allUserList) {
//				    	if(null!=user.getEmployeeId()) {
//				    	addToAllNotiTable(userId, notificationType, user.getEmployeeId(),orgId, msg);
//				    	}
////			if(rule.isEmailInd()){
//				
////			    	List<String> adminEmailIds = allUserList
////			    			.stream().map(EmployeeDetails::getEmailId).collect(Collectors.toList());	    			
////			    	for (String to : adminEmailIds) {
//				    	if(null!=user.getEmailId()) {
//			    		String text = "<div style=' display: block; margin-top: 100px; '>"
//								+ "    <div style='  text-align: center;'> </div>"
//								+ "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
//								+ "        <div class='box-2' style='  text-align: center;'>"
//								+ "            <h1 style='text-align: center; padding: 10px;'>"
//								+ " </h1>"
//								+ "            <p style='text-align: center;'>"+msg
//								+ "            </div>" + "        </div>" + "    </div>" + "</div>";
//						
//			    		try {
//							emailService.sendMail( "support@innoverenit.com",  user.getEmailId(),  "Notification...", text);
//						} catch (MessagingException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
			allUserList.parallelStream().forEach(user -> {
			    if (user.getEmployeeId() != null) {
			        addToAllNotiTable(userId, notificationType, user.getEmployeeId(), orgId, msg);
			    }

			    if (user.getEmailId() != null) {
			        String text = "<div style=' display: block; margin-top: 100px; '>"
			                + "    <div style='  text-align: center;'> </div>"
			                + "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
			                + "        <div class='box-2' style='  text-align: center;'>"
			                + "            <h1 style='text-align: center; padding: 10px;'>"
			                + " </h1>"
			                + "            <p style='text-align: center;'>" + msg
			                + "            </div>" + "        </div>" + "    </div>" + "</div>";

			        try {
			            emailService.sendMail("support@innoverenit.com", user.getEmailId(), "Notification...", text);
			        } catch (MessagingException e) {
			            // Handle exception appropriately
			            e.printStackTrace();
			        }
			    }
			});

			}
	}
	
	@Override
	public void addToAllNotiTable(String userId,String notificationType, String assignToUserId ,String orgId,String msg) {
		NotificationDetails notification = new NotificationDetails();
		notification.setNotificationType(notificationType);
		notification.setMessage(msg);
		notification.setUser_id(userId);
		notification.setAssignedBy(userId);
		notification.setAssignedTo(assignToUserId);
		notification.setNotificationDate(new Date());
		notification.setMessageReadInd(false);
		notification.setLiveInd(true);
		notification.setCreationDate(new Date());
		notification.setOrg_id(orgId);
		notificationRepository.save(notification);
	}
	
	@Override
	public void addToNotiTable(String userId,String notificationType, String assignToUserId,String name ,String orgId,String msg) {
		NotificationDetails notification = new NotificationDetails();
		notification.setNotificationType(notificationType);
		notification.setMessage(msg+name);
		notification.setUser_id(userId);
//		notification.setAssignedBy(name);
		notification.setAssignedTo(assignToUserId);
		notification.setNotificationDate(new Date());
		notification.setMessageReadInd(false);
		notification.setLiveInd(true);
		notification.setCreationDate(new Date());
		notification.setOrg_id(orgId);
		notificationRepository.save(notification);
	}
	
	@Async
	@Override
	public void createNotificationForDynamicUsers(Notificationparam param) throws IOException, TemplateException {
		EmployeeDetails emp = param.getEmployeeDetails();
//		String name = employeeService.getEmployeeFullNameByObject(emp);
		System.out.println("EmployeeDetails=="+emp.toString());
		EmployeeDetails reportingManager1 = employeeRepository.getEmployeesByuserId(emp.getReportingManager());
		List<EmployeeDetails> adminEmpList = employeeRepository.getActiveEmployeesByOrgIdAndRole(emp.getOrgId(),"ADMIN");
		NotificationRule rule = notificationRuleRepository.getNotificationByOrgId(emp.getOrgId());
		if(null!=rule) {
			NotificationConfigs notificationConfigs;
			if(param.getType().equalsIgnoreCase("create")) {
				notificationConfigs = notiConfigRepository.
						findByNameAndCreateIndAndOrgId(param.getProcessNmae(), true , emp.getOrgId());
			}else if(param.getType().equalsIgnoreCase("update")){
				notificationConfigs = notiConfigRepository.
						findByNameAndUpdateIndAndOrgId(param.getProcessNmae(),true , emp.getOrgId());
			}else{
				notificationConfigs = notiConfigRepository.
						findByNameAndDeleteIndAndOrgId(param.getProcessNmae(),true, emp.getOrgId());
			}
			
			if(rule.isInappInd()){
			addToNotiTableForMultiUser(param.getUserId(), param.getNotificationType(), param.getUserId(),emp.getOrgId(), param.getOwnMsg());
			}
			if(rule.isEmailInd()){
	//		sendMailText(param.getOwnMsg(), emp.getEmailId());
	//		sendMailText(param.getOwnMsg(),  emp.getEmailId(), param);
				sendMailText(param.getOwnMsg(),  emp, param);
			}
			
			if (null != rule && null != notificationConfigs) {
				if(rule.isInappInd()){
				Set<String> adminIds =  new HashSet<>();
				if (notificationConfigs.isAdmin()) {
					Set<String>  adminId = adminEmpList
			    			.stream().map(EmployeeDetails::getUserId).collect(Collectors.toSet());	
					adminIds.addAll(adminId);	
					}
						if (notificationConfigs.isReportingManager()) {
							adminIds.add(emp.getReportingManager());
					      }
	
					    if (notificationConfigs.isReportingManager1()) {
					    	adminIds.add(reportingManager1.getReportingManager());
					    }
	
					    for (String adminId : adminIds) {
				    		addToNotiTableForMultiUser(param.getUserId(), param.getNotificationType(), adminId,emp.getOrgId(), param.getAdminMsg());
					    }
					    if (null != param.getAssignToUserIds() && !param.getAssignToUserIds().isEmpty()) {
						    for (String assignToUserId : param.getAssignToUserIds()) {
					    		addToNotiTableForMultiUser(param.getUserId(), param.getNotificationType(), assignToUserId,emp.getOrgId(), param.getAssignToMsg());
							}
					    }
					    if (null != param.getIncludeedUserIds() && !param.getIncludeedUserIds().isEmpty()) {
						    for (String assignToUserId : param.getIncludeedUserIds()) {
					    		addToNotiTableForMultiUser(param.getUserId(), param.getNotificationType(), assignToUserId,emp.getOrgId(), param.getIncludeMsg());
							}
					    }
	
				}
			 if(rule.isEmailInd()){
	//			 Set<String> adminsEmailIds = new HashSet<>();
				 Set<EmployeeDetails> adminsEmailIds = new HashSet<>();
					
					if (notificationConfigs.isReportingManager()) {
	//					adminsEmailIds.add(reportingManager1.getEmailId());
						adminsEmailIds.add(reportingManager1);
				        		}
	
				    if (notificationConfigs.isReportingManager1()) {
				    	EmployeeDetails reportingManager1Email = employeeRepository.getEmployeesByuserId(reportingManager1.getReportingManager());
	//			    	adminsEmailIds.add(reportingManager1Email);
				    	adminsEmailIds.add(reportingManager1Email);
				    }
	
				    if (notificationConfigs.isAdmin()) {
	//			    	Set<String> adminsEmailId = adminEmpList
	//			    			.stream().map(EmployeeDetails::getEmailId).collect(Collectors.toSet());	    			
	//			    	adminsEmailIds.addAll(adminsEmailId);
				    	adminsEmailIds.addAll(adminEmpList);
				    }
	//			    for (String adminEmail : adminsEmailIds) {
				    for (EmployeeDetails adminEmail : adminsEmailIds) {
	//			    sendMailText(param.getAdminMsg(),adminEmail);
				   sendMailText( param.getAdminMsg(),  adminEmail, param);
				    }
				    if (null != param.getAssignToUserIds() && !param.getAssignToUserIds().isEmpty()) {			    	
				    	List<EmployeeDetails> assignToUserEmails = employeeRepository.getEmployeesByIds(param.getAssignToUserIds());
	//					    	List<String> assignToUserEmails = employeeRepository.getEmployeesByIds(param.getAssignToUserIds())
	//	    			.stream().map(EmployeeDetails::getEmailId).collect(Collectors.toList());
	//			    	for (String email : assignToUserEmails) {
				    		for (EmployeeDetails employeeDetails : assignToUserEmails) {
	//					    sendMailText(param.getAssignToMsg(),email);
				    		sendMailText( param.getAssignToMsg(),  employeeDetails, param);
						    }
				    	}
				    if (null != param.getIncludeedUserIds() && !param.getIncludeedUserIds().isEmpty()) {			    	
				    	List<EmployeeDetails> includeedUserIds = employeeRepository.getEmployeesByIds(param.getIncludeedUserIds());
				    		for (EmployeeDetails employeeDetails : includeedUserIds) {
				    		sendMailText( param.getIncludeMsg(),  employeeDetails, param);
						    }
				    	}
			 		}	
				}
			}	
		}
	
//	private void sendMailText(String msg, String to) {
//		
//		String text = "<div style=' display: block; margin-top: 100px; '>"
//				+ "    <div style='  text-align: center;'> </div>"
//				+ "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
//				+ "        <div class='box-2' style='  text-align: center;'>"
//				+ "            <h1 style='text-align: center; padding: 10px;'>"
//				+ " </h1>"
//				+ "            <p style='text-align: center;'>"+msg
//				+ "            </div>" + "        </div>" + "    </div>" + "</div>";
//		
//		try {
//			emailService.sendMail( "support@innoverenit.com",  to,  "Notification...", text);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	@Override
	public String getNotificationEmailContent(String companyName, String receiverName, String msg) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException{
		 StringWriter stringWriter = new StringWriter();
	     Map<String, Object> model = new HashMap<>();
        model.put("companyName", companyName);
        model.put("receiverName", receiverName);
        model.put("msg", msg);
        configuration.getTemplate("notification.ftlh").process(model, stringWriter);
//        System.out.println("................................................");
//        System.out.println("emailcontent"+stringWriter.getBuffer().toString());
//        System.out.println("................................................");
        return stringWriter.getBuffer().toString();
       
    }
	
	private void sendMailText(String msg, EmployeeDetails to,Notificationparam param) throws IOException, TemplateException {
		
		String text = getNotificationEmailContent(param.getCompanyName(), employeeService.getEmployeeFullNameByObject(to), msg);
		
		try {
			emailService.sendMail( "support@innoverenit.com",  to.getEmailId(),  param.getEmailSubject(), text);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addToNotiTableForMultiUser(String userId,String notificationType, String assignToUserId,String orgId,String msg) {
		NotificationDetails notification = new NotificationDetails();
		notification.setNotificationType(notificationType);
		notification.setMessage(msg);
		notification.setUser_id(userId);
//		notification.setAssignedBy(name);
		notification.setAssignedTo(assignToUserId);
		notification.setNotificationDate(new Date());
		notification.setMessageReadInd(false);
		notification.setLiveInd(true);
		notification.setCreationDate(new Date());
		notification.setOrg_id(orgId);
		notificationRepository.save(notification);
	}


	@Override
	public HashMap getNotificationCountUserId(String userId) {
		HashMap map = new HashMap();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(new Date()));
			start_date = Utility.removeTime(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<NotificationDetails> list = notificationRepository.getTodayNotificationUser(userId,start_date,end_date);
		map.put("notification", list.size());
		return map;
	}

	@Override
	public List<NotificationConfigResponse> getNotifiactionConfigByOrgId(String orgId) {
		List<NotificationConfigResponse> resultList = new ArrayList<>();
		List<NotificationConfigs> configList = notiConfigRepository.findByOrgId(orgId);
		for (NotificationConfigs config : configList) {
			if (null != config) {
				NotificationConfigResponse response = getNotifiactionConfig(config);
				if(null!=response) {
					resultList.add(response);
				}
			}
		}
		return  resultList;
	}

}
