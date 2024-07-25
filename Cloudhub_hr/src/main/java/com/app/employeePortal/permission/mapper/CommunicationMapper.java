package com.app.employeePortal.permission.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommunicationMapper {

	@JsonProperty("communicationId")
	private String communicationId;

	@JsonProperty("emailCustomerInd")
	private boolean emailCustomerInd;

	@JsonProperty("emailJobDesInd")
	private boolean emailJobDesInd;

	@JsonProperty("whatsappCustomerInd")
	private boolean whatsappCustomerInd;

	@JsonProperty("whatsappJobDesInd")
	private boolean whatsappJobDesInd;

	@JsonProperty("candidateEventUpdateInd")
	private boolean candidateEventUpdateInd;

	@JsonProperty("candiWorkflowEnabledInstInd")
	private boolean candiWorkflowEnabledInstInd;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("candiPipelineEmailInd")
	private boolean candiPipelineEmailInd;

}
