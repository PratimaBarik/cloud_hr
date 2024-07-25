package com.app.employeePortal.event.entity;

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
@Table(name="event_type")
public class EventType {
	
	@Id
	@GenericGenerator(name = "event_type_id", strategy = "com.app.employeePortal.event.generator.EventTypeGenerator")
    @GeneratedValue(generator = "event_type_id")
	
	@Column(name="event_type_id")
	private String eventTypeId;
	
	@Column(name="eventType")
	private String eventType;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="edit_ind")
	private boolean editInd;
	
	@Column(name = "live_ind")
	private boolean liveInd;

}
