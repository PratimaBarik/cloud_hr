package com.app.employeePortal.sequence.service;

import java.util.List;

import com.app.employeePortal.sequence.mapper.SequenceMapper;
import com.app.employeePortal.sequence.mapper.SequenceRuleMapper;

public interface SequenceService {

	String saveSequence(SequenceMapper sequenceMapper);

	List<SequenceMapper> getSequenceByOrgId(String orgId);

	SequenceRuleMapper createSequenceRule(SequenceRuleMapper sequenceRuleMapper);

	SequenceRuleMapper getSequenceRuleBySequenceId(String sequenceId);

	SequenceMapper updateSequence(String sequenceId, SequenceMapper sequenceMapper);

	boolean deleteSequence(String sequenceId);

}
