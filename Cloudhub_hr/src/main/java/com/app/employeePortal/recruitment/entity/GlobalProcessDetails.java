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
@Table(name="global_process_detail")
public class GlobalProcessDetails {
	
	@Id
	@GenericGenerator(name = "global_process_detail_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentProcessDetailsGenerator")
	@GeneratedValue(generator = "global_process_detail_id")
	
	@Column(name="global_process_detail_id")
	private String global_process_detail_id;
	
	@Column(name="process_id")
	private String process_id;

	@Column(name="process_name")
	private String process_name;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="orgId")
	private String orgId;
	
	@Column(name="live_ind")
	private boolean live_ind;
	
	@Column(name="publish_ind")
	private boolean publishInd;
	
	@Column(name="userId")
	private String userId;

}
