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
@Getter
@Setter
@Table(name="recruitment_opportunity_contact_link")
public class RecruitmentOpportunityContactLink {
	
	@Id
	@GenericGenerator(name = "recruitment_opportunity_contact_link_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentOpportunityLinkGenerator")
	@GeneratedValue(generator = "recruitment_opportunity_contact_link_id")
	
	@Column(name = "recruitment_opportunity_contact_link_id")
	private String recruitment_opportunity_contact_link_id;
	
	@Column(name="opportunity_id")
	private String opportunity_id;
	
	@Column(name="contact_id")
	private String contact_id;
	
	@Column(name="profile_id")
	private String profile_id;
	
	
	@Column(name="recruitment_process_id")
	private String recruitment_process_id;
	
	@Column(name="stage_id")
	private String stage_id;
	
	@Column(name="recruitment_id")
	private String recruitment_id;
	
	
	@Column(name="creation_date")
	private Date creation_date;
	
		
	@Column(name="live_ind")
	private boolean live_ind;


	
	
	


}
