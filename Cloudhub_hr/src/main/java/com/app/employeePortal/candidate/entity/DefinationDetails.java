package com.app.employeePortal.candidate.entity;

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
@Table(name = "defination_details")
public class DefinationDetails {
	
	@Id
	@GenericGenerator(name = "defination_details_id", strategy = "com.app.employeePortal.candidate.generator.DefinationDetailsGenerator")
    @GeneratedValue(generator = "defination_details_id")
	
	@Column(name="defination_details_id")
	private String defination_details_id;

	@Column(name="defination_id")
	private String definationId;

	@Column(name="name")
	private String name;

	@Column(name="org_id")
	private String org_id;

	@Column(name="user_id")
	private String user_id;

	@Column(name="creation_date")
	private Date creation_date;

	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="edit_ind",nullable = false)
	private boolean editInd;

	
	
	
	

}
