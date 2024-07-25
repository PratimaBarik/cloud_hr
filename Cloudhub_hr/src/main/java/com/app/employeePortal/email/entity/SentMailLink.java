package com.app.employeePortal.email.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="sent_mail_link")
public class SentMailLink {
	
	@Id
	@GenericGenerator(name ="id", strategy = "com.app.employeePortal.email.generator.SentMailLinkGenerator")
	@GeneratedValue(generator ="id")
	
	@Column(name="sent_mail_link_id")
	private String id;
	
	@Column(name="email_name")
	private String emailName;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;


}
