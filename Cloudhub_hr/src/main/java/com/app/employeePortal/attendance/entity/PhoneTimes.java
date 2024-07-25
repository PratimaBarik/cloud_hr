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

@Entity
@Getter
@Setter
@Table(name = "phone_times")
public class PhoneTimes {
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.attendance.generator.PhoneTimesGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "phone_times_id")
	private String id;
	
	@Column(name = "phone_id")
	private String phoneId;
	
	@Column(name = "pause_start_time")
	private LocalTime pauseStartTime;

	@Column(name = "pause_end_time")
	private LocalTime pauseEndTime;
	
	@Column(name = "pause_ind")
	private boolean pauseInd;
	
	@Column(name="pause_duration",nullable = false)
	private double pauseDuration;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "created_at")
    private Date createAt;
	
	@Column(name = "active")
	private boolean active;
	

}
