package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.candidate.entity.DefinationDetails;

public interface DefinationRepository extends JpaRepository<DefinationDetails, String>{
	
	@Query(value = "select a  from DefinationDetails a  where a.org_id=:orgId and a.liveInd = true" )
    public List<DefinationDetails> getDefinationsOfAdmin(@Param(value="orgId")String orgId);

	@Query(value = "select a  from DefinationDetails a  where a.name=:skillName and a.org_id=:orgId and a.liveInd = true" )
	public List<DefinationDetails> getByNameAndOrgId(@Param(value="skillName") String skillName,@Param(value="orgId") String orgId);

	public DefinationDetails findByDefinationId(String definationId);

	//public List<DefinationDetails> findByNameContaining(String name);
	
	public List<DefinationDetails> findByNameContaining(String name);

	@Query(value = "select a  from DefinationDetails a  where a.name=:skillName and a.liveInd = true" )
	public List<DefinationDetails> getBySkillName(@Param(value="skillName")String skillName);

	@Query(value = "select a  from DefinationDetails a  where a.name=:skillName and a.user_id=:userId and a.liveInd = true" )
	public List<DefinationDetails> getByNameAndUserId(@Param(value="skillName") String skillName,@Param(value="userId") String userId );

	@Query(value = "select a  from DefinationDetails a  where a.name=:skillName and a.liveInd = true" )
	public DefinationDetails getBySkillNameAndLiveInd(@Param(value="skillName")String skillName);

	public List<DefinationDetails> findByNameContainingAndDefinationIdNotAndLiveInd(String name,String definationId,boolean liveInd);

	public List<DefinationDetails> findByNameContainingAndLiveInd(String name,boolean b);
}
