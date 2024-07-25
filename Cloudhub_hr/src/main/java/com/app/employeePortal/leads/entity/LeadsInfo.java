package com.app.employeePortal.leads.entity;

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
@Table(name="leads_info")
public class LeadsInfo {
	
	@Id
	@GenericGenerator(name = "leads_info_id", strategy = "com.app.employeePortal.leads.generator.LeadsInfoGenerator")
    @GeneratedValue(generator = "leads_info_id")
	
	@Column(name="leads_info_id")
	private String id;
	
	
	@Column(name="creator_id")
	private String creatorId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;

	
	

	
}
