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
@Table(name="candidate_education_details")
public class CandidateEducationDetails {
	@Id
	@GenericGenerator(name = "education_details_id", strategy = "com.app.employeePortal.candidate.generator.CandidateEducationDetailsGenerator")
	@GeneratedValue(generator = "education_details_id")
	
	
	@Column(name="education_details_id")
	private String id;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="education_type")
	private String educationType;
	
	@Column(name="course_name")
	private String courseName;
	
	@Column(name="course_type")
	private String courseType;
	
	@Column(name="specialization")
	private String specialization;
	
	@Column(name="university")
	private String university;
	
	@Column(name="year_of_passing")
	private int yearOfPassing;
	
	@Column(name="marks_secured")
	private double marksSecured;
	
	@Column(name="marks_type")
	private String marksType;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name = "document_id")
	private String documentId;

	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name = "user_id")
	private String userId;

	@Column(name="document_type_id")
	private String documentTypeId;
}
