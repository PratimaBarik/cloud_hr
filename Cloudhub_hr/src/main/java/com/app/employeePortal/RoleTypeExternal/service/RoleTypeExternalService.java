package com.app.employeePortal.RoleTypeExternal.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.RoleTypeExternal.mapper.RoleTypeExternalMapper;

public interface RoleTypeExternalService {

	RoleTypeExternalMapper createRoleTypeExternal(RoleTypeExternalMapper roleMapper);

	RoleTypeExternalMapper updateRoleTypeExternal(String roleTypeExternalId, RoleTypeExternalMapper roleMapper);

	List<RoleTypeExternalMapper> getRoleListByOrgId(String orgId);

	void deleteRoleTypeDetailsById(String roleTypeExternalId,RoleTypeExternalMapper externalMapper);

//	List<RoleTypeExternalMapper> getRoleTypeExternalByName(String name);

	HashMap getRoleTypeExternalCountByOrgId(String orgId);

	ByteArrayInputStream exportRoleTypeExternalListToExcel(List<RoleTypeExternalMapper> list);

	List<RoleTypeExternalMapper> getRoleTypeExternalByNameByOrgLevel(String name, String orgId);

	boolean checkRoleNameInRoleTypeByOrgLevel(String roleType, String orgId);

}
