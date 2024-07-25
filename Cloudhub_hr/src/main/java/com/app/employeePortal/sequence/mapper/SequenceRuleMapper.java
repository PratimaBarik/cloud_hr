package com.app.employeePortal.sequence.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SequenceRuleMapper {

	@JsonProperty("sequenceRuleId")
	private String sequenceRuleId;
	
	@JsonProperty("sequenceId")
	private String sequenceId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("trueSequenceRule")
	private String trueSequenceRule;
	
	@JsonProperty("falseSequenceRule")
	private String falseSequenceRule;
	
	@JsonProperty("noInputSequenceRule")
	private String noInputSequenceRule;
	
	@JsonProperty("trueSequenceId")
	private String trueSequenceId;
	
	@JsonProperty("falseSequenceId")
	private String falseSequenceId;
	
	@JsonProperty("noInputSequenceId")
	private String noInputSequenceId;
	
}
