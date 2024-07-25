package com.app.employeePortal.template.service;

import java.util.List;

import com.app.employeePortal.template.mapper.NotificationTemplateMapper;
import com.app.employeePortal.template.mapper.SignatureMapper;
import com.app.employeePortal.template.mapper.TemplateMapper;


public interface TemplateService {
	public TemplateMapper SaveEmailTemplate(TemplateMapper templateMapper);

	public TemplateMapper getEmailTemplateData(String templateId);

	public List<TemplateMapper> getEmailTemplateByOrgId(String orgId);
	
	public TemplateMapper updateEmailTemplate(TemplateMapper templateMapper);

	public SignatureMapper updateSignatureTemplate(SignatureMapper signatureMapper);
	
	public SignatureMapper getSignatureDetails(String signatureId);

	public SignatureMapper getAdminSignature(String orgId);

	public SignatureMapper getUserSignature(String userId);

	public NotificationTemplateMapper insertToNotificationTemplate(NotificationTemplateMapper notificationTemplateMapper);

	public List<NotificationTemplateMapper> fetchNotificationTemplateDetails(String orgId);

	public NotificationTemplateMapper getNotificationTemplate(String notificationTemplateId);

	public String updateNotificationTemplate(NotificationTemplateMapper notificationTemplateMapper);
}
