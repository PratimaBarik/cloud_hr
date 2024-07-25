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
@Table(name = "unit")

public class Unit {
	

	@Id
    @GenericGenerator(name = "unit_id", strategy = "com.app.employeePortal.category.generator.UnitGenerator")
    @GeneratedValue(generator = "unit_id")
	
	@Column(name = "unit_id")
    private String unitId;
	

	@Column(name="unit_name")
	private String unitName;
	
	@Column(name = "org_id")
    private String orgId;

	@Column(name = "user_id")
    private String userId;
	
	@Column(name="live_ind",nullable = false)
	private boolean liveInd=false;
	
	@Column(name="edit_ind",nullable = false)
	private boolean editInd=false;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="updatedBy")
	private String updatedBy;
}
