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
@Table(name="expense_info")
public class ExpenseInfo {
	@Id
	@GenericGenerator(name = "expense_id", strategy = "com.app.employeePortal.expense.generator.ExpenseInfoGenerator")
	@GeneratedValue(generator = "expense_id")
	
	@Column(name="expense_id")
	private String expense_id;
	
	@Column(name="creation_date")
	private Date creation_date;

	
}
