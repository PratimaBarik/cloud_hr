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
@Table(name="expense_type")
public class ExpenseType {
	
	@Id
	@GenericGenerator(name = "expense_type_id", strategy = "com.app.employeePortal.expense.generator.ExpenseTypeGenerator")
    @GeneratedValue(generator = "expense_type_id")
	
	@Column(name="expense_type_id")
	private String expenseTypeId;
	
	@Column(name="expenseType")
	private String expenseType;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name="edit_ind")
	private boolean editInd;
	
	@Column(name = "live_ind")
	private boolean liveInd;

}
