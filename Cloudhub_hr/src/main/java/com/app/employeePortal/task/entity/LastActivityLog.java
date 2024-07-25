package com.app.employeePortal.task.entity;

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
@Table(name="last_activity_log")
public class LastActivityLog {
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.task.generator.LastActivityLogGenerator")
    @GeneratedValue(generator = "id")

    @Column(name="id")
    private String id;

	@Column(name = "activity_id")
	private String activityId;

	@Column(name = "subject")
	private String subject;

	@Column(name = "activity_type")
	private String activityType;

//	@Column(name = "call_category")
//	private String call_category;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "start_time")
	private long startTime;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "end_time")
	private long endTime;

	@Column(name = "time_zone")
	private String timeZone;

	@Column(name = "creation_date")
	private Date creationDate;

//	@Column(name = "live_ind")
//	private boolean live_ind;

	@Column(name = "organization_id")
	private String organizationId;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_type")
	private String userType;
	
	@Column(name = "user_type_id")
	private String userTypeId;

//	@Column(name = "call_status")
//	private String call_status;
//
//	@Column(name = "contact")
//	private String contact;
//
//	@Column(name = "candidateId")
//	private String candidateId;
//
//	@Column(name = "mode")
//	private String mode;
//
//	@Column(name = "mode_type")
//	private String modeType;
//
//	@Column(name = "mode_link")
//	private String modeLink;
//
//	@Column(name = "remind_ind")
//	private boolean remind_ind;
//
//	@Column(name = "remind_time")
//	private String remind_time;
//
//	@Column(name = "assigned_to")
//	private String assignedTo;
//
//	@Column(name = "complition_ind", nullable = false)
//	private boolean complitionInd = false;
//
//	@Column(name = "rating")
//	private float rating;
//
//	@Column(name = "update_date")
//	private Date updateDate;
//	
//	@Column(name = "customer")
//	private String customer;
//	
//	@Column(name = "oppertunity")
//	private String oppertunity;
//	
}
