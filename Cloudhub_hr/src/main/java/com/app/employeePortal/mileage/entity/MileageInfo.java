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
@Table(name="mileage_info")
public class MileageInfo {
	@Id
	@GenericGenerator(name = "mileage_id", strategy = "com.app.employeePortal.mileage.generator.MileageInfoGenerator")
	@GeneratedValue(generator = "mileage_id")
	
	@Column(name="mileage_id")
	private String mileage_id;
	
	@Column(name="creation_date")
	private Date creation_date;

	
	
}
