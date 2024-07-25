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
@Table(name="email_templete_details")
public class EmailTemplateDetails {
	
	@Id
	@GenericGenerator(name = "templete_id", strategy = "com.app.employeePortal.template.generator.TemplateGenerator")
	@GeneratedValue(generator = "templete_id")

	@Column(name="templete_id")
	private String templete_id;
	
	@Column(name="user_id")
	private String user_id;
	
	@Column(name="organization_id")
	private String organization_id;
	
	
	@Lob
	@Column(name="template")
	private String template;

	
	@Column(name="type")
	private String type;
	
	@Lob
	@Column(name="description")
	private String description;
	
	@Column(name="subject")
	private String subject;
	
	
	@Column(name="creation_date")
	private Date creation_date;
	
	
	@Column(name="live_ind")
	private boolean live_ind;

	@Column(name="customer_id")
	private String customerId;


	

}
