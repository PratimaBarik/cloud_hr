package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.SkillCandidateNo;

@Repository
public interface SkillCandidateNoRepository extends JpaRepository<SkillCandidateNo, String> {
	
	@Query(value = "select a  from SkillCandidateNo a  where a.organizationId=:orgId " )
    public List<SkillCandidateNo> getskillCandidateNo(@Param(value="orgId")String orgId);

	public SkillCandidateNo findBySkill(String skillName);

	

}
