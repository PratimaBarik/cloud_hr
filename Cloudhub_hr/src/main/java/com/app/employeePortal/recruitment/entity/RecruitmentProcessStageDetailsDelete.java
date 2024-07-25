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
@Table(name = "recruitment_process_stage_delete")
public class RecruitmentProcessStageDetailsDelete {
	@Id
	@GenericGenerator(name = "recruitment_stg_detls_del_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentProcessStageDetailsDeleteGenerator")
	@GeneratedValue(generator = "recruitment_stg_detls_del_id")
	
	@Column(name = "recruitment_stg_detls_del_id")
	private String recruitment_stg_detls_del_id;
	
	//@Column(name = "recruitment_stage_details_id")
	//private String recruitment_stage_details_id;

	@Column(name = "recruitment_stage_id")
	private String recruitmentStageId;
	
	@Column(name = "org_id")
	private String orgId;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="userId")
	private String userId;
	
}