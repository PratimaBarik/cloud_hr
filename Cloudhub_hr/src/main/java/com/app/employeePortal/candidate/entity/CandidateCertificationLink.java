package com.app.employeePortal.candidate.entity;

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
@Table(name = "candidate_certification_link")

public class CandidateCertificationLink {

	@Id
	@GenericGenerator(name = "candidate_certification_link_id", strategy = "com.app.employeePortal.candidate.generator.CandidateCertificationLinkGenerator")
	@GeneratedValue(generator = "candidate_certification_link_id")

	@Column(name = "candidate_certification_link_id")
	private String candidateCertificationLinkId;

	@Column(name = "candidate_certification_name")
	private String candidateCertificationName;

	@Column(name = "candidate_id")
	private String candidateId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "edit_ind")
	private boolean editInd;
	
	@Column(name="org_id")
	private String orgId;

	@Column(name="user_id")
	private String userId;

}
