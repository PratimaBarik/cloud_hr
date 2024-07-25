package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.ServiceLineDepartmentLink;

@Repository
public interface ServiceLineDepartmentLinkRepository extends JpaRepository<ServiceLineDepartmentLink, String> {

	List<ServiceLineDepartmentLink> findByDepartmentIdAndLiveInd(String departmentId, boolean b);
}
