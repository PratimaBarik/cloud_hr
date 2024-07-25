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
@Table(name="global_process_info")
public class GlobalProcessInfo {
	
	@Id
	@GenericGenerator(name = "global_process_info_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitProcessInfoGenerator")
	@GeneratedValue(generator = "global_process_info_id")
	
	@Column(name="global_process_info_id")
	private String global_process_info_id;
	
	@Column(name="creation_date")
	private Date creation_date;

}
