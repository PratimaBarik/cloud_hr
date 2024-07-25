package com.app.employeePortal.candidate.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class CandidateDocumentMapper {

	@JsonProperty("CandidateTrainingMapper")
	private List<CandidateTrainingMapper> candidateTrainingMapper;
	
}
