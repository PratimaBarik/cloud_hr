package com.app.employeePortal.ruleEngine.entity;

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
@Table(name = "report_scheduling")

public class ReportScheduling {
	
	@Id
	@GenericGenerator(name = "report_scheduling_id", strategy = "com.app.employeePortal.ruleEngine.generator.ReportSchedulingGenerator")
    @GeneratedValue(generator = "report_scheduling_id")
	
	@Column(name="report_scheduling_id")
	private String reportSchedulingId;

	@Column(name="org_id")
	private String orgId;
	
	@Column(name="type")
	private String type;
	
	@Column(name="department")
	private String department;
	
	@Column(name="frequency")
	private String frequency;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="user_id")
	private String userId;
}
