package com.app.employeePortal.RoleTypeExternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.RoleTypeExternal.entity.RoleTypeExternal;

@Repository
public interface RoleTypeExternalRepo  extends JpaRepository<RoleTypeExternal, String> {

	RoleTypeExternal findByRoleTypeExternalId(String roleTypeId);

	List<RoleTypeExternal> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<RoleTypeExternal> findByOrgId(String roleTypeId);

	List<RoleTypeExternal> findByRoleTypeAndLiveIndAndOrgId(String roleType, boolean b, String orgId);

	List<RoleTypeExternal> findByRoleTypeContainingAndOrgId(String name, String orgId);

}
