package com.app.employeePortal.category.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegionsTargetDashBoardMapper {
	
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
	 
	@JsonProperty("creationDate")
	private String creationDate;
	
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
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("employeeName")
	private String employeeName;
	
	@JsonProperty("currencyInd")
	private boolean currencyInd;
	
	@JsonProperty("userCurrency")
	private String userCurrency;
	
	@JsonProperty("useKpiList")
	private List<UserKpiDashBoardResponseMapper> useKpiList;

	
	
	
	
}
