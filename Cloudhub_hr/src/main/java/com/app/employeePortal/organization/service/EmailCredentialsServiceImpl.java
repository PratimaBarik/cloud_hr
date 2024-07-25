package com.app.employeePortal.organization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.organization.entity.EmailCredentialDetails;
import com.app.employeePortal.organization.mapper.EmailCredentialsMapper;
import com.app.employeePortal.organization.repository.EmailCredentialRepository;

@Service
public class EmailCredentialsServiceImpl implements EmailCredentialsService {
	
	@Autowired
	EmailCredentialRepository emailCredentialRepository;
	
	
	
	@Override
	public String saveEmailCredentials(EmailCredentialsMapper emailCredentialsMapper) {

		EmailCredentialDetails emailCredentialDetails = new EmailCredentialDetails();
		emailCredentialDetails.setEmail(emailCredentialsMapper.getEmail());
		emailCredentialDetails.setPassword(emailCredentialsMapper.getPassword());
		emailCredentialDetails.setHost(emailCredentialsMapper.getHost());
		emailCredentialDetails.setPort(emailCredentialsMapper.getPort());
		emailCredentialDetails.setEmployee_id(emailCredentialsMapper.getEmployeeId());
		emailCredentialDetails.setOrg_id(emailCredentialsMapper.getOrganizationId());
		emailCredentialDetails.setCreation_date(new Date());
		EmailCredentialDetails newEmailCredentialDetails = emailCredentialRepository.save(emailCredentialDetails);
		String id = newEmailCredentialDetails.getEmail_credentials_id();
		return id;
	}

	@Override
	public EmailCredentialsMapper getEmailCredentialsByOrgId(String orgId) {

		EmailCredentialDetails emailCredentialDetails =emailCredentialRepository.getEmailCredentialsByOrgId(orgId);
		EmailCredentialsMapper emailCredentialsMapper = new EmailCredentialsMapper();
		if(null!=emailCredentialDetails) {
			emailCredentialsMapper.setId(emailCredentialDetails.getEmail_credentials_id());
			emailCredentialsMapper.setEmail(emailCredentialDetails.getEmail());
			emailCredentialsMapper.setPassword(emailCredentialDetails.getPassword());
			emailCredentialsMapper.setHost(emailCredentialDetails.getHost());
			emailCredentialsMapper.setPort(emailCredentialDetails.getPort());
			emailCredentialsMapper.setEmployeeId(emailCredentialDetails.getEmployee_id());
			emailCredentialsMapper.setOrganizationId(emailCredentialDetails.getOrg_id());
		}
		
		return emailCredentialsMapper;
	}

	@Override
	public EmailCredentialsMapper updateEmailCredentials(EmailCredentialsMapper emailCredentialsMapper) {
		EmailCredentialDetails emailCredentialDetails =emailCredentialRepository.getById(emailCredentialsMapper.getId());
		if(null!=emailCredentialDetails) {
			
			if(!StringUtils.isEmpty(emailCredentialsMapper.getEmail()))
				emailCredentialDetails.setEmail(emailCredentialsMapper.getEmail());
			if(!StringUtils.isEmpty(emailCredentialsMapper.getPassword()))
				emailCredentialDetails.setPassword(emailCredentialsMapper.getPassword());
			if(!StringUtils.isEmpty(emailCredentialsMapper.getHost()))
				emailCredentialDetails.setHost(emailCredentialsMapper.getHost());
			if(emailCredentialsMapper.getPort()!=0)
				emailCredentialDetails.setPort(emailCredentialsMapper.getPort());
		}
		emailCredentialRepository.save(emailCredentialDetails);
		EmailCredentialsMapper resultMapper = getEmailCredentialsByOrgId(emailCredentialsMapper.getOrganizationId());
		return resultMapper;
	}

