package com.app.employeePortal.sequence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.sequence.entity.Sequence;
@Repository
public interface SequenceRepository extends JpaRepository<Sequence, String>{
	
	@Query(value = "select a  from Sequence a  where a.orgId=:orgId" )
	List<Sequence> getSequenceByOrgId(@Param(value="orgId") String orgId);
	
	@Query(value = "select a  from Sequence a  where a.sequenceId=:sequenceId" )
	public Sequence getSequenceBySequenceId(@Param(value="sequenceId")String sequenceId);
	
	@Query(value = "select a  from Sequence a  where a.sequenceId=:sequenceId" )
	public Sequence findBySequenceeId(@Param(value="sequenceId")String sequenceId);

	
}
