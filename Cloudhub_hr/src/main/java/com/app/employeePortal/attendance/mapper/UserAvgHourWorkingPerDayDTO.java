package com.app.employeePortal.attendance.mapper;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAvgHourWorkingPerDayDTO {

	private String userId;
	
	private String userName;
	
	private String orgId;

	private List<UserAvgHourDTO> dto;
}
