package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.KeySkillDetails;

@Repository
public interface KeySkillRepository extends JpaRepository<KeySkillDetails, String> {

	
	public List<KeySkillDetails> getKeyskillsByEmployeeId( String empId);
	
	
	@Query(value = "select a  from KeySkillDetails a  where a.employeeId=:employeeId and a.id=:keySkillId")
	public KeySkillDetails getKeySkillDetailsByEmpIdAndKeySkillId (@Param(value="employeeId") String employeeId,
			                                                       @Param(value="keySkillId") String keySkillId);




	public List<KeySkillDetails> findBySkillNameContaining(String skill);

	@Query(value = "select a  from KeySkillDetails a  where a.employeeId=:employeeId and liveInd=true")
	public List<KeySkillDetails> getSkillByEmployeeId(@Param(value="employeeId")String employeeId);

    List<KeySkillDetails> findBySkillNameAndEmployeeId(String definationId, String employeeId);

    @Query(value = "select a  from KeySkillDetails a  where a.id=:keySkillId" )
	public KeySkillDetails getEmployeeSkillByKeySkillId(@Param(value="keySkillId")String keySkillId);
}
