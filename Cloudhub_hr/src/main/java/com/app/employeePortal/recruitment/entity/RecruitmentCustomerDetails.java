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

@Entity
@Getter
@Setter
@Table(name="recruitment_customer_details")
public class RecruitmentCustomerDetails {

	@Id
	@GenericGenerator(name = "recruitment_customer_details_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentCustomerGenerator")
	@GeneratedValue(generator = "recruitment_customer_details_id")
	
	@Column(name = "recruitment_customer_details_id")
	private String recruitmentOpportunityDetailsId;
	
	@Column(name = "recruitment_id")
	private String recruitmentId;	
	
	@Column(name = "customer_id")
	private String customerId;

	
	@Column(name = "recruitment_process_id")
	private String recruitmentProcessId;
	
	@Column(name = "recruitment_stage_id")
	private String recruitmentStageId;
	
	@Column(name = "sponser_id")
	private String sponserId;
	
	@Column(name = "candidate_id")
	private String candidateId;
	
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
	private Date stageCreationDate;
	
	@Column(name="modification_date")
	private Date modificationDate;

	@Column(name="available_date")
	private Date availableDate;
	
	@Column(name = "billing")
	private double billing;
	
	@Column(name="live_ind")
	private boolean liveInd;	
		
	@Column(name = "currency")
	private String currency;

}
