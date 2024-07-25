package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.RoleTypeDelete;

@Repository
public interface RoleTypeDeleteRepository extends JpaRepository<RoleTypeDelete, String> {

	RoleTypeDelete findByRoleTypeId(String roleTypeId);

	List<RoleTypeDelete> findByOrgId(String orgId);


}
