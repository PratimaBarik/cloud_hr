package com.app.employeePortal.mileage.entity;

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
@Table(name = "mileage_rate")
public class MileageRate {
	@Id
	@GenericGenerator(name = "mileage_rate_id", strategy = "com.app.employeePortal.mileage.generator.MileageRateGenerator")
	@GeneratedValue(generator = "mileage_rate_id")
	

	@Column(name="mileage_rate_id")
	private String mileageRateId;
	
	@Column(name="mileage_rate")
	private double mileageRate;
	
	@Column(name="country")
	private String country;

	@Column(name="user_id")
	private String userId;
	
	@Column(name="organization_id")
	private String organizationId;
	
	@Column(name="creation_date")
	private Date creationDate;
		
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;
}
