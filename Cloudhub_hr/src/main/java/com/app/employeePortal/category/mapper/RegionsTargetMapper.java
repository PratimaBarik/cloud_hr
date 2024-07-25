package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegionsTargetMapper {
	
	@JsonProperty("regionsTargetId")
	private String regionsTargetId;
	
	@JsonProperty("regionsId")
	private String regionsId;
	
	@JsonProperty("regions")
	private String regions;

	@JsonProperty("sales")
	private double sales;
	
	@JsonProperty("fulfilment")
	private double fulfilment;
	
	@JsonProperty("investment")
	private double investment;
	
	@JsonProperty("year")
	private double year;
	
	@JsonProperty("salesCurrency")
	private String salesCurrency;
	
	@JsonProperty("investmentCurrency")
	private String investmentCurrency;
	
	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;
	 
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("quarter")
	private String quarter;
	
	@JsonProperty("kpiSales")
	private String kpiSales;
	
	@JsonProperty("kpiFullfillment")
	private String kpiFullfillment;
	
	@JsonProperty("kpiInvestment")
	private String kpiInvestment;
	
	@JsonProperty("kpiSalesName")
	private String kpiSalesName;
	
	@JsonProperty("kpiFullfillmentName")
	private String kpiFullfillmentName;
	
	@JsonProperty("kpiInvestmentName")
	private String kpiInvestmentName;
	
}
