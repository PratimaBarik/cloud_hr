package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.DepartmentPerformanceMgmtLink;

@Repository
public interface DepartmentPerformanceMgmtlinkRepository extends JpaRepository<DepartmentPerformanceMgmtLink, String> {

	List<DepartmentPerformanceMgmtLink>  findByDepartmentIdAndRoleTypeId(String departmentId, String roleTypeId);

//	@Query(value = "select exp  from DepartmentPerformanceMgmtLink exp  where exp.userId=:userId and exp.roleTypeId=:roleTypeId and exp.departmentId=:departmentId and exp.liveInd=true")
//	List<DepartmentPerformanceMgmtLink> getByUserIdRoleTypeIdAndDepartmentId(@Param(value = "userId") String userId,
//			@Param(value = "roleTypeId") String roleTypeId, @Param(value = "departmentId") String departmentId);

}
