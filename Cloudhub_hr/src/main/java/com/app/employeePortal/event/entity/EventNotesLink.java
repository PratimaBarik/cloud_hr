package com.app.employeePortal.event.entity;

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
@Table(name = "event_notes_link")
public class EventNotesLink {

	@Id
	@GenericGenerator(name = "eventNotesLinkId", strategy = "com.app.employeePortal.event.generator.EventNotesLinkGenerator")
	@GeneratedValue(generator = "eventNotesLinkId")
	
	@Column(name="event_notes_link_id")
	private String eventNotesLinkId;
	
	@Column(name="event_id")
	private String eventId;
	
	@Column(name="note_id")
	private String notesId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;

	
}
