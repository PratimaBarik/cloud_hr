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
@Table(name="notification_detail")
public class NotificationDetails {
	
	@Id
	@GenericGenerator(name = "notification_id", strategy = "com.app.employeePortal.notification.generator.NotificationDetailsGenerator")
	@GeneratedValue(generator = "notification_id")
	
	@Column(name="notification_id")
	private String notificationId;
	
	@Column(name="notification_date")
	private Date notificationDate;
	
	
	@Column(name="message")
	private String message;
	
	/*	@Column(name="recipient_id")
	private String recipient_id;
	
	@Column(name="message_heading")
	private String message_heading;

	@Column(name="creator_id")
	private String creator_id;
	
	@Column(name="live_ind")
	private boolean live_ind;
	
	@Column(name="push_notification_ind")
	private boolean push_notification_ind;
	
	@Column(name="employee_id")
	private String employee_id;
	*/
	@Column(name="message_read_ind")
	private boolean messageReadInd;
	
	@Column(name="notification_type")
	private String notificationType;
	
	@Column(name="name")
	private String name;
	
	@Column(name = "user_id")
	private String user_id;
	
	@Column(name = "assigned_by")
	private String assignedBy;
	
	@Column(name = "assigned_to")
	private String assignedTo;
	
	@Column(name="org_id")
	private String org_id;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	

	

	
	
	
	
	
	
}
