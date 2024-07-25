package com.app.employeePortal.holiday.entity;

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
@Table(name ="weekends")
public class Weekends {
	@Id
	@GenericGenerator(name = "weekends_id", strategy = "com.app.employeePortal.holiday.generator.WeekendsGenerator")
	@GeneratedValue(generator = "weekends_id")

	@Column(name = "weekends_id")
	private String weekendsId;
	
	@Column(name ="org_id")
	private String orgId;
	
	@Column(name ="user_id")
	private String userId;
	
	@Column(name ="creation_date")
	private Date creationDate;
	
	@Column(name ="monday_ind")
	private boolean mondayInd;
	
	@Column(name ="tuesday_ind")
	private boolean tuesdayInd;
	
	@Column(name ="wednesday_ind")
	private boolean wednesdayInd;
	
	@Column(name ="thursday_ind")
	private boolean thursdayInd;
	
	@Column(name ="friday_ind")
	private boolean fridayInd;
	
	@Column(name ="saturday_ind")
	private boolean saturdayInd;
	
	@Column(name ="sunday_ind")
	private boolean sundayInd;
	
	@Column(name ="country_id")
	private String countryId;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
}
