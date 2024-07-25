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
@Table(name = "report_scheduling_delete")
public class ReportSchedulingDelete {

	@Id
	@GenericGenerator(name = "report_scheduling_id", strategy = "com.app.employeePortal.ruleEngine.generator.ReportSchedulingDeleteGenerator")
    @GeneratedValue(generator = "report_scheduling_id")
	
	@Column(name="report_scheduling_delete_id")
	private String reportSchedulingDeleteId;

	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="report_scheduling_id")
	private String reportSchedulingId;
}
