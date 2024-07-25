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
@Table(name = "candidate_info")
public class CandidateInfo {
	
	@Id
	@GenericGenerator(name = "candidate_id", strategy = "com.app.employeePortal.candidate.generator.CandidateInfoGenerator")
    @GeneratedValue(generator = "candidate_id")
	
	@Column(name="candidate_id")
	private String candidateId;
	
	
	@Column(name="creator_id")
	private String creatorId;
	
	@Column(name="org_id")
	private String org_id;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	
	@Column(name = "available_date")
	private Date availableDate;

	

	

	

	

}
