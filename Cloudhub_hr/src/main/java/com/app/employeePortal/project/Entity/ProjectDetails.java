package com.app.employeePortal.project.Entity;

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
@Table(name = "project_details")
public class ProjectDetails {

	@Id
	@GenericGenerator(name = "project_id", strategy = "com.app.employeePortal.project.generator.ProjectGenerator")
	@GeneratedValue(generator = "project_id")

	@Column(name = "project_id")
	private String projectId;

	@Column(name = "project_name")
	private String projectName;

	@Column(name="live_ind",nullable = false)
	private boolean liveInd=false;
	
	@Column(name="edit_ind",nullable = false)
	private boolean editInd=false;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "customer_id")
	private String customerId;

	
}
