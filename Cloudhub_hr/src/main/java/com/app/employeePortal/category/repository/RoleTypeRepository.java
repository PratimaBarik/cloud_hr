package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.RoleType;

@Repository
public interface RoleTypeRepository extends JpaRepository<RoleType, String> {

	RoleType findByRoleTypeId(String roleTypeId);

	List<RoleType> findByorgId(String orgId);

	List<RoleType> findByRoleTypeAndLiveInd(String roleType, boolean liveInd);

	List<RoleType> findByUserId(String userId);

	public List<RoleType> findByOrgIdAndLiveInd(String orgId, boolean liveInd);

	List<RoleType> findByDepartmentIdAndLiveInd(String departmentId, boolean b);

	List<RoleType> findByRoleTypeAndLiveIndAndDepartmentIdAndOrgId(String roleType, boolean b, String departmentId,
			String orgId);

	List<RoleType> findByRoleTypeContainingAndOrgId(String name, String orgId);

	RoleType getByRoleTypeAndLiveIndAndDepartmentIdAndOrgId(String asString, boolean b, String departmentId, String orgId);
}
