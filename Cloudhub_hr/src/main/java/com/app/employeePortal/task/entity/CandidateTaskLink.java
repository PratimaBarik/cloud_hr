package com.app.employeePortal.task.entity;

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
@Table(name="task_candidate_link")
public class CandidateTaskLink {
	
	@Id
	@GenericGenerator(name = "task_candidate_link_id", strategy = "com.app.employeePortal.task.generator.CandidateTaskLinkGenerator")
	@GeneratedValue(generator = "task_candidate_link_id")
	
	@Column(name="task_candidate_link_id")
	private String taskCandidateLinkId;
	
	
	@Column(name="task_id")
	private String taskId;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="creation_date")
	private Date creationdate;
	
	@Column(name="live_ind")
	private boolean liveInd;

	@Column(name="complition_status")
	private String complitionStatus;

}
