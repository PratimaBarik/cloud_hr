package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "industry")
public class Industry {

	@Id
	@GenericGenerator(name = "industry_id", strategy = "com.app.employeePortal.category.generator.IndustryGenerator")
	@GeneratedValue(generator = "industry_id")

	@Column(name = "industry_id")
	private String industryId;

	@Column(name = "name")
	private String name;

	@Column(name = "orgId")
	private String orgId;

	@Column(name = "userId")
	private String userId;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "liveInd")
	private boolean liveInd;
	
	@Column(name = "updationDate")
	private Date updationDate;
	
	@Column(name = "updatedBy")
	private String updatedBy;
	
}
