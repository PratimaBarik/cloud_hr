package com.app.employeePortal.employee.entity;

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
@Table(name="visa")
public class Visa {
	@Id
	@GenericGenerator(name = "visa_id", strategy = "com.app.employeePortal.employee.generator.VisaGenerator")
	@GeneratedValue(generator = "visa_id")
	
	@Column(name="visa_id")
	private String visaId;
	
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="country")
	private String country;
	
	@Column(name="document_id")
	private String documentId;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="multiple_entry_ind",nullable=false)
	private boolean multipleEntryInd=false;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="type")
	private String type;

	@Column(name="user_id")
	private String userId;

	@Column(name="document_type")
	private String documentType;
	

}
