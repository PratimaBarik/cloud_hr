package com.app.employeePortal.permission.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="sourcing")
public class Sourcing {

	@Id
	@GenericGenerator(name = "sourcing_id", strategy = "com.app.employeePortal.permission.generator.SourcingGenerator")
	@GeneratedValue(generator = "sourcing_id")
	
	@Column(name="sourcing_id")
	private String sourcingId;
	
	@Column(name="talent_out_rich_ind",nullable = false)
	private boolean talentOutRichInd=false;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name ="last_updated_on")
	private Date lastUpdatedOn;
	
	@Column(name="user_id")
	private String userId;	
}
