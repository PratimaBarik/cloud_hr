package com.app.employeePortal.notification.service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.mapper.NotificationConfigRequest;
import com.app.employeePortal.notification.mapper.NotificationConfigResponse;
import com.app.employeePortal.notification.mapper.NotificationMapper;
import com.app.employeePortal.notification.mapper.NotificationRuleMapper;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
@Transactional
public interface NotificationService {

	List<NotificationMapper> getTodaysNotificationsByUserId(String assignedTo) throws Exception;

	List<NotificationMapper> getPreviousNotificationsByUserId(String assignedTo) throws Exception;
	
	public NotificationMapper getNotificationResponseByNotificationId(String notificationId);

	public String patchNotification(String notificationId, NotificationMapper notificationMapper);

	public NotificationRuleMapper updateNotifiactionRule(NotificationRuleMapper notificationRuleMapper);

	public NotificationRuleMapper getNotificationRuleByOrgId(String orgId);

	boolean getInappNotificationRule(String orgId);

	NotificationConfigResponse updateNotifiactionConfig(NotificationConfigRequest mapper, String userId, String orgId);

	void createNotification(String userId,String notificationType, String msg ,String processNmae, String type);

	void createNotificationForAll(String orgId, String userId, String notificationType, String msg, String processNmae,
			String type);

	void addToNotiTable(String userId, String notificationType, String assignToUserId, String name, String orgId,
			String msg);

	void addToAllNotiTable(String userId, String notificationType, String assignToUserId, String orgId, String msg);

	void createNotificationForDynamicUsers(Notificationparam param) throws IOException, TemplateException;

	NotificationConfigResponse getNotifiactionConfig(String orgId,String name);

	HashMap getNotificationCountUserId(String userId);

	String getNotificationEmailContent(String companyName, String receiverName, String msg)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException,
			IOException;

	List<NotificationConfigResponse> getNotifiactionConfigByOrgId(String orgId);	

}
