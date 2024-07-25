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
@Table(name = "leads_catagory")

public class LeadsCatagory {

	@Id
	@GenericGenerator(name = "leads_catagory_id", strategy = "com.app.employeePortal.category.generator.LeadsCatagoryGenerator")
	@GeneratedValue(generator = "leads_catagory_id")

	@Column(name = "leads_catagory_id")
	private String leadsCatagoryId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name = "updation_by")
	private String updationBy;

	@Column(name = "hot")
	private int hot;
	
	@Column(name = "cold")
	private int cold;
	
	@Column(name = "worm")
	private int worm;
	
	@Column(name = "not_defined")
	private int notDefined;
	
}
