package com.app.employeePortal.unboardingWorkflow.entity;

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
@Table(name="supplier_unboarding_stages")
public class SupplierUnboardingStages {

	@Id
	@GenericGenerator(name = "supplier_unboarding_stages_id", strategy = "com.app.employeePortal.unboardingWorkflow.generator.SupplierUnboardingStagesGenerator")
    @GeneratedValue(generator = "supplier_unboarding_stages_id")
	
	@Column(name="supplier_unboarding_stages_id")
	private String supplierUnboardingStagesId;
	
	@Column(name="stage_name")
	private String stageName;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "probability")
	private double probability;
	
	@Column(name = "days")
	private int days;
	
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name = "publish_ind")
	private boolean publishInd;
	
//	@Column(name="responsible")
//	private String responsible;
	
	@Column(name="supplierUnboardingWorkflowDetailsId")
	private String supplierUnboardingWorkflowDetailsId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "updationDate")
	private Date updationDate;
	
	@Column(name = "updatedBy")
	private String updatedBy;
}
