package com.app.employeePortal.ruleEngine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.ruleEngine.entity.ReportSchedulingDelete;
@Repository
public interface ReportSchedulingDeleteRepository extends JpaRepository<ReportSchedulingDelete, String>{

	ReportSchedulingDelete findByReportSchedulingId(String reportSchedulingId);

	List<ReportSchedulingDelete> findByOrgId(String orgId);

}
