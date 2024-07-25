package com.app.employeePortal.sequence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sequence_rule")

public class SequenceRule {
	@Id
	@GenericGenerator(name = "sequence_rule_id", strategy = "com.app.employeePortal.sequence.generator.SequenceRuleGenerator")
    @GeneratedValue(generator = "sequence_rule_id")
	
	@Column(name="sequence_rule_id")
	private String sequenceRuleId;
	
	@Column(name="sequence_id")
	private String sequenceId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="true_sequence_rule")
	private String trueSequenceRule;
	
	@Column(name="false_sequence_rule")
	private String falseSequenceRule;
	
	@Column(name="no_input_sequence_rule")
	private String noInputSequenceRule;
	
	@Column(name="true_sequence_id")
	private String trueSequenceId;
	
	@Column(name="false_sequence_id")
	private String falseSequenceId;
	
	@Column(name="no_input_sequence_id")
	private String noInputSequenceId;
}
