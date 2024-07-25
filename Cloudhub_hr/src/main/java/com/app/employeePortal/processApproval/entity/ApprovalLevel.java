package com.app.employeePortal.processApproval.entity;

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

@ToString
@Entity
@Getter
@Setter
@Table(name = "approval_level")
public class ApprovalLevel {
	
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.processApproval.generator.ApprovalLevelGenerator")
    @GeneratedValue(generator = "id")

	@Column(name="approval_level_id")
	private String id;
	
	@Column(name="reporting_to")
	private String reportingTo;
	
	@Column(name="reporting_to2")
	private String reportingTo2;
	
	@Column(name="reporting_to3")
	private String reportingTo3;
	
	@Column(name="reporting_to4")
	private String reportingTo4;
	
	@Column(name="reporting_to5")
	private String reportingTo5;
	
	@Column(name="role_type")
	private String roleType;
	
	@Column(name="role_type2")
	private String roleType2;
	
	@Column(name="role_type3")
	private String roleType3;
	
	@Column(name="level_count")
	private int levelCount;
	
	@Column(name="threshold1")
	private float threshold1;
	 
	@Column(name="threshold2")
	 private float threshold2;
	 
	@Column(name="threshold3")
	 private float threshold3;
	 
	@Column(name="threshold4")
	 private float threshold4;
	 
	@Column(name="threshold5")
	 private float threshold5;
	 
	@Column(name="sub_process_approval_id")
	private String subProcessApprovalId;
	
	@Column(name="approval_type")
	private String approvalType;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="approval_indicator")
	private boolean approvalIndicator;
	
	@Column(name="creation_date")
	private Date CreationDate;
	
	@Column(name="department_id")
	private String departmentId;
	
	@Column(name="designation_id")
	private String designationId;
	
	@Column(name="job_level")
	private int jobLevel;
}
