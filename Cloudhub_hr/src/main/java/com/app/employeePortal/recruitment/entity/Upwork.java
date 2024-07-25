package com.app.employeePortal.recruitment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="upwork")
public class Upwork {

	@Id
	@GenericGenerator(name = "upwork_id", strategy = "com.app.employeePortal.recruitment.generator.UpworkGenerator")
    @GeneratedValue(generator = "upwork_id")
	
	@Column(name="upwork_id")
	private String upworkId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="upwork_ind")
	private boolean upworkInd;
	
	@Column(name ="last_updated_on")
	private Date lastUpdatedOn;
	
	@Column(name="user_id")
	private String userId;

}
