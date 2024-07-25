package com.app.employeePortal.employee.entity;

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
@Table(name="employee_id_details")
public class EmployeeIDDetails {

	@Id
	@GenericGenerator(name = "employee_id_details_id", strategy = "com.app.employeePortal.employee.generator.EmployeeIdDetailsGenerator")
	@GeneratedValue(generator = "employee_id_details_id")
	
	
	@Column(name="employee_id_details_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="id_type")
	private String idType;
	
	@Column(name="id_no")
	private String idNo;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name = "document_id")
	private String documentId;
	
	@Column(name = "document_name")
	private String documentName;
	
	@Lob
	@Column(name = "description")
	private String description;

	@Column(name="document_type")
	private String documentType;

}
