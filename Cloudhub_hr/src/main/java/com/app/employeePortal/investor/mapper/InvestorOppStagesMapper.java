package com.app.employeePortal.investor.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InvestorOppStagesMapper {
    @JsonProperty("investorOppStagesId")
    private String investorOppStagesId;

    @JsonProperty("stageName")
    private String stageName;

    @JsonProperty("orgId")
    private String orgId;

    @JsonProperty("probability")
    private double probability;

    @JsonProperty("days")
    private int days;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("publishInd")
    private boolean publishInd;

    @JsonProperty("liveInd")
    private boolean liveInd;

	@JsonProperty("responsible")
	private String responsible;

    @JsonProperty("investorOppWorkflowId")
    private String investorOppWorkflowId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("updationDate")
    private String updationDate;

    @JsonProperty("name")
    private String name;
}
