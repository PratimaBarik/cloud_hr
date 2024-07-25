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
@Table(name="event_candidate_link")
public class CandidateEventLink {

	@Id
	@GenericGenerator(name = "event_candidate_link_id", strategy = "com.app.employeePortal.event.generator.CandidateEventLinkGenerator")
	@GeneratedValue(generator = "event_candidate_link_id")
	
	@Column(name="event_candidate_link_id")
	private String id;
	
	
	@Column(name="event_id")
	private String eventId;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
}
