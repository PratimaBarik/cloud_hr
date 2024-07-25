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
@Table(name = "service_line_department_link")

public class ServiceLineDepartmentLink {
	@Id
	@GenericGenerator(name = "service_line_department_link_id", strategy = "com.app.employeePortal.category.generator.ServiceLineDepartmentLinkGenerator")
	@GeneratedValue(generator = "service_line_department_link_id")

	@Column(name = "service_line_department_link_id")
	private String ServiceLineDepartmentLinkId;

	@Column(name = "service_line_id")
	private String serviceLineId;
	
	@Column(name = "department_id")
	private String departmentId;

	@Column(name = "live_ind")
	private boolean liveInd;

	@Column(name = "creation_date")
	private Date creationDate;

}
