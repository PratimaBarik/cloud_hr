package com.app.employeePortal.employee.entity;

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
@Table(name="training_details")
public class TrainingDetails {
	@Id
	@GenericGenerator(name = "training_details_id", strategy = "com.app.employeePortal.employee.generator.TrainingDetailsGenerator")
	@GeneratedValue(generator = "training_details_id")
	
	@Column(name="training_details_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
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
	
	
	  @Column(name="document_type_id")
	  private String document_type_id;
	 
	
	@Column(name="document_type_name")
	private String document_type_name;


	
	
	
}
