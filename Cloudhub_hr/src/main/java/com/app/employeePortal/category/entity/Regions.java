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
@Table(name = "regions")

public class Regions {
	@Id
	@GenericGenerator(name = "regions_id", strategy = "com.app.employeePortal.category.generator.RegionsGenerator")
	@GeneratedValue(generator = "regions_id")

	@Column(name = "regions_id")
	private String regionsId;

	@Column(name = "regions")
	private String regions;

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
}
