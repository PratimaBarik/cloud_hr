package com.app.employeePortal.category.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceMapper {
	
	@JsonProperty("invoiceId")
	private String invoiceId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("projectName")
	private String projectName;

	@JsonProperty("projectId")
	private String projectId;
	
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
	
	@JsonProperty("customerId")
    private String customerId;
	
	@JsonProperty("customerName")
    private String customerName;
	
	@JsonProperty("result")
	private List<InvoiceMapper> result;
	
//	@JsonProperty("invoiceStartDate")
//	private String invoiceStartDate;
//	
//	@JsonProperty("invoiceEndDate")
//	private String invoiceEndDate;
	
	@JsonProperty("month")
    private String month;
	
	@JsonProperty("year")
    private String year;
	
	@JsonProperty("actualBillableHour")
	private float actualBillableHour;

	@JsonProperty("projectedBillableHour")
	private float projectedBillableHour;
	
	@JsonProperty("billableCurency")
	private String billableCurency;
	
	@JsonProperty("projectedBillableAmount")
	private float projectedBillableAmount;
	
	@JsonProperty("actualBillableAmount")
	private float actualBillableAmount;
	
	@JsonProperty("creatorName")
    private String creatorName;
		
}
