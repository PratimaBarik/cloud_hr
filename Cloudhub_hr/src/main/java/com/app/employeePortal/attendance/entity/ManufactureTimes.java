package com.app.employeePortal.attendance.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "manufacture_times")
public class ManufactureTimes extends Auditable{
	@Id
//	@GenericGenerator(name = "manufacture_times_id", strategy = "com.app.NuboxedErp.generator.ManufactureTimesGenerator")
//	@GeneratedValue(generator = "manufacture_times_id")

	@Column(name = "manufacture_times_id")
	private String manufactureTimesId;
	
	@Column(name = "manufacture_id")
	private String manufactureId;
	
	@Column(name = "production_product_id")
	private String productionProductId;
	
	@Column(name = "start_time")
	private LocalTime startTime;

	@Column(name = "end_time")
	private LocalTime endTime;
	
	@Column(name = "start_ind")
	private boolean startInd;
	
	@Column(name="working_duration")
	private double workingDuration;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "org_id")
	private String orgId;

}
