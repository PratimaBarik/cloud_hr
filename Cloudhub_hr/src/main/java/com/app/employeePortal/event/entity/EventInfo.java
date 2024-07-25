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
@Table(name="event_info")
public class EventInfo {


	@Id
	@GenericGenerator(name = "event_id", strategy = "com.app.employeePortal.event.generator.EventInfoGenerator")
	@GeneratedValue(generator = "event_id")
	
	@Column(name="event_id")
	private String event_id;
	
	@Column(name="creation_date")
	private Date creation_date;

	
	
}
