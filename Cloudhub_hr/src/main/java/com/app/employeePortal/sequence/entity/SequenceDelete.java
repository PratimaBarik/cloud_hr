package com.app.employeePortal.sequence.entity;

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
@Table(name = "sequence_delete")

public class SequenceDelete {
	@Id
	@GenericGenerator(name = "sequence_delete_id", strategy = "com.app.employeePortal.sequence.generator.SequenceDeleteGenerator")
    @GeneratedValue(generator = "sequence_delete_id")
	
	@Column(name="sequence_delete_id")
	private String sequenceDeleteId;

	@Column(name="sequence_id")
	private String sequenceId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name = "updation_date")
	private Date updationDate;

}

