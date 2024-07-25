package com.app.employeePortal.recruitment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Entity
@ToString
@Table(name = "recruitment_profile_details")
public class RecruitProfileLinkDetails {
	
	@Id
	@GenericGenerator(name = "recruitment_profile_details_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitProfileLinkGeneretor")
	@GeneratedValue(generator = "recruitment_profile_details_id")
	
	@Column(name = "recruitment_profile_details_id")
	private String recruitment_profile_details_id;
	
	@Column(name = "profile_id")
	private String profile_id;
	
	@Column(name = "recruitment_id")
	private String recruitment_id;
	
	@Column(name = "process_id")
	private String process_id;
	
	@Column(name = "opp_id")
	private String opp_id;
	
	@Column(name = "stage_id")
	private String stage_id;
	
	@Column(name = "creation_date")
	private Date creation_date;
	
	@Column(name = "stage_creation_date")
	private Date stage_creation_date;
	
	
	@Column(name = "modification_date")
	private Date modification_date;
	
	@Column(name = "rejected_date")
	private Date rejected_date;
	
	@Column(name = "offer_date")
	private Date offer_date;
	
	
	@Column(name="approve_ind")
	private boolean approve_ind;
	
	@Column(name="reject_ind")
	private boolean reject_ind;
	
	@Column(name="stage_ind")
	private boolean stage_ind;
	
	@Column(name="live_ind")
	private boolean live_ind;

	@Column(name="open_ind")
	private boolean openInd;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "recruit_owner")
	private String recruitOwner;
	
	@Column(name="onboard_ind",  nullable = false)
	private boolean onboard_ind = false;
	
	@Column(name = "onboard_date")
	private Date onboard_date;
	
	@Column(name="email_ind",  nullable = false)
	private boolean emailInd = false;
	
	@Column(name="intrest_ind",  nullable = false)
	private boolean intrestInd = false;
	
	@Column(name = "actual_end_date")
	private Date actualEndDate;
	
	@Column(name = "final_billing")
	private float finalBilling;
	
	@Column(name = "billable_hour")
	private float billableHour;
	
	@Column(name = "onboard_currency")
	private String onboardCurrency;
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "orgId")
	private String orgId;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "candidateId")
	private String candidateId;
	
	@Column(name = "customer_id")
	private String customerId;

	public RecruitProfileLinkDetails(String candidateId) {
		super();
		this.candidateId = candidateId;
	}

	public RecruitProfileLinkDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
