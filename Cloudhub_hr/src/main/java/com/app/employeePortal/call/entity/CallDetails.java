package com.app.employeePortal.call.entity;

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
@Table(name = "call_details")
public class CallDetails {

	@Id
	@GenericGenerator(name = "call_details_id", strategy = "com.app.employeePortal.call.generator.CallDetailsGenerator")
	@GeneratedValue(generator = "call_details_id")

	@Column(name = "call_details_id")
	private String call_details_id;

	@Column(name = "call_id")
	private String call_id;

	@Column(name = "subject")
	private String subject;

	@Column(name = "call_type")
	private String callType;

	@Column(name = "call_category")
	private String call_category;

	@Lob
	@Column(name = "call_description")
	private String call_description;

	@Column(name = "call_start_date")
	private Date call_start_date;

	@Column(name = "call_start_time")
	private long call_start_time;

	@Column(name = "call_end_date")
	private Date call_end_date;

	@Column(name = "call_end_time")
	private long call_end_time;

	@Column(name = "time_zone")
	private String time_zone;

	@Column(name = "creation_date")
	private Date creation_date;

	@Column(name = "live_ind")
	private boolean live_ind;

	@Column(name = "organization_id")
	private String organization_id;

	@Column(name = "user_id")
	private String user_id;

	@Column(name = "call_status")
	private String call_status;

	@Column(name = "contact")
	private String contact;

	@Column(name = "candidateId")
	private String candidateId;

	@Column(name = "mode")
	private String mode;

	@Column(name = "mode_type")
	private String modeType;

	@Column(name = "mode_link")
	private String modeLink;

	@Column(name = "remind_ind")
	private boolean remind_ind;

	@Column(name = "remind_time")
	private String remind_time;

	@Column(name = "assigned_to")
	private String assignedTo;

	@Column(name = "complition_ind", nullable = false)
	private boolean complitionInd = false;

	@Column(name = "rating")
	private float rating;

	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "oppertunity")
	private String oppertunity;
	
	@Column(name = "assigned_by")
	private String assignedBy;
}
