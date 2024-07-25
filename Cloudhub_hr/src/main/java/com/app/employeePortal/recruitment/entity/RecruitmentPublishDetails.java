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
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="recruitment_publish_details")
public class RecruitmentPublishDetails {
	
	@Id
	@GenericGenerator(name = "recruitment_publish_details_id", strategy = "com.app.employeePortal.recruitment.generator.OpportunityRecruiterLinkGenerator")
	@GeneratedValue(generator = "recruitment_publish_details_id")
	
	@Column(name = "recruitment_publish_details_id")
	private String recruitment_publish_details_id;
	
	@Column(name = "recruiter_id")
	private String recruiter_id;
	
	@Column(name = "org_id")
	private String org_id;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="ping_ind", nullable =false)
	private boolean pingInd = false;

}
