package com.app.employeePortal.employee.entity;

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
@Table(name="room_notes_link")
public class RoomNotesLink {
	
	@Id
	@GenericGenerator(name = "room_notes_link_id", strategy = "com.app.employeePortal.employee.generator.RoomNotesLinkGenerator")
	@GeneratedValue(generator = "room_notes_link_id")
	
	@Column(name="room_notes_link_id")
	private String id;
	
	@Column(name="notes_id")
	private String notesId;
	
	@Column(name="room_id")
	private String roomId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind",nullable =false)
	private boolean liveInd = true;



}
