package com.app.employeePortal.category.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleDepartmentResponseMapper {

	@JsonProperty("crmInd")
	private boolean crmInd;
	
	@JsonProperty("erpInd")
	private boolean erpInd;
	
	@JsonProperty("imInd")
	private boolean imInd;
	
	@JsonProperty("hrInd")
	private boolean hrInd;
	
	@JsonProperty("recruitProInd")
	private boolean recruitProInd;
	
	@JsonProperty("productionInd")
	private boolean productionInd;
	
	@JsonProperty("repairInd")
	private boolean repairInd;
	
	@JsonProperty("inventoryInd")
	private boolean inventoryInd;
	
	@JsonProperty("orderManagementInd")
	private boolean orderManagementInd;
	
	@JsonProperty("logisticsInd")
	private boolean logisticsInd;
	
	@JsonProperty("procurementInd")
	private boolean procurementInd;
	
	@JsonProperty("eLearningInd")
	private boolean eLearningInd;
	
	@JsonProperty("financeInd")
	private boolean financeInd;
	
	@JsonProperty("projectModInd")
	private boolean projectModInd;
	
	@JsonProperty("ecomModInd")
	private boolean ecomModInd;
	
	@JsonProperty("tradingInd")
	private boolean tradingInd;
}
