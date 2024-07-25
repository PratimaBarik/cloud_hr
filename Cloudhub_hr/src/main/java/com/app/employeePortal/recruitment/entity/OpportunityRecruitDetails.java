package com.app.employeePortal.recruitment.entity;

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
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="opportunity_recruit_details")
public class OpportunityRecruitDetails {
	
	@Id
	@GenericGenerator(name = "opportunity_recruit_details_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentOpportunityGenerator")
	@GeneratedValue(generator = "opportunity_recruit_details_id")
	
	@Column(name = "opportunity_recruit_details_id")
	private String opportunity_recruit_details_id;
	
	@Column(name = "recruitment_id")
	private String recruitment_id;	
	
	@Column(name = "opportunity_id")
	private String opportunity_id;

	
	@Column(name = "recruitment_process_id")
	private String recruitment_process_id;
	
	@Column(name = "recruitment_stage_id")
	private String recruitment_stage_id;
	
	@Column(name = "sponser_id")
	private String sponser_id;
	
	@Column(name = "candidate_id")
	private String candidateid;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Lob
	@Column(name = "description")
	private String description;
	
	@Column(name = "number")
	private long number;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="stage_creation_date")
	private Date stage_creation_date;
	
	@Column(name="modification_date")
	private Date modification_date;


	@Column(name="available_date")
	private Date available_date;
	
	@Column(name="end_date")
	private Date end_date;
	
	@Column(name = "billing")
	private double billing;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "recruiter_id")
	private String recruiter_id;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
		
	@Column(name="open_ind")
	private boolean openInd;
	
	@Column(name = "job_order")
	private String job_order;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "experience")
	private String experience;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "partner_id")
	private String partner_id;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "work_Preferance")
	private String workPreferance;
	
	@Column(name="close_ind", nullable = false)
	private boolean closeInd =false;
	
	@Column(name="close_by_date")
	private Date closeByDate;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="work_type")
	private String workType;

	@Column(name="work_days")
	private String workDays;
	}
