package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourCalculateMapper {
	
	@JsonProperty("projectName")
	private String projectName;
	
//	@JsonProperty("projectName")
//	private List<String> listOfProject;

	@JsonProperty("projectId")
	private String projectId;
	
	@JsonProperty("orgId")
	private String orgId;
		
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("candidateId")
    private String candidateId;
	
	@JsonProperty("hour")
    private float hour;
	
	@JsonProperty("billingAmount")
    private float billingAmount;
	
	@JsonProperty("candidateName")
    private String candidateName;
	
	@JsonProperty("actualBillableHour")
	private float actualBillableHour;

	@JsonProperty("finalBillableHour")
	private float finalBillableHour;
	
	@JsonProperty("billableCurency")
	private String billableCurency;
	
	@JsonProperty("finalBillableAmount")
	private float finalBillableAmount;
	
	@JsonProperty("actualBillableAmount")
	private float actualBillableAmount;
	
	@JsonProperty("deviationBillableHour")
	private float deviationBillableHour;
	
	@JsonProperty("deviationBillableAmount")
	private float deviationBillableAmount;
	
	@JsonProperty("customerId")
    private String customerId;
	
	@JsonProperty("customerName")
    private String customerName;
	
	@JsonProperty("month")
	private String month;
	
	@JsonProperty("year")
	private String year;
		
}
