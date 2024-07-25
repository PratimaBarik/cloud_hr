package com.app.employeePortal.category.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMapper {
	
	@JsonProperty("roleType")
	private String roleType;

	@JsonProperty("roleTypeId")
	private String roleTypeId;
	
	@JsonProperty("libraryType")
	private String libraryType;

	@JsonProperty("libraryTypeId")
	private String libraryTypeId;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("department")
	private String department;
	
	@JsonProperty("skillList")
	private List<String> skillList;
	
	@JsonProperty("EditInd")
	private boolean EditInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("crmInd")
	private boolean crmInd;
	
	@JsonProperty("erpInd")
	private boolean erpInd;
	
	@JsonProperty("imInd")
	private boolean imInd;
	
	@JsonProperty("accountInd")
	private boolean accountInd;
	
	@JsonProperty("recruitOppsInd")
	private boolean recruitOppsInd;
	
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
}
