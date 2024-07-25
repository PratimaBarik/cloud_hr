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
@Table(name="candidate_training")
public class CandidateTraining {
	
	@Id
	@GenericGenerator(name = "candidate_training_id", strategy = "com.app.employeePortal.candidate.generator.CandidateTrainingGenerator")
	@GeneratedValue(generator = "candidate_training_id")
	
	@Column(name="candidate_training_id")
	private String candidateTrainingId;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="course_name")
	private String courseName;
	
	@Column(name="grade")
	private String grade;
	
	@Column(name="tenure")
	private String tenure;
	
	@Column(name="organization")
	private String organization;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name = "document_id")
	private String documentId;
	
	@Column(name="live_ind")
	private boolean liveInd;

	
	


}
