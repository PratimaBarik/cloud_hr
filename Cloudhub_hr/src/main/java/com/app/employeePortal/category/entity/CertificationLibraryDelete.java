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
@Table(name = "certification_delete")
public class CertificationLibraryDelete {

	@Id
	@GenericGenerator(name = "certification_delete_id", strategy = "com.app.employeePortal.category.generator.CertificationLibraryDeleteGenerator")
    @GeneratedValue(generator = "certification_delete_id")
	
	@Column(name="certification_delete_id")
	private String certificationDeleteId;
	
	@Column(name="certification_id")
	private String certificationId;

	@Column(name="org_id")
	private String orgId;

	@Column(name="user_id")
	private String userId;

	@Column(name="updation_date")
	private Date updationDate;

}
