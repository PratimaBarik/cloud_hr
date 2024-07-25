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
@Table(name = "shipBy")

public class ShipBy {
	@Id
	@GenericGenerator(name = "shipBy_id", strategy = "com.app.employeePortal.category.generator.ShipbyGenerator")
    @GeneratedValue(generator = "shipBy_id")
	
	@Column(name="shipBy_id")
	private String shipById;
	
	@Column(name="name")
	private String name;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind", nullable= false)
	private boolean liveInd=false;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name = "edit_ind")
	private boolean editInd;
}

