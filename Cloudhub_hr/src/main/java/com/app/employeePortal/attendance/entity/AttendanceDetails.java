package com.app.employeePortal.attendance.entity;
import java.time.LocalTime;
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

@Entity
@Getter
@Setter
@ToString
@Table(name = "attendance_details")

public class AttendanceDetails {
	
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.attendance.generator.AttendanceDetailsGenerator")
    @GeneratedValue(generator = "id")
	
	
	@Column(name="attendance_details_id")
	private String id;
	
	@Column(name="user_id")
	private String userId;

	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="startInd")
	private boolean startInd;
	
	@Column(name = "start_time")
	private LocalTime startTime;

	@Column(name = "stop_time")
	private LocalTime stopTime;
	
	@Column(name="working_hours",nullable = false)
	private double workingHours;

	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="location")
	private String location;
	
	@Column(name="country")
	private String country;
	
	@Column(name="other")
	private String other;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="return_date")
	private Date returnDate;
	}
