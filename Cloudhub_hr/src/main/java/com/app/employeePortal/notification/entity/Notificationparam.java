package com.app.employeePortal.notification.entity;

import java.util.List;

import com.app.employeePortal.employee.entity.EmployeeDetails;

import lombok.Data;

@Data
public class Notificationparam {

	private String userId;
	private List<String> assignToUserIds;
	private String notificationType;
	private String ownMsg;
	private String adminMsg;
	private String assignToMsg;
	private String includeMsg;
	private String processNmae;
	private String type;
	private String emailSubject;
	private EmployeeDetails employeeDetails;
	private String companyName;
	private List<String> includeedUserIds;
}
