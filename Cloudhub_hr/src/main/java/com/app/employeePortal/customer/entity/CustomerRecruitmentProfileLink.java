package com.app.employeePortal.customer.entity;

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
@Table(name="customer_recruitment_profile_link")
public class CustomerRecruitmentProfileLink {
	
	@Id
	@GenericGenerator(name = "customer_recruitment_profile_link_id", strategy ="com.app.employeePortal.customer.generator.CustomerRecruitmentProfileLinkGenerator")	
	@GeneratedValue(generator = "customer_recruitment_profile_link_id")
	
	@Column(name = "customer_recruitment_profile_link_id")
	private String id;	
	
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "profile_id")
	private String profileId;
	
	@Column(name = "recruitment_id")
	private String recruitmentId;
	
	@Column(name = "process_id")
	private String processId;
	
	@Column(name = "stage_id")
	private String stageId;
	
	@Column(name = "available_date")
	private Date availableDate;
	
	@Column(name="stage_creation_date")
	private Date stageCreationDate;
	
	@Column(name = "billing")
	private double billing;
	
	@Column(name = "currency")
	private String currency;

	@Column(name="stage_modification_date")
	private Date stageModificationDate;
	
	@Column(name = "skill")
	private String skill;
	
	@Column(name = "positions")
	private long positions;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

}
