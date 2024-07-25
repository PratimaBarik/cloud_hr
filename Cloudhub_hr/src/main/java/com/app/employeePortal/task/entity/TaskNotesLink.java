package com.app.employeePortal.task.entity;

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
@Table(name = "task_notes_link")
public class TaskNotesLink {

	@Id
	@GenericGenerator(name = "task_notes_link_id", strategy = "com.app.employeePortal.task.generator.TaskNotesLinkGenerator")
	@GeneratedValue(generator = "task_notes_link_id")
	
	@Column(name="task_notes_link_id")
	private String id;
	
	@Column(name="task_id")
	private String taskId;
	
	@Column(name="note_id")
	private String notesId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;
	
}
