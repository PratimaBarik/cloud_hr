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
@Table(name = "Recruitpro_Sponser_Email_Rule_Link")
public class RecruitproSponserEmailRuleLink {
	
	 @Id
	    @GenericGenerator(name = "recruitpro_sponser_email_link_id", strategy = "com.app.employeePortal.ruleEngine.generator.RecruitproSponserEmailRuleLinkGenerator")
	    @GeneratedValue(generator = "recruitpro_sponser_email_link_id")

	    @Column(name = "recruitpro_sponser_email_link_id")
	    private String recruitpro_sponser_email_link_id;


	    @Column(name = "receiver")
	    private String receiver;

	    @Column(name = "template_id")
	    private String template_id;

	    @Column(name = "attachment_ind")
	    private boolean attachment_ind;

	    @Column(name = "approval_ind")
	    private boolean approval_ind;

	    @Column(name = "org_id")
	    private String org_id;

	    @Column(name = "user_id")
	    private String user_id;

	    @Column(name = "creation_date")
	    private Date creation_date;

	    @Column(name = "live_ind")
	    private boolean live_ind;

	    @Column(name = "toggle_ind")
	    private boolean toggle_ind;

	    @Column(name = "no_of_time")
	    private long no_of_time;

	    @Column(name = "frequency")
	    private String frequency;

	    @Column(name = "reminder_ind")
	    private boolean reminder_ind;

	   
	}



