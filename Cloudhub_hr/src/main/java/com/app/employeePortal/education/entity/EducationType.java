package com.app.employeePortal.education.entity;

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
@Table(name="education_type")
public class EducationType {

	@Id
	@GenericGenerator(name = "education_type_id", strategy = "com.app.employeePortal.education.generator.EducationTypeGenerator")
    @GeneratedValue(generator = "education_type_id")
	
	@Column(name="education_type_id")
	private String educationTypeId;
	
	@Column(name="education_type")
	private String educationType;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "edit_ind")
	private boolean editInd;
	
	@Column(name = "live_ind")
	private boolean liveInd;


}
