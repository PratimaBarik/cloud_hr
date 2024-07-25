package com.app.employeePortal.candidate.entity;

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
@Table(name = "candidate_notes_link")
public class CandidateNotesLink {
	
	@Id
	@GenericGenerator(name = "candidate_notes_link_id", strategy = "com.app.employeePortal.candidate.generator.CandidateNotesLinkGenerator")
    @GeneratedValue(generator = "candidate_notes_link_id")
	
	@Column(name="candidate_notes_link_id")
	private String candidate_notes_link_id;

	@Column(name="candidate_id")
	private String candidate_id;
	
	@Column(name="notes_id")
	private String notesId;
	
	@Column(name="creation_date")
	private Date creation_date;

	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;



}
