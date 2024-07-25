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
@Table(name="education_type_del")
public class EducationTypeDelete {

	@Id
	@GenericGenerator(name = "education_type_del_id", strategy = "com.app.employeePortal.education.generator.EducationTypeDeleteGenerator")
    @GeneratedValue(generator = "education_type_del_id")
	
	@Column(name="education_type_del_id")
	private String educationTyDelId;
	
	@Column(name="education_type_id")
	private String educationTypeId;
	
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="user_id")
	private String userId;

	@Column(name = "updated_by")
	private String updatedBy;
}
