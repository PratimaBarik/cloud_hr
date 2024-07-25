package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.ServiceLine;

@Repository
public interface ServiceLineRepository extends JpaRepository<ServiceLine, String> {

	ServiceLine findByServiceLineId(String serviceLineId);

	List<ServiceLine> findByOrgIdAndLiveInd(String orgId, boolean b);

//	List<ServiceLine> findByServiceLineNameContainingAndLiveInd(String name, boolean b);

	List<ServiceLine> findByServiceLineNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

	List<ServiceLine> findByServiceLineNameAndLiveIndAndOrgId(String name, boolean b, String orgId);
}
