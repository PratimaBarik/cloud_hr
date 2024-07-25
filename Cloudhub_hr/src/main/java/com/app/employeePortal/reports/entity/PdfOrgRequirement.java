package com.app.employeePortal.reports.entity;

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
@Table(name="pdf_org_requirement")
public class PdfOrgRequirement {

	@Id
	@GenericGenerator(name = "pdf_org_requirement_id", strategy = "com.app.employeePortal.reports.generator.PdfOrgRequirementGenerator")
	@GeneratedValue(generator = "pdf_org_requirement_id")
	
	@Column(name="pdf_org_requirement_id")
	private String pdfOrgRequirementId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="job_id_ind",nullable=false)
	private boolean jobIDInd=false;
	
	@Column(name="requirement_ind",nullable=false)
	private boolean requirementInd=false;
	
	@Column(name="sponsor_ind",nullable=false)
	private boolean sponsorInd=false;
	
	@Column(name="created_ind",nullable=false)
	private boolean createdInd=false;
	
	@Column(name="start_date_ind",nullable=false)
	private boolean startDateInd=false;
	
	@Column(name="skill_set_ind",nullable=false)
	private boolean skillSetInd=false;
	
	@Column(name="	submitted_ind",nullable=false)
	private boolean submittedInd=false;
	
	@Column(name="onboarded_ind",nullable=false)
	private boolean onboardedInd=false;
	
	@Column(name="selected_ind",nullable=false)
	private boolean selectedInd=false;
}
