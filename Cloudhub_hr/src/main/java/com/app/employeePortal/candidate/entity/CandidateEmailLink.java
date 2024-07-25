package com.app.employeePortal.candidate.entity;

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
@Table(name = "candidate_email_link")
public class CandidateEmailLink {

	@Id
	@GenericGenerator(name = "candidate_email_Link_id", strategy = "com.app.employeePortal.candidate.generator.CandidateEmailLinkGenerator")
    @GeneratedValue(generator = "candidate_email_Link_id")
	
	@Column(name="candidate_email_Link_id")
	private String candidateEmailLinkId;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="candidate_email_details_id")
	private String candidateEmailsDetailsId;
	
}
