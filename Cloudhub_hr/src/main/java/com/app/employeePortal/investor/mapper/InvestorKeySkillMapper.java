package com.app.employeePortal.investor.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class InvestorKeySkillMapper {
    @JsonProperty("skillSetList")
    private List<String> skillSetList;
}
