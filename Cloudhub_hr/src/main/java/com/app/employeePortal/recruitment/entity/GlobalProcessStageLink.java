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
@Getter
@Setter
@Entity
@Table(name = "global_process_stage_link")
public class GlobalProcessStageLink {
	
	@Id
	@GenericGenerator(name = "global_process_stage_link_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentProcessStageLinkGenerator")
	@GeneratedValue(generator = "global_process_stage_link_id")
	
	@Column(name = "global_process_stage_link_id")
	private String global_process_stage_link_id;
	
	@Column(name = "recruitment_stage_id")
	private String recruitmentStageId;
	
	@Column(name = "recruitment_process_id")
	private String recruitmentProcessId;
	
	
	@Column(name="creation_date")
	private Date creation_date;
	
	
	@Column(name="live_ind")
	private boolean liveInd;
	

}
