package com.app.employeePortal.leave.entity;

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
@Table(name="leave_details")
public class LeaveDetails {
	@Id
	@GenericGenerator(name = "leave_details_id", strategy = "com.app.employeePortal.leave.generator.LeaveDetailsGenerator")
	@GeneratedValue(generator = "leave_details_id")
	
	@Column(name="leave_details_id")
	private String leave_details_id;
	
	@Column(name="leave_id")
	private String leave_id;
	
	@Column(name="employee_id")
	private String employee_id;
	
	@Column(name="org_id")
	private String org_id;
	
	@Column(name="start_date")
	private Date start_date;
	
	
	@Column(name="end_date")
	private Date end_date;
	
	
	@Column(name="cover_details")
	private String cover_details;
	
	@Lob
	@Column(name="reason")
	private String reason;
	
	@Column(name="status")
	private String status;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;
	
	@Column(name="task_id")
	private String task_id;

	@Column(name="reject_reason")
	private String rejectReason;
		
	@Column(name="half_day_ind", nullable =false)
	private boolean halfDayInd = false;
	
	@Column(name="half_day_type")
	private boolean halfDayType;
	
	@Column(name="self_other_ind", nullable =false)
	private boolean selfOtherInd = false;
	
	@Column(name="otherUser")
	private String otherUser;
}
