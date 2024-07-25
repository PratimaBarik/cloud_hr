package com.app.employeePortal.template.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="noti_templete_details")
public class NotificationTemplateDetails {
	
	@Id
	@GenericGenerator(name = "notification_templete_id", strategy = "com.app.employeePortal.template.generator.NotificationTemplateGenerator")
	@GeneratedValue(generator = "notification_templete_id")

	@Column(name="notification_templete_id")
	private String notificationTempleteId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="organization_id")
	private String organizationId;
		
	@Lob
	@Column(name="template")
	private String template;

	
	@Column(name="type")
	private String type;
	
	@Lob
	@Column(name="description")
	private String description;
	
//	@Column(name="subject")
//	private String subject;
	
	@Column(name = "message")
    private String message;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	
	@Column(name="liveInd")
	private boolean liveInd;


	

}
