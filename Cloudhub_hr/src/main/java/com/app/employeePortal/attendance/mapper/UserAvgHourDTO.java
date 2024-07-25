package com.app.employeePortal.attendance.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAvgHourDTO {

	private String date;
	
	private double totalTimeSpend;
	
	private double totalTimeTakenInHours;
	
	private double totalTimeTakenInMinutes;

}
