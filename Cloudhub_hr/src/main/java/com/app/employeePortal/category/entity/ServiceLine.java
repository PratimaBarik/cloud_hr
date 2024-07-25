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
@Table(name = "service_line")

public class ServiceLine {
	@Id
	@GenericGenerator(name = "service_line_id", strategy = "com.app.employeePortal.category.generator.ServiceLineGenerator")
	@GeneratedValue(generator = "service_line_id")

	@Column(name = "service_line_id")
	private String serviceLineId;

	@Column(name = "service_line_name")
	private String serviceLineName;
	
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
