package com.app.employeePortal.registration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "designation")
public class Designation {

	@Id
	@GenericGenerator(name = "designation_type_id", strategy = "com.app.employeePortal.registration.generator.DesignationGenerator")
	@GeneratedValue(generator = "designation_type_id")

	@Column(name = "designation_type_id")
	private String designationTypeId;

	@Lob
	@Column(name = "designation_type")
	private String designationType;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "edit_ind")
	private boolean editInd;

	@Column(name = "live_ind")
	private boolean liveInd;

}
