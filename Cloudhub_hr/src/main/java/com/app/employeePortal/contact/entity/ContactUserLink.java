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
@Table(name = "contact_user_link")
public class ContactUserLink {
	
	@Id
	@GenericGenerator(name = "contact_user_link_id", strategy = "com.app.employeePortal.contact.generator.ContactUserLinkGenerator")
	@GeneratedValue(generator = "contact_user_link_id")
	
	@Column(name="contact_user_link_id")
	private String id;
	
	@Column(name="contact_id")
	private String contactId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="owner_user")
	private String ownerUser;
	
	@Column(name="task_id")
	private String taskId;
	
//	@Column(name="convet_contact_ind", nullable =false)
//	private boolean convertContactInd =false;
	
	@Column(name="creation_date")
	private Date creation_date;

}
