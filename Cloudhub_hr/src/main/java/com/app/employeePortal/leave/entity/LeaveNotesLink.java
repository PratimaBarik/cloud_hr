package com.app.employeePortal.leave.entity;

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
@Table(name = "leave_notes_link")
public class LeaveNotesLink {

	@Id
	@GenericGenerator(name = "leave_notes_link_id", strategy = "com.app.employeePortal.leave.generator.LeaveNotesLinkGenerator")
	@GeneratedValue(generator = "leave_notes_link_id")
	
	@Column(name="leave_notes_link_id")
	private String id;
	
	@Column(name="leave_id")
	private String leaveId;
	
	@Column(name="note_id")
	private String noteId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;

	
}
