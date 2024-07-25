package com.app.employeePortal.mileage.entity;

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
@Table(name = "mileage_notes_link")
public class MileageNotesLink {

	@Id
	@GenericGenerator(name = "mileage_notes_link_id", strategy = "com.app.employeePortal.mileage.generator.MileageNotesLinkGenerator")
	@GeneratedValue(generator = "mileage_notes_link_id")
	
	@Column(name="mileage_notes_link_id")
	private String id;
	
	@Column(name="mileage_id")
	private String mileageId;
	
	@Column(name="note_id")
	private String noteId;
	
	@Column(name="creation_date")
	private Date creationDate;
		
	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;
	
}
