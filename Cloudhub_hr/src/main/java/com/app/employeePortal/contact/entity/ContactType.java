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
@Table(name ="contact_type")
public class ContactType {
	@Id
	@GenericGenerator(name = "contact_type_id", strategy = "com.app.employeePortal.contact.generator.ContactTypeGenerator")
	@GeneratedValue(generator = "contact_type_id")
	
	@Column(name="contact_type_id")
	private String contact_type_id;
	
	@Column(name="contact_type_name")
	private String contact_type_name;
	
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="creator_id")
	private String creator_id;
	
	@Column(name="org_id")
	private String org_id;
	

	@Column(name="live_ind")
	private boolean live_ind;


	
	


}
