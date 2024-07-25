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
@Table(name = "contact_notes_link")
public class ContactNotesLink {

	@Id
	@GenericGenerator(name = "contact_notes_link_id", strategy = "com.app.employeePortal.contact.generator.ContactNotesLinkGenerator")
	@GeneratedValue(generator = "contact_notes_link_id")
	
	@Column(name="contact_notes_link_id")
	private String contact_notes_link_id;
	
	@Column(name="contact_id")
	private String contact_id;
	
	@Column(name="note_id")
	private String notesId;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;

}
