package com.app.employeePortal.expense.entity;

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
@Table(name = "expense_notes_link")
public class ExpenseNotesLink {

	@Id
	@GenericGenerator(name = "expense_notes_link_id", strategy = "com.app.employeePortal.expense.generator.ExpenseNotesLinkGenerator")
	@GeneratedValue(generator = "expense_notes_link_id")
	
	@Column(name="expense_notes_link_id")
	private String id;
	
	@Column(name="expense_id")
	private String expenseId;
	
	@Column(name="note_id")
	private String noteId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	
	@Column(name="live_ind", nullable =false)
	private boolean liveInd = true;
	
}
