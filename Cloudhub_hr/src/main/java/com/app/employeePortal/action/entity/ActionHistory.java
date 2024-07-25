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
@Table(name = "action_history")
public class ActionHistory {
	
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.action.generator.ActionHistoryGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "action_history_id")
	private String id;
	
	@Column(name = "action_id")
	private String actionId;

	@Column(name = "opportunity_id")
	private String opportunityId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "creation_date")
	private Date creationDate;

}
