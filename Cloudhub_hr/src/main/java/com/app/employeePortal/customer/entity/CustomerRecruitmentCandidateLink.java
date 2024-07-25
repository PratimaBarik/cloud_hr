package com.app.employeePortal.customer.entity;

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
@Table(name="customer_recruitment_candidate")
public class CustomerRecruitmentCandidateLink {
	
	@Id
	@GenericGenerator(name = "customer_recruitment_candidate_id", strategy ="com.app.employeePortal.customer.generator.CustomerRecruitmentCandidateLinkGenerator")	
	@GeneratedValue(generator = "customer_recruitment_candidate_id")
	
	@Column(name = "customer_recruitment_candidate_id")
	private String id;	
	
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "recruitment_id")
	private String recruitmentId;
	
	@Column(name = "profile_id")
	private String profileId;
	
	@Column(name = "candidate_id")
	private String candidateId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

}
