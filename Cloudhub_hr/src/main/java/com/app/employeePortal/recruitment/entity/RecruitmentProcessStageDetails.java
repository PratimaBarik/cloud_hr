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

@Entity
@Getter
@Setter
@Table(name = "recruitment_process_stage_details")
public class RecruitmentProcessStageDetails {
	@Id
	@GenericGenerator(name = "recruitment_stage_details_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentStageDetailsGenerator")
	@GeneratedValue(generator = "recruitment_stage_details_id")
	
	@Column(name = "recruitment_stage_details_id")
	private String recruitment_stage_details_id;
	
	@Column(name = "stage_name")
	private String stage_name;
	
	@Column(name = "org_id")
	private String orgId;
	
	@Column(name = "recruitment_stage_id")
	private String recruitmentStageId;
	
	@Column(name="probability")
	private double probability;
	
	@Column(name="days")
	private int days;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="publish_ind")
	private boolean publishInd;
	
	@Column(name = "responsible")
	private String responsible;
	
	@Column(name="userId")
	private String userId;
	
}