package com.app.employeePortal.partner.entity;

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
@Table(name="partner_notes_link")

public class PartnerNotesLink {
	@Id
	@GenericGenerator(name = "partner_document_link_id", strategy = "com.app.employeePortal.partner.generator.PartnerNotesLinkGenerator")
    @GeneratedValue(generator = "partner_document_link_id")
	
	@Column(name="partner_note_link_id")
	private String id;

	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="note_id")
	private String noteId;
	
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;
}
