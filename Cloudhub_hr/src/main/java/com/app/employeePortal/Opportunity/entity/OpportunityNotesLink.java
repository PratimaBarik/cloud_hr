package com.app.employeePortal.Opportunity.entity;

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
@Table(name="opportunity_notes_link")
public class OpportunityNotesLink {
	
	@Id
	@GenericGenerator(name = "opportunity_notes_link_id",strategy="com.app.employeePortal.Opportunity.generator.OpportunityNoteLinkGenerator")
	@GeneratedValue(generator = "opportunity_notes_link_id")
	
	@Column(name="opportunity_notes_link_id")
	private String opportunity_notes_link_id;
	
	@Column(name="opportunity_id")
	private String opportunity_id ;
	
	@Column(name="note_id")
	private String notesId;
	
	@Column(name="creation_date")
	private Date creation_date;

	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;

}
