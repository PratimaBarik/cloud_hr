package com.app.employeePortal.category.repository;

import com.app.employeePortal.category.entity.MinActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinimumActivityRepo extends JpaRepository<MinActivity,String> {
    MinActivity getByOrgId(String orgId);
}
