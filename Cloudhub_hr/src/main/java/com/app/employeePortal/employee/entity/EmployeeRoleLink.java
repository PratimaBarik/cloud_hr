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
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="employee_role_link")
public class EmployeeRoleLink {

	@Id
	@GenericGenerator(name = "employee_role_link_id", strategy = "com.app.employeePortal.employee.generator.EmployeeRoleLinkGenerator")
	@GeneratedValue(generator = "employee_role_link_id")
	
	@Column(name="employee_role_link_id")
	private String employeeRoleLinkId;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="role")
	private String role;
	
	@Column(name="provide_date")
	private Date provideDate;
	
	@Column(name="live_ind", nullable = false)
	private boolean liveInd =false;
	
}
