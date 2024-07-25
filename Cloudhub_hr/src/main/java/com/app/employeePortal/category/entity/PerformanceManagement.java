package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "performance_management")

public class PerformanceManagement {
	@Id
	@GenericGenerator(name = "performance_management_id", strategy = "com.app.employeePortal.category.generator.PermormanceManagementGenerator")
	@GeneratedValue(generator = "performance_management_id")

	@Column(name = "performance_management_id")
	private String performanceManagementId;

	@Column(name = "frequency")
	private String frequency;

	@Column(name = "kpi")
	private String kpi;

	@Column(name = "departmentId")
	private String departmentId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "live_ind", nullable = false)
	private boolean liveInd = false;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "updation_date")
	private Date updationDate;

	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "currency_ind", nullable = false)
	private boolean currencyInd=false;
}
