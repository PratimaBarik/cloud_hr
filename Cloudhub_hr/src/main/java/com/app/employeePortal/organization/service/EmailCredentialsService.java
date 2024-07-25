package com.app.employeePortal.organization.service;

import java.util.List;

import com.app.employeePortal.organization.mapper.EmailCredentialsMapper;

public interface EmailCredentialsService {
	
	public String saveEmailCredentials(EmailCredentialsMapper emailCredentialsMapper);
	
	public EmailCredentialsMapper getEmailCredentialsByOrgId(String orgId);
	
	public EmailCredentialsMapper updateEmailCredentials(EmailCredentialsMapper emailCredentialsMapper);

	public List<EmailCredentialsMapper> getEmailCredentialsByUserId(String userId);

	public String saveUserEmailCredentials(EmailCredentialsMapper emailCredentialsMapper);

	public List<EmailCredentialsMapper> updateDefaultSetting(EmailCredentialsMapper emailCredentialsMapper);

}
