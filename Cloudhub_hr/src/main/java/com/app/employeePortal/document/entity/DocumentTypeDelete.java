package com.app.employeePortal.document.entity;

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
@Table(name = "document_type_del")
public class DocumentTypeDelete {

	@Id
	@GenericGenerator(name = "document_type_del_id", strategy = "com.app.employeePortal.document.generator.DocumentTypeDeleteGenerator")
	@GeneratedValue(generator = "document_type_del_id")

	@Column(name="document_type_del_id")
	private String documentTypeDelId;
	
	@Column(name = "document_type_id")
	private String document_type_id;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name = "updated_by")
	private String updatedBy;
}
