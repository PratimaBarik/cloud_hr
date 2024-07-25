package com.app.employeePortal.call.entity;

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
@Table(name="call_candidate_link")
public class CallCandidateLink {

	@Id
	@GenericGenerator(name = "call_candidate_link_id", strategy = "com.app.employeePortal.call.generator.CallCandidateLinkGenerator")
	@GeneratedValue(generator = "call_candidate_link_id")
	
	@Column(name="call_candidate_link_id")
	private String id;
	
	
	@Column(name="call_id")
	private String callId;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;


}
