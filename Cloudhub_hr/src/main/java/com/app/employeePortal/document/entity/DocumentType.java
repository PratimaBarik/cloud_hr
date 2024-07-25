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
@Table(name = "document_type")
public class DocumentType {

	@Id
	@GenericGenerator(name = "document_type_id", strategy = "com.app.employeePortal.document.generator.DocumentTypeGenerator")
	@GeneratedValue(generator = "document_type_id")

	@Column(name = "document_type_id")
	private String document_type_id;

	@Column(name = "document_type_name")
	private String documentTypeName;

	@Column(name = "creation_date")
	private Date creation_date;

	@Column(name = "creator_id")
	private String creator_id;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "live_ind")
	private boolean live_ind;

	@Column(name = "edit_ind")
	private boolean editInd;
	
	@Column(name="user_id")
	private String userId;

	@Column(name="user_type")
	private String userType;
	
	@Column(name="mandatory_ind")
	private boolean mandatoryInd;
}
