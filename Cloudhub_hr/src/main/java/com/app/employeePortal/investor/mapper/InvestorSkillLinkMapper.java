package com.app.employeePortal.investor.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class InvestorSkillLinkMapper {
    @JsonProperty("investorSkillLinkId")
    private String investorSkillLinkId;

    @JsonProperty("skillName")
    private String skillName;

    @JsonProperty("investorId")
    private String investorId;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("editInd")
    private boolean editInd;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("orgId")
    private String orgId;
}
