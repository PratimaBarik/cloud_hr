package com.app.employeePortal.notification.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="notification_rule")
public class NotificationRule {
	
	@Id
	@GenericGenerator(name = "notification_rule_id", strategy = "com.app.employeePortal.notification.generator.NotificationRuleGenerator")
	@GeneratedValue(generator = "notification_rule_id")

	@Column(name="notification_rule_id")
	private String notificationRuleId;
	
	@Column(name="sms_ind")
	private boolean smsInd;
	
	@Column(name="whatsapp_ind")
	private boolean whatsappInd;
	
	@Column(name="email_ind")
	private boolean emailInd;
	
	@Column(name="inapp_ind")
	private boolean inappInd;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="updated_date")
	private Date updatedDate;
}
