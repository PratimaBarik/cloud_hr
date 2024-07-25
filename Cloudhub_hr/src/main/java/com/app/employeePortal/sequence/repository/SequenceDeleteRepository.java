package com.app.employeePortal.sequence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.sequence.entity.SequenceDelete;
@Repository
public interface SequenceDeleteRepository extends JpaRepository<SequenceDelete, String>{

	List<SequenceDelete> findByOrgId(String orgId);

	SequenceDelete findBySequenceId(String sequenceId);

	
	
}
