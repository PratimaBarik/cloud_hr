package com.app.employeePortal.event.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "event_details")
public class EventDetails {

	@Id
	@GenericGenerator(name = "event_details_id", strategy = "com.app.employeePortal.event.generator.EventDetailsGenerator")
	@GeneratedValue(generator = "event_details_id")

	@Column(name = "event_details_id")
	private String event_details_id;

	@Column(name = "event_id")
	private String event_id;

	@Column(name = "subject")
	private String subject;

	@Column(name = "event_type")
	private String event_type;

	@Column(name = "event_heading")
	private String event_heading;

	@Column(name = "start_date")
	private Date start_date;

	@Column(name = "start_time")
	private long start_time;

	@Column(name = "end_date")
	private Date end_date;

	@Column(name = "end_time")
	private long end_time;

	@Column(name = "time_zone")
	private String time_zone;

	@Column(name = "event_status")
	private String event_status;

	@Lob
	@Column(name = "event_description")
	private String event_description;

	@Column(name = "creation_date")
	private Date creation_date;

	@Column(name = "live_ind")
	private boolean live_ind;

	@Column(name = "organization_id")
	private String organization_id;

	@Column(name = "user_id")
	private String user_id;
	
	@Column(name="candidateId")
	private String candidateId;
	
	@Column(name="assigned_to")
	private String assignedTo;
	
	@Column(name = "complition_ind", nullable =false)
	private boolean complitionInd=false;

	@Column(name = "rating")
	private float rating;
	
	@Column(name="update_date")
	private Date updateDate;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "oppertunity")
	private String oppertunity;
	
	@Column(name = "contact")
	private String contact;
	
	@Column(name ="assignedBy")
	private String assignedBy;
}
