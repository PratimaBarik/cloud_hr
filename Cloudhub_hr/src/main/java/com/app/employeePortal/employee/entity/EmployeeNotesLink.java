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
@Table(name="employee_notes_link")
public class EmployeeNotesLink {
	
	@Id
	@GenericGenerator(name = "employee_notes_link_id", strategy = "com.app.employeePortal.employee.generator.EmployeeNotesLinkGenerator")
	@GeneratedValue(generator = "employee_notes_link_id")
	
	@Column(name="employee_notes_link_id")
	private String id;
	
	@Column(name="notes_id")
	private String notesId;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind",nullable =false)
	private boolean liveInd = true;



}
