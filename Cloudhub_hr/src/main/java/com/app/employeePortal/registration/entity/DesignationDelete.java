package com.app.employeePortal.registration.entity;

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
@Table(name = "designation_del")
public class DesignationDelete {

	@Id
	@GenericGenerator(name = "designation_del_id", strategy = "com.app.employeePortal.registration.generator.DesignationDeleteGenerator")
	@GeneratedValue(generator = "designation_del_id")

	@Column(name = "designation_del_id")
	private String designationDelId;
	
	@Column(name = "designation_type_id")
	private String designationTypeId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name = "updated_by")
	private String updatedBy;

}
