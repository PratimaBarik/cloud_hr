package com.app.employeePortal.permission.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="assessment_details")

public class AssessmentDetails {
	
	@Id
	@GenericGenerator(name = "assessment_details_id", strategy = "com.app.employeePortal.permission.generator.AssessmentDetailsGenerator")
	@GeneratedValue(generator = "assessment_details_id")
	
	@Column(name="assessment_details_id")
	private String id;
	
	@Column(name="assessment_Ind")
	private boolean assessmentInd;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="last_updated_on")
	private Date lastUpdatedOn;
	
	@Column(name="user_id")
	private String userId;

}
