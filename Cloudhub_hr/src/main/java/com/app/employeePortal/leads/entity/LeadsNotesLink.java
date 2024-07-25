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
@Table(name = "leads_notes_link")
public class LeadsNotesLink {

	@Id
	@GenericGenerator(name = "leads_notes_link_id", strategy = "com.app.employeePortal.leads.generator.LeadsNotesLinkGenerator")
	@GeneratedValue(generator = "leads_notes_link_id")
	
	@Column(name="leads_notes_link_id")
	private String id;
	
	@Column(name="leads_id")
	private String leadsId;
	
	@Column(name="note_id")
	private String notesId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;

	
}
