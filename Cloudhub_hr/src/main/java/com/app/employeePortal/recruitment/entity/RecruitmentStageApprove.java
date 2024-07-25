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
@Table(name = "recruitment_stage_approve")
public class RecruitmentStageApprove {
	
	@Id
	@GenericGenerator(name = "recruitment_stage_approve_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentStageApproveGenerator")
	@GeneratedValue(generator = "recruitment_stage_approve_id")
	
	@Column(name = "recruitment_stage_approve_id")
	private String recruitmentStageApproveId;
	
	@Column(name="level1")
	private String level1;
	
	@Column(name="level2")
	private String level2;

	@Column(name="threshold")
	private String threshold;
	
	@Column(name="approval_type")
	private String approvalType;
	
	@Column(name="stage_id")
	private String stageId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="approval_indicator")
	private boolean approvalIndicator;
	
	@Column(name="creation_date")
	private Date CreationDate;
	
	@Column(name="function_id")
	private String functionId;
	
	@Column(name="designation_id")
	private String designationId;
	
	@Column(name="job_level")
	private int jobLevel;

	@Column(name="user_id")
	private String userId;
	
	@Column(name="organization_id")
	private String organizationId;
	
	@Column(name="process_id")
	private String processId;
}
