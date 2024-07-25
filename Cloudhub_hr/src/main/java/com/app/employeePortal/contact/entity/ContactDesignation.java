package com.app.employeePortal.contact.entity;

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
@Table(name="contact_designation")
public class ContactDesignation {
	@Id
	@GenericGenerator(name = "contact_designation_id", strategy = "com.app.employeePortal.contact.generator.ContactDesignationGenerator")
	@GeneratedValue(generator = "contact_designation_id")
	
	@Column(name="contact_designation_id")
	private String contact_designation_id;
	
	@Column(name="contact_designation_name")
	private String contact_designation_name;
	
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="creator_id")
	private String creator_id;
	
	@Column(name="org_id")
	private String org_id;
	

	@Column(name="live_ind")
	private boolean live_ind;

	
	


}
