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
@Table(name="expense_type_delete")
public class ExpenseTypeDelete {
	
	@Id
	@GenericGenerator(name = "expense_type_delete_id", strategy = "com.app.employeePortal.expense.generator.ExpenseTypeDeleteGenerator")
    @GeneratedValue(generator = "expense_type_delete_id")
	
	
	@Column(name="expense_type_delete_id")
	private String expenseTypeDeleteId;
	
	@Column(name="expense_type_id")
	private String expenseTypeId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "updation_date")
	private Date updationDate;

	@Column(name = "updated_by")
	private String updatedBy;

}
