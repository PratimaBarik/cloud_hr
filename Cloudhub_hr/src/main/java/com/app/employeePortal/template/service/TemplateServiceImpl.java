package com.app.employeePortal.template.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.template.entity.EmailTemplateDetails;
import com.app.employeePortal.template.entity.NotificationTemplateDetails;
import com.app.employeePortal.template.entity.SignatureDetails;
import com.app.employeePortal.template.mapper.NotificationTemplateMapper;
import com.app.employeePortal.template.mapper.SignatureMapper;
import com.app.employeePortal.template.mapper.TemplateMapper;
import com.app.employeePortal.template.repository.EmailTemplateRepository;
import com.app.employeePortal.template.repository.NotificationTemplateRepository;
import com.app.employeePortal.template.repository.SignatureRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {
	@Autowired
	EmailTemplateRepository templateRepository;
	@Autowired
	SignatureRepository signatureRepository;
	@Autowired
	NotificationTemplateRepository notificationTemplateRepository;
	@Autowired
	CustomerRepository customerRepository;


	@Override
	public TemplateMapper SaveEmailTemplate(TemplateMapper templateMapper) {
	    	EmailTemplateDetails templeteDetails = new EmailTemplateDetails();		
			templeteDetails.setTemplate(templateMapper.getTemplate());
			templeteDetails.setType(templateMapper.getType());
			templeteDetails.setSubject(templateMapper.getSubject());
			templeteDetails.setCustomerId(templateMapper.getCustomerId());
			templeteDetails.setDescription(templateMapper.getDescription());
			templeteDetails.setOrganization_id(templateMapper.getOrgId());
			templeteDetails.setUser_id(templateMapper.getUserId());	
			templeteDetails.setCreation_date(new Date());
			templeteDetails.setLive_ind(true);						
			EmailTemplateDetails emailTemplateDetails = templateRepository.save(templeteDetails);
			String templateId = emailTemplateDetails.getTemplete_id();
			TemplateMapper resultMapper = null;
			if(!StringUtils.isEmpty(templateId)) {
				resultMapper = getEmailTemplateData(templateId);		
			}
			return resultMapper;
		
	}

	@Override
	public TemplateMapper getEmailTemplateData(String templateId) {

		EmailTemplateDetails emailTemplateDetails =	templateRepository.getById(templateId);
		TemplateMapper templateMapper = new TemplateMapper();

		if(null!=emailTemplateDetails) {
			templateMapper.setTemplateId(templateId);
			templateMapper.setSubject(emailTemplateDetails.getSubject());
			templateMapper.setTemplate(emailTemplateDetails.getTemplate());
			templateMapper.setOrgId(emailTemplateDetails.getOrganization_id());
			templateMapper.setUserId(emailTemplateDetails.getUser_id());
			templateMapper.setType(emailTemplateDetails.getType());
		    templateMapper.setCreationDate(Utility.getISOFromDate(emailTemplateDetails.getCreation_date()));	
		    templateMapper.setDescription(emailTemplateDetails.getDescription());
		    if(!StringUtils.isEmpty(emailTemplateDetails.getCustomerId())) {
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(emailTemplateDetails.getCustomerId());
				if(null != customer) {
					templateMapper.setCustomerId(customer.getCustomerId());
					templateMapper.setCustomerName(customer.getName());
				}
			}
		}
		
		return templateMapper;
	}

	@Override
	public List<TemplateMapper> getEmailTemplateByOrgId(String orgId) {
		
		List<TemplateMapper> resultList = new ArrayList<TemplateMapper>();
		List<EmailTemplateDetails> emailTemplate = templateRepository.getTempleteDetailsListByOrgId(orgId);
		if(null!=emailTemplate && !emailTemplate.isEmpty()) {
			emailTemplate.stream().map(emailTemplateDetails -> {
				TemplateMapper templateMapper =getEmailTemplateData(emailTemplateDetails.getTemplete_id());
				resultList.add(templateMapper);
				return resultList;
			}).collect(Collectors.toList());
		}
		
		return resultList;
	}

	@Override
	public TemplateMapper updateEmailTemplate(TemplateMapper templateMapper) {
		EmailTemplateDetails emailTemplateDetails =	templateRepository.getById(templateMapper.getTemplateId());
		if(null != emailTemplateDetails) {
			emailTemplateDetails.setLive_ind(false);
			templateRepository.save(emailTemplateDetails);			
		
		EmailTemplateDetails templateDetailsNew = new EmailTemplateDetails();			
			
			if(null !=templateMapper.getType()) {
				
				
				templateDetailsNew.setType(templateMapper.getType());

			}else {
				
				templateDetailsNew.setType(emailTemplateDetails.getType());
			}
			if(null !=templateMapper.getTemplate()) {
				templateDetailsNew.setTemplate(templateMapper.getTemplate());
			}else {
				templateDetailsNew.setTemplate(emailTemplateDetails.getTemplate());
	
			}
			if(null !=templateMapper.getDescription()) {
				templateDetailsNew.setDescription(templateMapper.getDescription());
			}else {
				templateDetailsNew.setDescription(emailTemplateDetails.getDescription());
	
			}
			if(null !=templateMapper.getCustomerId()) {
				templateDetailsNew.setCustomerId(templateMapper.getCustomerId());
			}else {
				templateDetailsNew.setCustomerId(emailTemplateDetails.getCustomerId());
	
			}
			if(null !=templateMapper.getSubject()) {
				templateDetailsNew.setSubject(templateMapper.getSubject());
			}else {
				templateDetailsNew.setSubject(emailTemplateDetails.getSubject());
	
			}
			templateDetailsNew.setUser_id(templateMapper.getUserId());
			templateDetailsNew.setCreation_date(new Date());
			templateDetailsNew.setOrganization_id(templateMapper.getOrgId());
			templateDetailsNew.setLive_ind(true);
    		EmailTemplateDetails templeteDetails = templateRepository.save(templateDetailsNew);		
    		
		
		TemplateMapper resultMapper = getEmailTemplateData(templeteDetails.getTemplete_id());
		return resultMapper;
		}
		return null;
	}

	@Override
	public SignatureMapper updateSignatureTemplate(SignatureMapper signatureMapper) {
		SignatureDetails newSignDetails = new SignatureDetails();
		String signatureId = null;
		if(signatureMapper.getType().equalsIgnoreCase("User")) {
			
			SignatureDetails dbUserSignature = signatureRepository.getSignatureDetailsByUserId(signatureMapper.getUserId());

			if(null!=dbUserSignature) {
			//update user signature	
				
				if(null!=signatureMapper.getSignature()) {
					dbUserSignature.setSignature(signatureMapper.getSignature());
				}else {
					dbUserSignature.setSignature(dbUserSignature.getSignature());
	
					
				}
				SignatureDetails details = 	signatureRepository.save(dbUserSignature);
				signatureId = details.getSignature_id();
			}else {
			//insert user signature	
				newSignDetails.setSignature(signatureMapper.getSignature());
				newSignDetails.setUser_id(signatureMapper.getUserId());
				newSignDetails.setOrg_id(signatureMapper.getOrgId());
				newSignDetails.setType("User");
				newSignDetails.setLive_ind(true);
				newSignDetails.setCreation_date(new Date());
				newSignDetails.setSignature_id(signatureMapper.getSignatureId());
				SignatureDetails details =	signatureRepository.save(newSignDetails);
				signatureId = details.getSignature_id();

			}
			
		}else if(signatureMapper.getType().equalsIgnoreCase("Admin")) {
			SignatureDetails dbAdminSignature = signatureRepository.getSignatureDetailsByOrgId(signatureMapper.getOrgId());
			if(null!=dbAdminSignature) {
				//update admin signature
				if(null!=signatureMapper.getSignature()) {
					dbAdminSignature.setSignature(signatureMapper.getSignature());
				}else {
					dbAdminSignature.setSignature(dbAdminSignature.getSignature());
	
					
				}
				SignatureDetails details =	signatureRepository.save(newSignDetails);
				signatureId = details.getSignature_id();

				}else {
				//insert admin signature	
					newSignDetails.setSignature(signatureMapper.getSignature());
					newSignDetails.setSignature_id(signatureMapper.getSignatureId());
					newSignDetails.setUser_id(signatureMapper.getUserId());
					newSignDetails.setOrg_id(signatureMapper.getOrgId());
					newSignDetails.setType("Admin");
					newSignDetails.setLive_ind(true);
					newSignDetails.setCreation_date(new Date());
					SignatureDetails details =	signatureRepository.save(newSignDetails);	
					signatureId = details.getSignature_id();

				}
			
		}
		SignatureMapper resultMapper = getSignatureDetails(signatureId);
	
		return resultMapper;
	}

	@Override
	public SignatureMapper getSignatureDetails(String signatureId) {

		SignatureMapper signatureMapper = new SignatureMapper();
		
		SignatureDetails details =	signatureRepository.getById(signatureId);
		if(null!=details) {
			signatureMapper.setSignature(details.getSignature());
			signatureMapper.setUserId(details.getUser_id());
			signatureMapper.setOrgId(details.getOrg_id());
			signatureMapper.setType(details.getType());
			signatureMapper.setSignatureId(signatureId);
		}
		
		return signatureMapper;
	}

	@Override
	public SignatureMapper getAdminSignature(String orgId) {
		SignatureDetails dbUserSignature = signatureRepository.getSignatureDetailsByOrgId(orgId);
		SignatureMapper signatureMapper = null;
		if(null!=dbUserSignature) {
			signatureMapper=getSignatureDetails(dbUserSignature.getSignature_id());		
		}
		
		return signatureMapper;
	}

	@Override
	public SignatureMapper getUserSignature(String userId) {
		SignatureDetails dbUserSignature = signatureRepository.getSignatureDetailsByUserId(userId);
		SignatureMapper signatureMapper = null;
		if(null!=dbUserSignature) {
			signatureMapper=getSignatureDetails(dbUserSignature.getSignature_id());		
		}
		
		return signatureMapper;
	}

	@Override
	public NotificationTemplateMapper insertToNotificationTemplate(NotificationTemplateMapper notificationTemplateMapper) {

	        if (null != notificationTemplateMapper) {

	            NotificationTemplateDetails notificationTemplate = new NotificationTemplateDetails();

	            notificationTemplate.setMessage(notificationTemplateMapper.getMessage());
	          //  notificationTemplate.setSubject(notificationTemplateMapper.getSubject());
	            notificationTemplate.setCreation_date(new Date());
	            notificationTemplate.setUserId(notificationTemplateMapper.getUserId());
	            notificationTemplate.setOrganizationId(notificationTemplateMapper.getOrgId());
	            notificationTemplate.setType(notificationTemplateMapper.getNotificationName());
	            notificationTemplate.setDescription(notificationTemplateMapper.getDescription());
	            notificationTemplate.setLiveInd(true);

	            NotificationTemplateDetails notificationTemplate1 = notificationTemplateRepository.save(notificationTemplate);

	            String notiTemplateId = notificationTemplate1.getNotificationTempleteId();
	            NotificationTemplateMapper resultMapper = null;
				if(!StringUtils.isEmpty(notiTemplateId)) {
					resultMapper = getNotificationTemplate(notiTemplateId);		
				}
	        
				return resultMapper;
				}
			return null;
	         }
	    

	@Override
	public List<NotificationTemplateMapper> fetchNotificationTemplateDetails(String orgId) {
        List<NotificationTemplateDetails> list = notificationTemplateRepository.findByOrganizationIdAndLiveInd(orgId,true);

        List<NotificationTemplateMapper> notificationList = new ArrayList<>();

        if (null != list && !list.isEmpty()) {

			
				list.stream().map(notificationTemplate -> {
				NotificationTemplateMapper notificationTemplateMapper = new NotificationTemplateMapper();

				notificationTemplateMapper.setCreationDate(Utility.getISOFromDate(notificationTemplate.getCreation_date()));
				notificationTemplateMapper.setMessage(notificationTemplate.getMessage());
				notificationTemplateMapper.setNotificationName(notificationTemplate.getType());
				notificationTemplateMapper.setNotificationTemplateId(notificationTemplate.getNotificationTempleteId());
				notificationTemplateMapper.setOrgId(notificationTemplate.getOrganizationId());
				notificationTemplateMapper.setUserId(notificationTemplate.getUserId());
				notificationTemplateMapper.setDescription(notificationTemplate.getDescription());
				notificationList.add(notificationTemplateMapper);

				return notificationList;
				}).collect(Collectors.toList());
		}
        return notificationList;
    }
	
	 @Override
    public NotificationTemplateMapper getNotificationTemplate(String notificationTempleteId) {

        NotificationTemplateDetails notificationTemplate = notificationTemplateRepository.findByNotificationTempleteIdAndLiveInd(notificationTempleteId,true);

        NotificationTemplateMapper notificationTemplateMapper = new NotificationTemplateMapper();

        if (null != notificationTemplate) {


            notificationTemplateMapper.setCreationDate(Utility.getISOFromDate(notificationTemplate.getCreation_date()));
            notificationTemplateMapper.setMessage(notificationTemplate.getMessage());
            notificationTemplateMapper.setNotificationName(notificationTemplate.getType());
            notificationTemplateMapper.setNotificationTemplateId(notificationTemplate.getNotificationTempleteId());
            notificationTemplateMapper.setOrgId(notificationTemplate.getOrganizationId());
            notificationTemplateMapper.setUserId(notificationTemplate.getUserId());
            notificationTemplateMapper.setDescription(notificationTemplate.getDescription());

        }


        return notificationTemplateMapper;
    }

	@Override
	public String updateNotificationTemplate(NotificationTemplateMapper notificationTemplateMapper) {
		NotificationTemplateDetails notificationTemplate =
                notificationTemplateRepository.findByNotificationTempleteIdAndLiveInd(notificationTemplateMapper.getNotificationTemplateId(),true);

        if (null != notificationTemplate) {

            notificationTemplate.setLiveInd(false);
            notificationTemplateRepository.save(notificationTemplate);

            NotificationTemplateDetails notificationTemplateNew = new NotificationTemplateDetails();


            if (null != notificationTemplateMapper.getMessage()) {


                notificationTemplateNew.setMessage(notificationTemplateMapper.getMessage());

            } else {

                notificationTemplateNew.setMessage(notificationTemplate.getMessage());
            }
            if (null != notificationTemplateMapper.getNotificationName()) {


                notificationTemplateNew.setType(notificationTemplateMapper.getNotificationName());

            } else {

                notificationTemplateNew.setType(notificationTemplate.getType());
            }
            if (null != notificationTemplateMapper.getDescription()) {


                notificationTemplateNew.setDescription(notificationTemplateMapper.getDescription());

            } else {

                notificationTemplateNew.setDescription(notificationTemplate.getDescription());
            }

            notificationTemplateNew.setNotificationTempleteId(notificationTemplateMapper.getUserId());
            notificationTemplateNew.setCreation_date(new Date());
            notificationTemplateNew.setOrganizationId(notificationTemplate.getOrganizationId());
            notificationTemplateNew.setLiveInd(true);


            NotificationTemplateDetails notificationTemplate1 = notificationTemplateRepository.save(notificationTemplateNew);
            String notificationTemplateId = notificationTemplate1.getNotificationTempleteId();

        }

        return notificationTemplateMapper.getNotificationTemplateId();


    }



	
	
	
	
}
