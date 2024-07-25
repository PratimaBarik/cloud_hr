package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class feedbackAverageMapper {

    @JsonProperty("recruitmentAverageFeedback")
    private String  recruitmentAverageFeedback;
    
    @JsonProperty("candidateId")
    private String  candidateId;
    
    @JsonProperty("average")
    private String  average;
}
