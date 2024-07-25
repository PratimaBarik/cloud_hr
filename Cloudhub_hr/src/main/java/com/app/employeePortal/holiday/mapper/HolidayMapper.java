package com.app.employeePortal.holiday.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayMapper {
	
	
	@JsonProperty("holidayId")
 	private String holidayId;
	
	@JsonProperty("holidayName")
 	private String holidayName;
	
	
	@JsonProperty("date")
 	private String date;
	
	@JsonProperty("holidayType")
 	private String holidayType;
	
	@JsonProperty("organizationId")
 	private String organizationId;
	
	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("country")
	private String country;
	
	@JsonProperty("countryId")
	private String countryId;
	
	@JsonProperty("year")
	private int year;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("userId")
	private String userId; 

	@JsonProperty("orgOptnlHoliday")
	private int orgOptnlHoliday;
	
	@JsonProperty("userOptnlHolidayApplied")
	private int userOptnlHolidayApplied;
	
	@JsonProperty("optnlHolidayApplyInd")
	private boolean optnlHolidayApplyInd;
}
