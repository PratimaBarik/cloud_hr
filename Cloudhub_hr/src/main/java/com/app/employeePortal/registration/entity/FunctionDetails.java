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
@Table(name="function_detail")
public class FunctionDetails {

	@Id
	@GenericGenerator(name = "function_type_id", strategy = "com.app.employeePortal.registration.generator.FunctionGenerator")
    @GeneratedValue(generator = "function_type_id")
	
	@Column(name="function_type_id")
	private String functionTypeId;
	
	@Column(name="function_type")
	private String functionType;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;
}
