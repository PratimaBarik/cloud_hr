package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import com.app.employeePortal.category.mapper.ModuleDepartmentResponseMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentMapper {

	@JsonProperty("departmentId")
	private String departmentId;

	@JsonProperty("departmentName")
	private String departmentName;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("fullName")
	private String fullName;
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("sectorName")
	private String sectorName;
	
	@JsonProperty("EditInd")
	private boolean EditInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("mandetoryInd")
	private boolean mandetoryInd;
	
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
	
	@JsonProperty("moduleMapper")
	private ModuleDepartmentResponseMapper moduleMapper;
	
	@JsonProperty("financeInd")
	private boolean financeInd;
	
	@JsonProperty("serviceLineInd")
	private boolean serviceLineInd;
}
