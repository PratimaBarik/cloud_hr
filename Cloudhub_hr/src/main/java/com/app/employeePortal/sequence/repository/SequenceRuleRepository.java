package com.app.employeePortal.sequence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.sequence.entity.SequenceRule;
@Repository
public interface SequenceRuleRepository  extends JpaRepository<SequenceRule, String>{

	SequenceRule findBySequenceId(String sequenceId);

//	@Query(value = "select a  from SequenceRule a  where a.sequenceId=:sequenceId" )
//	SequenceRule getSequenceBySequenceId(@Param(value="sequenceId")String sequenceId);

}
