package com.app.employeePortal.registration.entity;


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
@Table(name="user_session")
public class UserSession {
	

	@Id
	@GenericGenerator(name = "user_session_id", strategy = "com.app.employeePortal.registration.generator.UserSessionGenerator")
	@GeneratedValue(generator = "user_session_id")
	
	@Column(name="user_session_id")
	private String user_session_id;
	
	@Column(name="user_id")
	private String user_id;
	
	@Column(name="email_id")
	private String email_id;
	
	@Column(name="token_id")
	private String token_id;
	
	@Column(name="organization_id")
	private String organization_id;
	
	@Column(name="device_type")
	private String device_type;
	
	@Column(name="creation_date")
	private Date creationDate ;
	
	@Column(name="session_start_time")
	private long session_start_time;
	
	@Column(name="session_end_time")
	private long session_end_time;
	
	@Column(name="live_ind")
	private boolean live_ind;

	


	
}
