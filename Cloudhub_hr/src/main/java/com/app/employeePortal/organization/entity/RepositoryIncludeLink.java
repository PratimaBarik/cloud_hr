package com.app.employeePortal.organization.entity;

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
@Table(name="repository_include_link")
public class RepositoryIncludeLink {
	
	@Id
	@GenericGenerator(name = "repository_include_link_id", strategy = "com.app.employeePortal.organization.generator.RepositoryIncludeLinkGenerator")
	@GeneratedValue(generator = "repository_include_link_id")
	
	@Column(name="repository_include_link_id")
	private String repositoryIncludeLinkId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="organization_document_link_id")
	private String organizationDocumentLinkId;
	
}
