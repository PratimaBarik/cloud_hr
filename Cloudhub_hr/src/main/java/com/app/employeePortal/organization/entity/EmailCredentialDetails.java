package com.app.employeePortal.organization.entity;

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
@Table(name="email_credentials")
public class EmailCredentialDetails {

	@Id
	@GenericGenerator(name = "email_credentials_id", strategy = "com.app.employeePortal.organization.generator.EmailCredentialDetailsGenerator")
	@GeneratedValue(generator = "email_credentials_id")
	
	
	@Column(name="email_credentials_id")
	private String email_credentials_id;
	
	@Column(name="org_id")
	private String org_id;
	
	@Column(name="employee_id")
	private String employee_id;
	
	@Column(name="host")
	private String host;
	
	@Column(name="port")
	private int port;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="creation_date")
	private Date creation_date;

	@Column(name="default_ind")
	private boolean defaultInd;

	
	
	
}
