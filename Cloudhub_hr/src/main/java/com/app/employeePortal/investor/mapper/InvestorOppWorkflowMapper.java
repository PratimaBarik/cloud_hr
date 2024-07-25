package com.app.employeePortal.investor.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InvestorOppWorkflowMapper {
    @JsonProperty("investorOppWorkflowId")
    private String investorOppWorkflowId;

    @JsonProperty("workflowName")
    private String workflowName;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("orgId")
    private String orgId;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("publishInd")
    private boolean publishInd;

    @JsonProperty("liveInd")
    private boolean liveInd;

    @JsonProperty("updationDate")
    private String updationDate;

    @JsonProperty("name")
    private String name;

}
