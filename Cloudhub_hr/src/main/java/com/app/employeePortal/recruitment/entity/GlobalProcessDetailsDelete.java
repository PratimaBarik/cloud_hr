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
@Table(name="global_process_detl_del")
public class GlobalProcessDetailsDelete {
	
	@Id
	@GenericGenerator(name = "global_process_detl_del_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentProcessDetailsDeleteGenerator")
	@GeneratedValue(generator = "global_process_detl_del_id")
	
	@Column(name="global_process_detl_del_id")
	private String global_process_detl_del_id;
	
	@Column(name="process_id")
	private String processId;
	
//	@Column(name="global_process_detail_id")
	//private String global_process_detail_id;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="orgId")
	private String orgId;
	
	@Column(name="userId")
	private String userId;
}
