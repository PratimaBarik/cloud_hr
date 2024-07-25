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
@Table(name="leave_info")
public class LeaveInfo {
	@Id
	@GenericGenerator(name = "leave_id", strategy = "com.app.employeePortal.leave.generator.LeaveInfoGenerator")
	@GeneratedValue(generator = "leave_id")
	
	@Column(name="leave_id")
	private String leave_id;
	
	@Column(name="creation_date")
	private Date creation_date;

	
	
	
}
