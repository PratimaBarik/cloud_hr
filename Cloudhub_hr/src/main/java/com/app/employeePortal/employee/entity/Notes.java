package com.app.employeePortal.employee.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="notes")
public class Notes {
	@Id
	@GenericGenerator(name = "notes_id", strategy = "com.app.employeePortal.employee.generator.NotesGenerator")
	@GeneratedValue(generator = "notes_id")
	
	@Column(name="notes_id")
	private String notes_id;
	
	@Lob
	@Column(name="notes")
	private String notes;
	
	@Column(name="creation_date")
	private Date creation_date;

	@Column(name="user_id")
	private String userId;
	
	@Column(name="liveInd")
	private boolean liveInd;
	
	@Override
	public String toString() {
		return "Notes [notes_id=" + notes_id + ", notes=" + notes + ", creation_date=" + creation_date + "]";
	}

	
	
	
	

}
