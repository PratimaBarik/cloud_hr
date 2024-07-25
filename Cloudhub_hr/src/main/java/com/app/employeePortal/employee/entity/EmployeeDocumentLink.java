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
@Table(name = "employee_document_link")
public class EmployeeDocumentLink {

	
	@Id
	@GenericGenerator(name = "employee_document_link_id", strategy = "com.app.employeePortal.employee.generator.EmployeeDocumentGenerator")
    @GeneratedValue(generator = "employee_document_link_id")
	
	@Column(name="employee_document_link_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="document_id")
	private String documentId;
	
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
	
	@Column(name="shared_user")
	private String sharedUser;

	@Column(name="contract_Ind", nullable = false)
	private boolean contractInd =false;
}
