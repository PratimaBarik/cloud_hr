package com.app.employeePortal.organization.entity;

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
@Table(name="organization_document_link")
public class OrganizationDocumentLink {
	
	@Id
	@GenericGenerator(name = "organization_document_link_id", strategy = "com.app.employeePortal.organization.generator.OrganizationDocumentLinkGenerator")
	@GeneratedValue(generator = "organization_document_link_id")
	
	
	@Column(name="organization_document_link_id")
	private String organizationDocumentLinkId;
	
	@Column(name="catagory")
	private String catagory;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="department")
	private String department;
	
	@Lob
	@Column(name="description")
	private String description;
	
	@Column(name="document_id")
	private String documentId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="name")
	private String name;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="share_ind")
	private boolean shareInd;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="document_type")
	private String documentType;
	
//	@Column(name = "publish_Ind", nullable = false)
//	private boolean publishInd=false;
//	
//	@Column(name = "public_Ind", nullable = false)
//	private boolean publicInd=false;
}
