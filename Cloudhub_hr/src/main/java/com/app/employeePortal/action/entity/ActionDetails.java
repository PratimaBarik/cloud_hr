package com.app.employeePortal.action.entity;

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
@Table(name = "action_details")
public class ActionDetails {

	@Id
	@GenericGenerator(name = "action_id", strategy = "com.app.employeePortal.action.generator.ActionDetailsGenerator")
	@GeneratedValue(generator = "action_id")

	@Column(name = "action_id")
	private String actionId;

	@Column(name = "action_name")
	private String actionName;

	@Column(name = "organization_id")
	private String organizationId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "creation_date")
	private Date creationDate;

}
