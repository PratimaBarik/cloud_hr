package com.app.employeePortal.ruleEngine.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.ApprovalTaskLink;
import com.app.employeePortal.ruleEngine.entity.ReportScheduling;
@Repository
public interface ReportSchedulingRepository extends JpaRepository<ReportScheduling, String>{

	List<ReportScheduling> findByOrgId(String orgId);

	ReportScheduling findByReportSchedulingId(String reportSchedulingId);
	
	@Query(value = "select a  from ReportScheduling a  where a.department=:department and a.frequency=:frequency" )
    public ReportScheduling getReportForManagementweekly(@Param(value = "department")String department,@Param(value = "frequency")String frequency);
	
	
	@Query(value = "select a  from ApprovalTaskLink a  where a.taskId=:taskId and a.liveInd=true" )
	ApprovalTaskLink getTaskApprovalLink(@Param(value = "taskId")String taskId);

	List<ReportScheduling> findByFrequency(String frequency);

	ReportScheduling findByTypeAndDepartmentAndFrequency(String type, String department, String frequency);

	List<ReportScheduling> findByDepartment(String departmentId);


}
