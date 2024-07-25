package com.app.employeePortal.investor.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class InvestorViewMapperForDropDown {
    @JsonProperty("investorId")
    private String investorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("creationDate")
    private String creationDate;

}
