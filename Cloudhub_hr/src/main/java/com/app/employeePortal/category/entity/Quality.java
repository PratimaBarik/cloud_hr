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
@Table(name = "quality")

public class Quality {
	@Id
	@GenericGenerator(name = "quality_id", strategy = "com.app.employeePortal.category.generator.QualityGenerator")
    @GeneratedValue(generator = "quality_id")
	
	@Column(name="quality_id")
	private String qualityId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="code")
	private String code;
	
	@Column(name="description")
	private String description;
	
}

