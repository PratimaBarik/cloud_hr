package com.app.employeePortal.support.entity;

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
@Table(name = "ticket")

public class Ticket {
	@Id
	@GenericGenerator(name = "ticket_id", strategy = "com.app.employeePortal.support.generator.TicketGenerator")
    @GeneratedValue(generator = "ticket_id")
	
	@Column(name="ticket_id")
	private String ticketId;
	
	@Lob
	@Column(name="ticketName")
	private String ticketName;
	
	@Column(name="emailId")
	private String emailId;
	
	@Column(name="mobileNumber")
	private String mobileNumber;
	
	@Column(name="orderId")
	private String orderId;
	
	@Column(name="ticketTypeId")
	private String ticketTypeId;
	
	@Column(name="documentId")
	private String documentId;
	
	@Column(name="imageId")
	private String imageId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="start_date")
	private Date startDate;
	
	@JsonProperty("startTime")
	private long startTime;
	
	@Column(name="end_date")
	private Date endDate;
	
	@JsonProperty("endTime")
	private long endTime;
	
	@Lob
	@Column(name="description")
	private String description;
	
}

