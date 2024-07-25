package com.app.employeePortal.candidate.entity;

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
@Table(name = "defination_details_del")
public class DefinationDetailsDelete {
	
	@Id
	@GenericGenerator(name = "defination_details_del_id", strategy = "com.app.employeePortal.candidate.generator.DefinationDetailsDeleteGenerator")
    @GeneratedValue(generator = "defination_details_del_id")
	
	@Column(name="defination_details_del_id")
	private String definationDetailsDelId;

	@Column(name="defination_id")
	private String definationId;

	@Column(name="name")
	private String name;

	@Column(name="org_id")
	private String orgId;

	@Column(name="user_id")
	private String userId;

	@Column(name = "updation_date")
	private Date updationDate;

	
	
	
	

}
