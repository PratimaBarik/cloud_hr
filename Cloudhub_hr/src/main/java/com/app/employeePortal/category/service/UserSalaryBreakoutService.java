package com.app.employeePortal.category.service;

import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.UserSalaryBreakoutReqMapper;
import com.app.employeePortal.category.mapper.UserSalaryBreakoutResponseMapper;

public interface UserSalaryBreakoutService {

	public UserSalaryBreakoutResponseMapper saveUserSalaryBreakout(UserSalaryBreakoutReqMapper requestMapper);
	
	public UserSalaryBreakoutResponseMapper updateUserSalaryBreakout(String userSalaryBreakoutId,
			UserSalaryBreakoutReqMapper requestMapper);

	public void deleteUserSalaryBreakout(String userSalaryBreakoutId, String userId);

	public UserSalaryBreakoutResponseMapper getUserSalaryBreakoutByRoleTypeId(String roleTypeId);

	public HashMap getUserSalaryBreakoutCountByOrgId(String orgId);

}
