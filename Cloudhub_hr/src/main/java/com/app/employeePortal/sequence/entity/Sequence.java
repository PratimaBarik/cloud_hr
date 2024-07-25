package com.app.employeePortal.sequence.entity;

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
@Table(name = "sequence")

public class Sequence {
	@Id
	@GenericGenerator(name = "sequence_id", strategy = "com.app.employeePortal.sequence.generator.SequenceGenerator")
    @GeneratedValue(generator = "sequence_id")
	
	@Column(name="sequence_id")
	private String sequenceId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="type")
	private String type;
	
	@Column(name="no_of_days")
	private int noOfDays;
}

