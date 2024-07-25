package com.app.employeePortal.category.entity;

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
@Table(name = "user_salary_breakout")
public class UserSalaryBreakout {

	@Id
	@GenericGenerator(name = "user_salary_breakout_id", strategy = "com.app.employeePortal.category.generator.UserSalaryBreakoutGenerator")
    @GeneratedValue(generator = "user_salary_breakout_id")
	
	@Column(name="user_salary_breakout_id")
	private String userSalaryBreakoutId;

	@Column(name="departmentId")
	private String departmentId;

	@Column(name="roleTypeId")
	private String roleTypeId;
	
	@Column(name="org_id")
	private String orgId;

	@Column(name="user_id")
	private String userId;

	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="transportation")
	private double transportation;
	
	@Column(name="basic")
	private double basic;
	
	@Column(name="housing")
	private double housing;
	
	@Column(name="others")
	private double others;
	
	
}
