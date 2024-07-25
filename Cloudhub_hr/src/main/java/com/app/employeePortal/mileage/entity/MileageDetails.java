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
@Table(name = "mileage_details")
public class MileageDetails {
	@Id
	@GenericGenerator(name = "mileage_details_id", strategy = "com.app.employeePortal.mileage.generator.MileageDetailsGenerator")
	@GeneratedValue(generator = "mileage_details_id")
	

	@Column(name="mileage_details_id")
	private String mileage_details_id;
	
	@Column(name="mileage_id")
	private String mileage_id;
	
	@Column(name="project_name")
	private String project_name;
	
	@Column(name="client_name")
	private String client_name;
	
	@Column(name="mileage_date")
	private Date mileage_date;
	
	@Column(name="from_location")
	private String from_location;
	
	@Column(name="to_location")
	private String to_location;
	
	@Column(name="distances")
	private double distances;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="mileage_rate")
	private double mileage_rate;
	
	@Column(name="user_id")
	private String user_id;
	
	@Column(name="organization_id")
	private String organization_id;
	
	 @Column(name="creation_date")
	 private Date creation_date;
		
	 @Column(name="currency")
	private String currency;
	 
	@Column(name="live_ind")
	private boolean live_ind;


	
	
	

}
