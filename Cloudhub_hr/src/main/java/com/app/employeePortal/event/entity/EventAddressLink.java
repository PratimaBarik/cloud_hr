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
@Table(name="event_address_link")
public class EventAddressLink {

	@Id
	@GenericGenerator(name = "event_address_link_id", strategy = "com.app.employeePortal.event.generator.EventAddressLinkGenrerator")
	@GeneratedValue(generator = "event_address_link_id")
	
	@Column(name="event_address_link_id")
	private String event_address_link_id;
	
	@Column(name="address_id")
	private String address_id;
	
	@Column(name="event_id")
	private String event_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

	
}
