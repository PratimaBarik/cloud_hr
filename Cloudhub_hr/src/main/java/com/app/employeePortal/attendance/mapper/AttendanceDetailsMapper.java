package com.app.employeePortal.attendance.mapper;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AttendanceDetailsMapper {

	@JsonProperty("attendanceId")
	private String attendanceId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("startInd")
	private boolean startInd;

	@JsonProperty("startTime")
    private LocalTime startTime;

	@JsonProperty("stopTime")
    private LocalTime stopTime;
	
	@JsonProperty("location")
	private String location;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("other")
	private String other;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("returnDate")
	private String returnDate;
	
	@JsonProperty("smsInd")
	private boolean smsInd;
	
	@JsonProperty("workingHours")
	private double workingHours;
}
