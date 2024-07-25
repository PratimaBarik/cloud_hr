package com.app.employeePortal.ruleEngine.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.recruitment.entity.RecruitmentProcessStageDetails;
import com.app.employeePortal.recruitment.repository.RecruitmentStageDetailsRepository;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.reports.service.ExcelService;
import com.app.employeePortal.ruleEngine.entity.RecruitProMailRuleLink;
import com.app.employeePortal.ruleEngine.entity.RecruitProNotificationLink;
import com.app.employeePortal.ruleEngine.entity.RecruitmentStageMailRuleLink;
import com.app.employeePortal.ruleEngine.entity.RecruitproSponserEmailRuleLink;
import com.app.employeePortal.ruleEngine.entity.ReportScheduling;
import com.app.employeePortal.ruleEngine.entity.ReportSchedulingDelete;
import com.app.employeePortal.ruleEngine.mapper.RecruitProMailMapper;
import com.app.employeePortal.ruleEngine.mapper.RecruitProNotificationMapper;
import com.app.employeePortal.ruleEngine.mapper.ReportSchedulingMapper;
import com.app.employeePortal.ruleEngine.repository.RecruitProNotificationRepository;
import com.app.employeePortal.ruleEngine.repository.RecruitmentProMailRepository;
import com.app.employeePortal.ruleEngine.repository.RecruitmentStageMailRuleLinkRepository;
import com.app.employeePortal.ruleEngine.repository.RecruitproSponserEmailRuleLinkRepository;
import com.app.employeePortal.ruleEngine.repository.ReportSchedulingDeleteRepository;
import com.app.employeePortal.ruleEngine.repository.ReportSchedulingRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class RuleEngineServiceImpl implements RuleEngineService {
	
	@Autowired
	RecruitmentProMailRepository recruitmentProMailRepository;
	
	@Autowired
	RecruitProNotificationRepository recruitProNotificationRepository;
	@Autowired
	RecruitproSponserEmailRuleLinkRepository recruitproSponserEmailRuleLinkRepository;
	@Autowired
	RecruitmentStageMailRuleLinkRepository recruitmentStageMailRuleLinkRepository;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CandidateDetailsRepository candidateDetailsRepositorye;
	@Autowired
	RecruitmentStageDetailsRepository recruitmentStageDetailsRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	ReportSchedulingRepository reportSchedulingRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	ExcelService excelService;
	@Autowired
	ReportSchedulingDeleteRepository reportSchedulingDeleteRepository;
	

	@Override
	public String saveRecruitProMailRuleLink(RecruitProMailMapper recruitProMailLink) {
		
		if(null!=recruitProMailLink) {
			RecruitProMailRuleLink recruitProMailRuleLink = new RecruitProMailRuleLink(); 
			
			//recruitProMailRuleLink.setFrequency(recruitProMailLink.getCandidateFrequency());
			
			//recruitProMailRuleLink.setReceipent(recruitProMailLink.getRecepient());
			recruitProMailRuleLink.setOrg_id(recruitProMailLink.getOrgId());
			recruitProMailRuleLink.setAttachment_ind(recruitProMailLink.isAttachmentInd());
			recruitProMailRuleLink.setApprove_ind(recruitProMailLink.isApproverContactInd());
			recruitProMailRuleLink.setToggle_ind(recruitProMailLink.isCandidateToggleInd());
			recruitProMailRuleLink.setReminder_ind(recruitProMailLink.isCandidateReminderInd());
			recruitProMailRuleLink.setFrequency(recruitProMailLink.getCandidateFrequency());
			recruitProMailRuleLink.setNo_of_time(recruitProMailLink.getCandidateNoOfTimes());
			recruitProMailRuleLink.setCreation_date(new Date());
			recruitProMailRuleLink.setLive_ind(true);
			//recruitProMailRuleLink.setReceipent(recruitProMailLink.getRecepient());
			
			recruitmentProMailRepository.save(recruitProMailRuleLink);

		}
		return null;
	}

	@Override
	public RecruitProMailMapper recruitProMailMapper(String orgId) {
		
		List<RecruitProMailRuleLink> recruitProMailRuleLink =recruitmentProMailRepository.getRecruitMailRuleLink(orgId);
		
		RecruitProMailMapper recruitProMailMapper = new RecruitProMailMapper();
		
		List<String> receiver = new ArrayList<>();
        List<String> sponser = new ArrayList<>();
		
		if(null!=recruitProMailRuleLink && !recruitProMailRuleLink.isEmpty()) {

			 for (RecruitProMailRuleLink emailRuleLink2 : recruitProMailRuleLink) {
				 
			 
            receiver.add(emailRuleLink2.getReceipent());
			recruitProMailMapper.setOrgId(orgId);
			recruitProMailMapper.setTemplate(emailRuleLink2.getTemplate());
			recruitProMailMapper.setAttachmentInd(emailRuleLink2.isAttachment_ind());
			recruitProMailMapper.setApproverContactInd(emailRuleLink2.isApprove_ind());
			recruitProMailMapper.setCandidateToggleInd(emailRuleLink2.isToggle_ind());
			recruitProMailMapper.setCandidateReminderInd(emailRuleLink2.isReminder_ind());
			if (emailRuleLink2.isReminder_ind() == true) {
				recruitProMailMapper.setCandidateFrequency(emailRuleLink2.getFrequency());
				recruitProMailMapper.setCandidateNoOfTimes(emailRuleLink2.getNo_of_time());
			} else {

				recruitProMailMapper.setCandidateFrequency("");
				recruitProMailMapper.setCandidateNoOfTimes(0);
				}

			 }
		}
	
	 List<RecruitproSponserEmailRuleLink> sponerLinkList = recruitproSponserEmailRuleLinkRepository.getSponserEmailRuleLink(orgId);

    if (null != sponerLinkList && !sponerLinkList.isEmpty()) {

        for (RecruitproSponserEmailRuleLink recruitproSponserEmailRuleLink : sponerLinkList) {

            sponser.add(recruitproSponserEmailRuleLink.getReceiver());
            recruitProMailMapper.setSponserReceiver(sponser);
            recruitProMailMapper.setSponserTempId(recruitproSponserEmailRuleLink.getTemplate_id());
            recruitProMailMapper.setSponserApproveInd(recruitproSponserEmailRuleLink.isApproval_ind());
            recruitProMailMapper.setSponserAttachmentInd(recruitproSponserEmailRuleLink.isAttachment_ind());
            recruitProMailMapper.setSponsorToggleInd(recruitproSponserEmailRuleLink.isToggle_ind());
            recruitProMailMapper.setSponsorReminderInd(recruitproSponserEmailRuleLink.isReminder_ind());
            if (recruitproSponserEmailRuleLink.isReminder_ind() == true) {

                recruitProMailMapper.setSponsorFrequency(recruitproSponserEmailRuleLink.getFrequency());
                recruitProMailMapper.setSponsorNoOfTimes(recruitproSponserEmailRuleLink.getNo_of_time());
            } else {

                recruitProMailMapper.setSponsorFrequency("");
                recruitProMailMapper.setSponsorNoOfTimes(0);
            }

        }
    }

    List<RecruitmentStageMailRuleLink> stageList = recruitmentStageMailRuleLinkRepository.getStageRuleLink(orgId);

    if (null != stageList && !stageList.isEmpty()) {

        for (RecruitmentStageMailRuleLink recruitmentStageMailRuleLink : stageList) {

            recruitProMailMapper.setStageInd(recruitmentStageMailRuleLink.isStageInd());
            recruitProMailMapper.setStageTemplateId(recruitmentStageMailRuleLink.getTemplate());
        }
    }		
		return recruitProMailMapper;
	}

	@Override
	public String saveRecruitProNotificationRuleLink(RecruitProNotificationMapper recruitProNotificationMapper) {
		if (null!=recruitProNotificationMapper) {
			
			RecruitProNotificationLink recruitProNotificationRuleLink = new RecruitProNotificationLink();
			//recruitProNotificationRuleLink.setReceipent(recruitProNotificationMapper.getRecepient());
			recruitProNotificationRuleLink.setTemplate(recruitProNotificationMapper.getTemplate());
			recruitProNotificationRuleLink.setCreation_date(new Date());
			recruitProNotificationRuleLink.setLive_ind(false);
			
			recruitProNotificationRepository.save(recruitProNotificationRuleLink);
			
		}
		return null;
	}

	@Override
	public RecruitProNotificationMapper getRecruitProNotification(String orgId) {
		RecruitProNotificationLink recruitProNotificationLink =recruitProNotificationRepository.getRecruitNotificationById(orgId);
		
		RecruitProNotificationMapper recruitProNotificationMapper = new RecruitProNotificationMapper();
		if (null!=recruitProNotificationLink) {
			recruitProNotificationMapper.setTemplate(recruitProNotificationLink.getTemplate());
			
		}
		return recruitProNotificationMapper;
	}

	@Override
	public void sendEmailAtEachStageUpdateForCandidate(String orgId, String opportunityId, String candidateId,
			String stageId, String loginUser) {
		List<RecruitmentStageMailRuleLink> stageList = recruitmentStageMailRuleLinkRepository.getStageRuleLink(orgId);

		if (null != stageList && !stageList.isEmpty()) {

			RecruitmentStageMailRuleLink stageLink = stageList.get(0);

			if (null != stageLink && stageLink.isStageInd() == true) {

				OpportunityDetails opportunityDetails = opportunityDetailsRepository.getOpportunityDetailsByOpportunityId(opportunityId);

				String accountName = "";
				String oppName = "";

				if (null != opportunityDetails) {

					oppName = opportunityDetails.getOpportunityName();

					if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {

						Customer customer = customerRepository
								.getCustomerByIdAndLiveInd(opportunityDetails.getCustomerId());

						if (null != customer) {

							accountName = customer.getName();
						}
					}

				}

				String lastName = "";
				String candidateName = "";
				String mailId = "";

				CandidateDetails candidate = candidateDetailsRepositorye.getcandidateDetailsById(candidateId);

				if (null != candidate) {

					if (!StringUtils.isEmpty(candidate.getLastName())) {

						lastName = candidate.getLastName();
					}
					candidateName = candidate.getFirstName() + " " + lastName;

					mailId = candidate.getEmailId();

				}

				String stageName = "";
				RecruitmentProcessStageDetails stageDetails = recruitmentStageDetailsRepository.getRecruitmentStageDetailsByStageId(stageId);

				if (null != stageDetails) {

					stageName = stageDetails.getStage_name();

				}

				String subject = "<b>Automated No Reply</b> Progress on " + oppName + " with " + accountName + ".";

				String myvar = "<div>Dear " + candidateName + " ," + "    </div>"
						+ "    <div style=\"margin-top: 8px;\">With regards to " + oppName + " for " + accountName
						+ " , your profile is in <b>" + stageName + "</b> stage." + "    </div>"
						+ "    <div style=\"margin-top: 5px;\">" + "" + "        Keep you posted on the latest updates."
						+ "    </div>" + "    <br />" + "    <br />" + "" + "    <p>"
						+ "        This is an automated email, please don't reply.</p>";

				if (!StringUtils.isEmpty(mailId)) {

					try {
						emailService.sendMailFromAdmin(mailId, subject, myvar, loginUser, orgId);
					} catch (MessagingException | IOException e) {
						e.printStackTrace();
					}

				}
			}
		}		
	}

	@Override
	public String saveReportScheduling(ReportSchedulingMapper reportSchedulingMapper) {
		String msg = null;
	if(null!= reportSchedulingMapper) {
	ReportScheduling dbReportScheduling = reportSchedulingRepository.
			findByTypeAndDepartmentAndFrequency(reportSchedulingMapper.getType(),reportSchedulingMapper.getDepartment(),reportSchedulingMapper.getFrequency());
	if(null!= dbReportScheduling) {
		msg = "This combination already exists";
	}else {
		ReportScheduling reportScheduling = new ReportScheduling();
		reportScheduling.setOrgId(reportSchedulingMapper.getOrgId());
		reportScheduling.setType(reportSchedulingMapper.getType());
		reportScheduling.setDepartment(reportSchedulingMapper.getDepartment());
		reportScheduling.setFrequency(reportSchedulingMapper.getFrequency());
		reportScheduling.setUserId(reportSchedulingMapper.getUserId());
		reportScheduling.setCreationDate(new Date());
		reportSchedulingRepository.save(reportScheduling);
		
		
		ReportSchedulingDelete reportSchedulingDelete = new ReportSchedulingDelete();
		reportSchedulingDelete.setOrgId(reportSchedulingMapper.getOrgId());
		reportSchedulingDelete.setReportSchedulingId(reportScheduling.getReportSchedulingId());
		reportSchedulingDelete.setUpdationDate(new Date());
		reportSchedulingDelete.setUserId(reportSchedulingMapper.getUserId());
		reportSchedulingDeleteRepository.save(reportSchedulingDelete);
			
		msg = "Successfully created";
	}
    }
	return msg;
	}
	
	@Override
	public List<ReportSchedulingMapper> getReportSchedulingListByOrgId(String departmentId, String orgId) {
		List<ReportScheduling> reportSchedulingList = reportSchedulingRepository.findByDepartment(departmentId);
		List<ReportSchedulingMapper> mapperList = new ArrayList<>();
		if (null != reportSchedulingList && !reportSchedulingList.isEmpty()) {

			reportSchedulingList.stream().map(li -> {

				ReportSchedulingMapper reportSchedulingMapper = new ReportSchedulingMapper();

				reportSchedulingMapper.setReportSchedulingId(li.getReportSchedulingId());
				reportSchedulingMapper.setOrgId(li.getOrgId());
				reportSchedulingMapper.setType(li.getType());
				reportSchedulingMapper.setDepartment(li.getDepartment());
				reportSchedulingMapper.setFrequency(li.getFrequency());
				reportSchedulingMapper.setUserId(li.getUserId());
				reportSchedulingMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				
				mapperList.add(reportSchedulingMapper);

				return mapperList;
			}).collect(Collectors.toList());
		}
		
		List<ReportSchedulingDelete> reportSchedulingDelete = reportSchedulingDeleteRepository.findByOrgId(orgId);
		if (null != reportSchedulingDelete && !reportSchedulingDelete.isEmpty()) {
			Collections.sort(reportSchedulingDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			mapperList.get(0).setUpdationDate(Utility.getISOFromDate(reportSchedulingDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(reportSchedulingDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		

		return mapperList;
	}
	
	
		@Override
		@Scheduled(cron = "0 00 9 * * 1")
		//@Scheduled(cron = "0 00 12 * * MON")
		public void getAllReport() throws Exception {
		
		System.out.println("schedular started");
			
			/*ReportScheduling reportScheduling = reportSchedulingRepository.getReportForManagementweekly("Management","Weekly");*/
			List<ReportScheduling> reportSchedulingList = reportSchedulingRepository.findByFrequency("Weekly");
			
			for (ReportScheduling reportScheduling : reportSchedulingList) {		
			if (null != reportSchedulingList) {
						
				System.out.println("dep="+reportScheduling.getDepartment());
				Department dept = departmentRepository.findByName(reportScheduling.getDepartment());
				List<EmployeeDetails> list = employeeRepository.getEmployeeListByDepartmentId(dept.getDepartment_id());
				
				for(EmployeeDetails emp : list) {									
				    
					File excelFile = excelService.exportEmployeeListByUserToExcel(emp.getEmployeeId(),reportScheduling.getDepartment(),reportScheduling.getType(),"Weekly");					
	                String serverUrl ="https://develop.tekorero.com/kite/email/send";
	        	    HttpHeaders headers = new HttpHeaders();
	        	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
	        	    body.add("file",new FileSystemResource(excelFile)); 
	        	    body.add("fromEmail", "support@innoverenit.com");
	        	    body.add("message", "This is to inform you that it is the weekly requirement report.");
	        	    body.add("subject","Weekly Requirement Report");
	        	    body.add("toEmail", emp.getEmailId());
	        	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
	        	   RestTemplate restTemplate = new RestTemplate();
	        	   ResponseEntity<String> response = restTemplate
	        	     .postForEntity(serverUrl, requestEntity, String.class);
	        	   System.out.println("response="+response.toString());
	        	   
	        	   /*delete temp file after send*/ 
	        	   excelFile.delete();
						      
				}
			}
		}
	}
		
		
	@Override
	@Scheduled(cron ="0 0 23 * * ?")
	//@Scheduled(cron ="0 31 0 * * ?")
	public void getDailyReport() throws Exception {
	
	System.out.println("schedular started");
		
		/*ReportScheduling reportScheduling = reportSchedulingRepository.getReportForManagementweekly("Management","Weekly");*/
		List<ReportScheduling> reportSchedulingList = reportSchedulingRepository.findByFrequency("Daily");
		
		for (ReportScheduling reportScheduling : reportSchedulingList) {		
		if (null != reportSchedulingList) {
					
			System.out.println("dep="+reportScheduling.getDepartment());
			Department dept = departmentRepository.findByName(reportScheduling.getDepartment());
			if(null!=dept) {
			List<EmployeeDetails> list = employeeRepository.getEmployeeListByDepartmentId(dept.getDepartment_id());
			if(null!=list && !list.isEmpty()) {
			for(EmployeeDetails emp : list) {									
			    
				File excelFile = excelService.exportEmployeeListByUserToExcel(emp.getEmployeeId(),reportScheduling.getDepartment(),reportScheduling.getType(),"Daily");					
                String serverUrl ="https://develop.tekorero.com/kite/email/send";
        	    HttpHeaders headers = new HttpHeaders();
        	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
        	    body.add("file",new FileSystemResource(excelFile)); 
        	    body.add("fromEmail", "support@innoverenit.com");
        	    body.add("message", "This is to inform you that it is the Daily requirement report.");
        	    body.add("subject","Daily Requirement Report");
        	    body.add("toEmail", emp.getEmailId());
        	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
        	   RestTemplate restTemplate = new RestTemplate();
        	   ResponseEntity<String> response = restTemplate
        	     .postForEntity(serverUrl, requestEntity, String.class);
        	   System.out.println("response="+response.toString());
        	   
        	   /*delete temp file after send*/ 
        	   excelFile.delete();
					      
			}
			}
			}
		}
	}
}

	
	@Override
	@Scheduled(cron = "0 0 0 1 * ?")	// runs on the first day of each month
	//@Scheduled(cron ="0 09 18 * * ?")
	public void getMonthlyReport() throws Exception {
	
	System.out.println("schedular started");
		
		/*ReportScheduling reportScheduling = reportSchedulingRepository.getReportForManagementweekly("Management","Weekly");*/
		List<ReportScheduling> reportSchedulingList = reportSchedulingRepository.findByFrequency("Monthly");
		
		for (ReportScheduling reportScheduling : reportSchedulingList) {		
		if (null != reportSchedulingList) {
					
			System.out.println("dep="+reportScheduling.getDepartment());
			Department dept = departmentRepository.findByName(reportScheduling.getDepartment());
			List<EmployeeDetails> list = employeeRepository.getEmployeeListByDepartmentId(dept.getDepartment_id());
			
			for(EmployeeDetails emp : list) {									
			    
				File excelFile = excelService.exportEmployeeListByUserToExcel(emp.getEmployeeId(),reportScheduling.getDepartment(),reportScheduling.getType(),"Monthly");					
                String serverUrl ="https://develop.tekorero.com/kite/email/send";
        	    HttpHeaders headers = new HttpHeaders();
        	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
        	    body.add("file",new FileSystemResource(excelFile)); 
        	    body.add("fromEmail", "support@innoverenit.com");
        	    body.add("message", "This is to inform you that it is the Monthly requirement report.");
        	    body.add("subject","Monthly Requirement Report");
        	    body.add("toEmail", emp.getEmailId());
        	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
        	   RestTemplate restTemplate = new RestTemplate();
        	   ResponseEntity<String> response = restTemplate
        	     .postForEntity(serverUrl, requestEntity, String.class);
        	   System.out.println("response="+response.toString());
        	   
        	   /*delete temp file after send*/ 
        	   excelFile.delete();
					      
			}
		}
	}
}
	
	
	@Override
	//@Scheduled(cron = "0 42 15 1/1 * ? *")	
	public void getQuarterlyReport() throws Exception {
	
	System.out.println("schedular started");
		
		/*ReportScheduling reportScheduling = reportSchedulingRepository.getReportForManagementweekly("Management","Weekly");*/
		List<ReportScheduling> reportSchedulingList = reportSchedulingRepository.findByFrequency("Quarterly");
		for (ReportScheduling reportScheduling : reportSchedulingList) {
		if (null != reportSchedulingList) {
					
			System.out.println("dep="+reportScheduling.getDepartment());
			Department dept = departmentRepository.findByName(reportScheduling.getDepartment());
			List<EmployeeDetails> list = employeeRepository.getEmployeeListByDepartmentId(dept.getDepartment_id());
			for(EmployeeDetails emp : list) {

				File excelFile = excelService.exportEmployeeListByUserToExcel(emp.getEmployeeId(),reportScheduling.getDepartment(),reportScheduling.getType(),"Quarterly");
                String serverUrl ="https://develop.tekorero.com/kite/email/send";
        	    HttpHeaders headers = new HttpHeaders();
        	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
        	    body.add("file",new FileSystemResource(excelFile)); 
        	    body.add("fromEmail", "support@innoverenit.com");
        	    body.add("message", "This is to inform you that it is the Quarterly requirement report.");
        	    body.add("subject","Quarterly Requirement Report");
        	    body.add("toEmail", emp.getEmailId());
        	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
        	   RestTemplate restTemplate = new RestTemplate();
        	   ResponseEntity<String> response = restTemplate
        	     .postForEntity(serverUrl, requestEntity, String.class);
        	   System.out.println("response="+response.toString());
        	   
        	   /*delete temp file after send*/ 
        	   excelFile.delete();

			}
		}
	}
}

	@Override
	public void deleteReportSchedulingRule(String reportSchedulingId) {
		
		ReportScheduling reportScheduling = reportSchedulingRepository.findByReportSchedulingId(reportSchedulingId);
		if(null!=reportScheduling) {
			ReportSchedulingDelete reportSchedulingDelete = reportSchedulingDeleteRepository.findByReportSchedulingId(reportSchedulingId);
			if(null!=reportSchedulingDelete) {
				reportSchedulingDelete.setUpdationDate(new Date());
				reportSchedulingDeleteRepository.save(reportSchedulingDelete);
			}
			reportSchedulingRepository.delete(reportScheduling);
		}
	}
}

