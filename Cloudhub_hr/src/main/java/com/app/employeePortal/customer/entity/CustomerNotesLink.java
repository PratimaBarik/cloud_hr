package com.app.employeePortal.customer.entity;

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
@Table(name = "customer_notes_link")
public class CustomerNotesLink {

	@Id
	@GenericGenerator(name = "customer_notes_link_id", strategy = "com.app.employeePortal.customer.generator.CustomerNotesLinkGenerator")
	@GeneratedValue(generator = "customer_notes_link_id")
	
	@Column(name="customer_notes_link_id")
	private String id;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="note_id")
	private String notesId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;

	
}
