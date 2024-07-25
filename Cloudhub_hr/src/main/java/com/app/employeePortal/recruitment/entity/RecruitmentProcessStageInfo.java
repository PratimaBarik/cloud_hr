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
@Table(name = "recruitment_process_stage_info")
public class RecruitmentProcessStageInfo {

	@Id
	@GenericGenerator(name = "recruitment_process_stage_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentStageInfoGenerator")
	@GeneratedValue(generator = "recruitment_process_stage_id")
	
	@Column(name = "recruitment_process_stage_id")
	private String recruitment_process_stage_id;
	
	@Column(name = "creation_date")
	private Date creationDate;
	

}
