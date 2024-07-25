package com.app.employeePortal.permission.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionMapper {
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("candidateShareInd")
	private boolean candidateShareInd;
	
	@JsonProperty("plannerShareInd")
	private boolean plannerShareInd;
	
	@JsonProperty("contactInd")
	private boolean contactInd;
	
	@JsonProperty("customerInd")
	private boolean customerInd;
	
	@JsonProperty("monitizeInd")
	private boolean monitizeInd;
	
	@JsonProperty("partnerContactInd")
	private boolean partnerContactInd;
	
	@JsonProperty("opportunityInd")
	private boolean opportunityShareInd;
	
	@JsonProperty("callInd")
	private boolean calleInd;
	
	@JsonProperty("eventInd")
	private boolean eventInd;
	
	@JsonProperty("taskInd")
	private boolean taskeInd;
	
	@JsonProperty("candiEmpShareInd")
	private boolean candiEmpShareInd;
	
	@JsonProperty("candiEmpSrchInd")
	private boolean candiEmpSrchInd;
	
	@JsonProperty("candiContShareInd")
	private boolean candiContShareInd;
	
	@JsonProperty("candiContSrchInd")
	private boolean candiContSrchInd;
	
	@JsonProperty("partnerInd")
	private boolean partnerInd;
	
	
	@JsonProperty("userName")
	private String userName;
	
	@JsonProperty("type")
	private String type;
	
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;
	
	@JsonProperty("name")
	private String name;

	
}
