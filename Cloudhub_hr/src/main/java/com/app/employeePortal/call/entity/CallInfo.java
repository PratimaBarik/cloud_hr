package com.app.employeePortal.call.entity;

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
@Table(name="call_info")
public class CallInfo {

	@Id
	@GenericGenerator(name = "call_id", strategy = "com.app.employeePortal.call.generator.CallInfoGenerator")
	@GeneratedValue(generator = "call_id")
	
	@Column(name="call_id")
	private String call_id;
	
	@Column(name="creation_date")
	private Date creation_date;

	
	
}
