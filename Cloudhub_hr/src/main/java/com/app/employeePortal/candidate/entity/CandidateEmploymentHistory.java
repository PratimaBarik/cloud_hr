package com.app.employeePortal.candidate.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="candidate_employment_history")
public class CandidateEmploymentHistory {
	
	@Id
	@GenericGenerator(name = "cand_history_id", strategy = "com.app.employeePortal.candidate.generator.CandidateEmploymentHistoryGenerator")
	@GeneratedValue(generator = "cand_history_id")
	
	
	@Column(name="cand_history_id")
	private String id;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="tenure")
	private String tenure;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="salary")
	private double salary;
	

	@Column(name="salary_type")
	private String salaryType;
	
	@Lob
	@Column(name="description")
	private String description;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

	@Column(name = "document_id")
	private String documentId;
	
	@Column(name="document_type_id")
	private String documentTypeId;
	
}
