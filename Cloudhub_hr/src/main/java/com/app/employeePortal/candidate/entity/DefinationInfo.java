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
@Setter
@Getter
@Entity
@Table(name = "defination_info")
public class DefinationInfo {
	
	@Id
	@GenericGenerator(name = "defination_info_id", strategy = "com.app.employeePortal.candidate.generator.DefinationInfoGenerator")
    @GeneratedValue(generator = "defination_info_id")
	
	@Column(name="defination_info_id")
	private String defination_info_id;

	@Column(name="creation_date")
	private Date creation_date;


}
