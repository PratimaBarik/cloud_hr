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
@Table(name = "certification_library")
public class CertificationLibrary {

	@Id
	@GenericGenerator(name = "certification_id", strategy = "com.app.employeePortal.category.generator.CertificationLibraryGenerator")
    @GeneratedValue(generator = "certification_id")
	
	@Column(name="certification_id")
	private String certificationId;

	@Column(name="name")
	private String name;

	@Column(name="org_id")
	private String orgId;

	@Column(name="user_id")
	private String userId;

	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="edit_ind",nullable = false)
	private boolean editInd;
}
