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
@Setter
@Getter
@Table(name = "advance_recruitment_rule_link")
public class AdvanceRecruitmentRule {
	
	@Id
	@GenericGenerator(name = "advance_recruitment_rule_id", strategy = "com.app.employeePortal.recruitment.generator.AdvanceRecruitmentRuleGenerator")
	@GeneratedValue(generator = "advance_recruitment_rule_id")
	
	@Column(name = "advance_recruitment_rule_id")
	private String advance_recruitment_rule_id;
	
	@Column(name="user_id")
	private String user_id;

	@Column(name="organization_id")
	private String organization_id;
	
	@Column(name="billing")
	private double billing;
	
	@Column(name="available_date")
	private double available_date;
	
	
	@Column(name="creation_date")
	private Date creation_date;

	@Column(name="live_ind")
	private boolean live_ind;

	
}
