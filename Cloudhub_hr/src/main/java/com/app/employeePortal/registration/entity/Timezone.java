package com.app.employeePortal.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="timezone")

public class Timezone {
	@Id
	@Column(name="timezone_id")
	private String timezoneId;
	
	
	@Column(name="zone_name")
	private String zoneName;


	

	
}
