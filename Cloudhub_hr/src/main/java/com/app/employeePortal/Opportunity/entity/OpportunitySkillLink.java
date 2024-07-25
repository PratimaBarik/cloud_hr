package com.app.employeePortal.Opportunity.entity;

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
@Table(name="opportunity_skill_link")
public class OpportunitySkillLink {
    @Id
    @GenericGenerator(name = "opportunity_skill_link_id", strategy = "com.app.employeePortal.Opportunity.generator.OpportunitySkillLinkGenerator")
    @GeneratedValue(generator = "opportunity_skill_link_id")
    
    @Column(name="opportunity_skill_link_id")
    private String opportunitySkillLinkId;
    
    @Column(name="opportunity_id")
    private String opportunityId;

    @Column(name="skill")
    private String skill;

    @Column(name="no_of_position")
    private String noOfPosition;
    
    @Column(name="opp_innitiative")
    private String oppInnitiative;
    
    @Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
}