	@Override
	public List<EmailCredentialsMapper> getEmailCredentialsByUserId(String userId) {
		List<EmailCredentialDetails> emailCredentialDetails =emailCredentialRepository.getEmailCredentialsByUserId(userId);
		if(null!=emailCredentialDetails && !emailCredentialDetails.isEmpty()) {
			return emailCredentialDetails.stream().map(emailCredentialDetails1->{
				EmailCredentialsMapper emailCredentialsMapper = new EmailCredentialsMapper();
			
			emailCredentialsMapper.setId(emailCredentialDetails1.getEmail_credentials_id());
			emailCredentialsMapper.setEmail(emailCredentialDetails1.getEmail());
			emailCredentialsMapper.setPassword(emailCredentialDetails1.getPassword());
			emailCredentialsMapper.setHost(emailCredentialDetails1.getHost());
			emailCredentialsMapper.setPort(emailCredentialDetails1.getPort());
			emailCredentialsMapper.setEmployeeId(emailCredentialDetails1.getEmployee_id());
			emailCredentialsMapper.setOrganizationId(emailCredentialDetails1.getOrg_id());
			emailCredentialsMapper.setDefaultInd(emailCredentialDetails1.isDefaultInd());
				return emailCredentialsMapper;

		}).collect(Collectors.toList());

	}
        return null;
}

	@Override
	public String saveUserEmailCredentials(EmailCredentialsMapper emailCredentialsMapper) {
		List<EmailCredentialDetails> emailCredentialDetails1 = emailCredentialRepository.getEmailCredentialByUserId(emailCredentialsMapper.getEmployeeId());
		 if (null != emailCredentialDetails1 && emailCredentialDetails1.size()!=0) {
			 EmailCredentialDetails emailCredentialDetails = new EmailCredentialDetails();
				emailCredentialDetails.setEmail(emailCredentialsMapper.getEmail());
				emailCredentialDetails.setPassword(emailCredentialsMapper.getPassword());
				emailCredentialDetails.setHost(emailCredentialsMapper.getHost());
				emailCredentialDetails.setPort(emailCredentialsMapper.getPort());
				emailCredentialDetails.setEmployee_id(emailCredentialsMapper.getEmployeeId());
				//emailCredentialDetails.setOrg_id(emailCredentialsMapper.getOrganizationId());
				emailCredentialDetails.setCreation_date(new Date());
				emailCredentialDetails.setDefaultInd(false);
				EmailCredentialDetails newEmailCredentialDetails = emailCredentialRepository.save(emailCredentialDetails);
				String id = newEmailCredentialDetails.getEmail_credentials_id();
				return id;
		 }else { 
			 EmailCredentialDetails emailCredentialDetails = new EmailCredentialDetails();
				emailCredentialDetails.setEmail(emailCredentialsMapper.getEmail());
				emailCredentialDetails.setPassword(emailCredentialsMapper.getPassword());
				emailCredentialDetails.setHost(emailCredentialsMapper.getHost());
				emailCredentialDetails.setPort(emailCredentialsMapper.getPort());
				emailCredentialDetails.setEmployee_id(emailCredentialsMapper.getEmployeeId());
				//emailCredentialDetails.setOrg_id(emailCredentialsMapper.getOrganizationId());
				emailCredentialDetails.setCreation_date(new Date());
				emailCredentialDetails.setDefaultInd(true);
				EmailCredentialDetails newEmailCredentialDetails = emailCredentialRepository.save(emailCredentialDetails);
				String id = newEmailCredentialDetails.getEmail_credentials_id();
				return id;
		 }
	}

	@Override
	public List<EmailCredentialsMapper> updateDefaultSetting(EmailCredentialsMapper emailCredentialsMapper) {
		List<EmailCredentialsMapper> resultMapper = new ArrayList<>();
		EmailCredentialDetails emailCredentialDetails =emailCredentialRepository.
				getEmailCredentialByUserIdAndDefaultInd(emailCredentialsMapper.getEmployeeId());
		if(null != emailCredentialDetails) {
			emailCredentialDetails.setDefaultInd(false);
			emailCredentialRepository.save(emailCredentialDetails);
			
			EmailCredentialDetails emailCredentialDetails1 =emailCredentialRepository.
					getEmailCredentialById(emailCredentialsMapper.getId());
			if(null != emailCredentialDetails1) {
				emailCredentialDetails1.setDefaultInd(true);
				emailCredentialRepository.save(emailCredentialDetails1);
			}
			
		}
		
		resultMapper = getEmailCredentialsByUserId(emailCredentialsMapper.getEmployeeId());
		
		return resultMapper;
	}

}
