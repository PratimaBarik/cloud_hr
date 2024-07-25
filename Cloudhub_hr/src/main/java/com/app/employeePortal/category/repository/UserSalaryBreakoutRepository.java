package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.UserSalaryBreakout;
@Repository
public interface UserSalaryBreakoutRepository extends JpaRepository<UserSalaryBreakout, String>{

	UserSalaryBreakout findByUserSalaryBreakoutId(String userSalaryBreakoutId);

	UserSalaryBreakout findByRoleTypeIdAndLiveInd(String roleTypeId, boolean b);

	List<UserSalaryBreakout> findByOrgIdAndLiveInd(String orgId, boolean b);

	UserSalaryBreakout findByDepartmentIdAndRoleTypeId(String departmentId, String roleTypeId);
}
