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
@Table(name = "recruitment_stage_rule_mail_link")
public class RecruitmentStageMailRuleLink {

	 @Id
	    @GenericGenerator(name = "recruitment_stage_rule_mail_link_id", strategy = "com.app.employeePortal.ruleEngine.generator.RecruitmentStageMailRuleLinkGenerator")
	    @GeneratedValue(generator = "recruitment_stage_rule_mail_link_id")

	    @Column(name = "recruitment_stage_rule_mail_link_id")
	    private String recruitmentStageRuleMailLinkId;

	    @Column(name = "stage_ind")
	    private boolean stageInd;

	    @Column(name = "template")
	    private String template;

	    @Column(name = "org_id")
	    private String orgId;

	    @Column(name = "creation_date")
	    private Date creationDate;

	    @Column(name = "live_ind")
	    private boolean liveInd;
}
