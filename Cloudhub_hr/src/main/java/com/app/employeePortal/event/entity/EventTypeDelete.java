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
@Table(name="event_type_del")
public class EventTypeDelete {
	
	@Id
	@GenericGenerator(name = "event_type_del_id", strategy = "com.app.employeePortal.event.generator.EventTypeDeleteGenerator")
    @GeneratedValue(generator = "event_type_del_id")
	
	@Column(name="event_type_del_id")
	private String eventTypeDelId;
	
	@Column(name="event_type_id")
	private String eventTypeId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;

}
