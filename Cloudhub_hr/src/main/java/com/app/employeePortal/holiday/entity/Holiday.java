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
@Table(name ="holiday")
public class Holiday {
	@Id
	@GenericGenerator(name = "holiday_id", strategy = "com.app.employeePortal.holiday.generator.HolidayGenerator")
	@GeneratedValue(generator = "holiday_id")

	@Column(name = "holiday_id")
	private String holiday_id;
	
	@Column(name = "holiday_name")
	private String holiday_name;
	
	@Column(name ="date")
	private Date date;
	
	@Column(name ="holiday_type")
	private String holiday_type;
	
	@Column(name ="org_id")
	private String org_id;
	
	@Column(name ="country_id")
	private String countryId;
	
	@Column(name ="year")
	private int year;
	
	@Column(name ="creation_date")
	private Date creation_date;
	
	@Column(name ="live_ind")
	private boolean live_ind;

	@Column(name="updated_by")
	private String updatedBy;
		
	@Column(name = "updation_date")
	private Date updationDate;
}
