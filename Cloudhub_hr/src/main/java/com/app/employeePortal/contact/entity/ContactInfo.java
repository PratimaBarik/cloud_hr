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
@Table(name ="contact_info")
public class ContactInfo {
	
	@Id
	@GenericGenerator(name = "contact_id", strategy = "com.app.employeePortal.contact.generator.ContactInfoGenerator")
    @GeneratedValue(generator = "contact_id")
	
	
	
	@Column(name="contact_id")
	private String contact_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="creator_id")
	private String creator_id;
	
	@Column(name="org_id")
	private String org_id;

	

	
	
	

}
