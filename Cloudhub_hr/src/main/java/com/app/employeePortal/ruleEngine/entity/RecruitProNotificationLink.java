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
@Table(name="recriut_notification_rule_link")
public class RecruitProNotificationLink {
	@Id
	@GenericGenerator(name = "recriut_notification_rule_id", strategy = "com.app.employeePortal.ruleEngine.generator.RecruitProNotificationLinkGenerator")
	@GeneratedValue(generator="recriut_notification_rule_id")
	
	@Column(name="invoice_notification_rule_id")
	private String invoice_notification_rule_id;
	
	
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

	
	

}
