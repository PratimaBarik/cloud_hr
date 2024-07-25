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
@Setter
@Getter
@Entity
@Table(name = "opportunity_recruit_info")
public class OpportunityRecuitInfo {
	
	@Id
	@GenericGenerator(name = "opportunity_recruit_info", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentInfoGenerator")
	@GeneratedValue(generator = "opportunity_recruit_info")
	
	@Column(name = "recruitment_id")
	private String recruitment_id;	
	
	@Column(name="creation_date")
	private Date creation_date;
}
