package com.app.employeePortal.ruleEngine.entity;

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
@Table(name="recriut_mail_rule_link")
public class RecruitProMailRuleLink {
	
	@Id
	@GenericGenerator(name = "recriut_mail_rule_id", strategy = "com.app.employeePortal.ruleEngine.generator.RecruitProMailRuleLinkGenerator")
	@GeneratedValue(generator="recriut_mail_rule_id")
	
	@Column(name="recriut_mail_rule_id")
	private String recriut_mail_rule_id;
	
	@Column(name="attachment_ind")
	private boolean attachment_ind;
	
	@Column(name="approve_ind")
	private boolean approve_ind;
	
	@Column(name="org_id")
	private String org_id;
	
	@Column(name="template")
	private String template;
	
	@Column(name="receipent")
	private String receipent;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;
	
	@Column(name="toggle_ind")
	private boolean toggle_ind;
	
	@Column(name="no_of_time")
	private long no_of_time;
	
	@Column(name="frequency")
	private String frequency;
	
	@Column(name="reminder_ind")
	private boolean reminder_ind;

	


}
