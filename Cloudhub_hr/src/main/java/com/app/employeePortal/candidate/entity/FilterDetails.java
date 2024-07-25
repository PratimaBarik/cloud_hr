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
@Table(name = "filter_details")
public class FilterDetails {

	@Id
	@GenericGenerator(name = "filter_details_id", strategy = "com.app.employeePortal.candidate.generator.FilterDetailsGenerator")
    @GeneratedValue(generator = "filter_details_id")
	
	@Column(name="filter_details_id")
	private String filterDetailsId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="billing")
	private String billing;
	
	@Column(name="role_type")
	private String roleType;
	
	@Column(name="work_location")
	private String workLocation;
	
	@Column(name="work_preference")
	private String workPreference;
	
	@Column(name="or_and")
	private String orAnd;
	
	@Column(name = "creation_date")
	private Date creationDate;

	
}
