package com.app.employeePortal.category.entity;

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
@Table(name = "department_performance_mgmt_link")

public class DepartmentPerformanceMgmtLink {
	@Id
	@GenericGenerator(name = "department_performance_mgmt_link_id", strategy = "com.app.employeePortal.category.generator.DepartmentPermormanceMgmtLinkGenerator")
	@GeneratedValue(generator = "department_performance_mgmt_link_id")

	@Column(name = "department_performance_mgmt_link_id")
	private String departmentPerformanceMgmtLinkId;
	
	@Column(name = "performance_management_id")
	private String performanceManagementId;

	@Column(name = "departmentId")
	private String departmentId;
	
	@Column(name = "roleTypeId")
	private String roleTypeId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "live_ind")
	private boolean liveInd;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "updation_date")
	private Date updationDate;

	@Column(name = "updated_by")
	private String updatedBy;
}
