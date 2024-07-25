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
@Table(name="pdf_myview_selected")
public class PdfMyViewSelected {

	@Id
	@GenericGenerator(name = "pdf_myview_selected_id", strategy = "com.app.employeePortal.reports.generator.PdfMyViewSelectedGenerator")
	@GeneratedValue(generator = "pdf_myview_selected_id")
	
	@Column(name="pdf_myview_selected_id")
	private String pdfMyViewSelectedId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="selected_ind",nullable=false)
	private boolean selectedInd=false;
	
	@Column(name="requirement_ind",nullable=false)
	private boolean requirementInd=false;
	
	@Column(name="job_id_ind",nullable=false)
	private boolean jobIDInd=false;
	
	@Column(name="created_ind",nullable=false)
	private boolean createdInd=false;
	
	@Column(name="start_date_ind",nullable=false)
	private boolean startDateInd=false;
	
	@Column(name="sponsor_ind",nullable=false)
	private boolean sponsorInd=false;
	
	@Column(name="skill_set_ind",nullable=false)
	private boolean skillSetInd=false;
	
	@Column(name="submitted_ind",nullable=false)
	private boolean submittedInd=false;
	
	@Column(name="onboarded_ind",nullable=false)
	private boolean onboardedInd=false;
}
