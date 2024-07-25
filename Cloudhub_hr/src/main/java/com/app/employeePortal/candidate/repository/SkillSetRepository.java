package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.SkillSetDetails;

@Repository

public interface SkillSetRepository extends JpaRepository<SkillSetDetails, String> {
	@Query(value = "select a  from SkillSetDetails a  where a.candidateId=:candidateId" )
	public List<SkillSetDetails> getSkillSetById(@Param(value="candidateId") String candidateId);

	@Query(value = "select a  from SkillSetDetails a  where a.skillName=:skillName and a.candidateId=:candidateId" )
	public SkillSetDetails getCandidateBySkill(@Param(value="skillName") String skillName ,@Param(value="candidateId") String candidateId);

	
	@Query(value = "select a  from SkillSetDetails a  where a.skillName=:skill" )
	public List<SkillSetDetails> getSkillLinkBySkill(@Param(value="skill") String skill);

	//public SkillSetDetails findOne(String id);

	public SkillSetDetails findBySkillSetDetailsId(String id);

	@Query(value = "select a  from SkillSetDetails a  where a.skillSetDetailsId=:skillSetDetailsId" )
    public SkillSetDetails getCandidateDetailsBySkillSetDetailsId(@Param(value="skillSetDetailsId")String skillSetDetailsId);

	//public List<SkillSetDetails> findBySkillName(String skillName);

	public List<SkillSetDetails> findBySkillNameAndCandidateId(String definationId, String candidateId);

	@Query(value = "select a  from SkillSetDetails a  where a.skillName=:skillName" )
	public SkillSetDetails getSkillLinkBySkillName(@Param(value="skillName")String skillName);


}
