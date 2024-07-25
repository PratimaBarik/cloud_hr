
package com.app.employeePortal.employee.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeDetails;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDetails, String> {
	
	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.liveInd=true and a.suspendInd=false" )
    public List<EmployeeDetails> getEmployeesByOrgId(@Param(value="orgId") String orgId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.liveInd=true" )
    public List<EmployeeDetails> getEmployeesByOrgIdAndLiveInd(@Param(value="orgId") String orgId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.liveInd=true and a.suspendInd=false" )
    public List<EmployeeDetails> getEmployeesByLiveIndAndSuspendInd();
	
	@Query(value = "select a  from EmployeeDetails a  where a.userId=:userId and a.liveInd=true and a.suspendInd=false" )
    public List<EmployeeDetails> getEmployeesByUserId(@Param(value="userId") String userId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.employeeId=:empId and a.liveInd=:liveInd" )
	public EmployeeDetails getEmployeeDetailsByEmployeeId(@Param(value="empId") String empId, @Param(value="liveInd") boolean liveInd);
	
	@Query(value = "select a  from EmployeeDetails a  where a.employeeId=:empId and a.creationDate BETWEEN :startDate AND :endDate" )
    public List<EmployeeDetails> getEmployeesByIdWithDateRange(@Param(value="empId")String empId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	
	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true" )
    public List<EmployeeDetails> getEmployeesByOrgIdWithDateRange(@Param(value="orgId")String orgId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	
	@Query(value = "select a  from EmployeeDetails a  where a.employeeId=:employeeId and a.liveInd=true and a.suspendInd=false" )
	public EmployeeDetails getEmployeeDetailsByEmployeeId(@Param(value="employeeId") String empId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.employeeId=:employeeId and a.liveInd=true and a.suspendInd=true" )
	public EmployeeDetails getSuspendEmployeeDetailsByEmployeeId(@Param(value="employeeId") String empId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.employeeId=:employeeId and a.liveInd=true" )
	public EmployeeDetails getEmployeeDetailsByEmployeeIdAndLiveInd(@Param(value="employeeId") String empId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.userId=:userId and a.liveInd=true" )
    public EmployeeDetails getEmployeesByuserId(@Param(value="userId") String userId);

	
	@Query(value = "select a  from EmployeeDetails a  where a.userId=:userId " )
    public List <EmployeeDetails> getEmployeeListByUserId(@Param(value="userId")String userId);

	@Query(value = "select a  from EmployeeDetails a  where a.reportingManager=:reportingManager and a.liveInd=true" )
	public EmployeeDetails getEmployeesByReportingManager(@Param(value="reportingManager")String reportingManager);


	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.department=:department and a.liveInd=true" )
    public List<EmployeeDetails> getAllRecuiterByOrgId(@Param(value="orgId") String orgId,@Param(value="department") String department);

	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.department=:department and a.liveInd=true" )
	public List<EmployeeDetails> getAllSalesEmployeeList(@Param(value="orgId") String orgId,@Param(value="department") String department);
	
	@Query(value = "select a  from EmployeeDetails a  where a.department=:department and a.liveInd=true and a.suspendInd=false" )
	public List<EmployeeDetails> getEmployeeListByDepartmentId(@Param(value="department") String department);
	
	@Query(value = "select a  from EmployeeDetails a  where a.department=:department and a.location=:location and a.liveInd=true and a.suspendInd=false" )
	public List<EmployeeDetails> getEmployeeListByDepartmentIdAndLocationId(@Param(value="department") String department, @Param(value="location") String location);

	public List<EmployeeDetails> findByFullNameContainingAndLiveInd(String name, boolean b);

	@Query(value = "select a  from EmployeeDetails a  where a.emailId=:emailId and a.liveInd=true and a.suspendInd =true" )
	public EmployeeDetails getEmailByMailId(@Param(value="emailId") String emailId);
	
//	@Query(value = "select a  from EmployeeDetails a  where LOWER(a.emailId)=LOWER(:emailId) and a.liveInd=true and a.suspendInd =false" )
//	public EmployeeDetails getEmailByMailId(@Param(value="emailId") String emailId);
//	
//	public EmployeeDetails findByEmailIdIgnoreCaseAndLiveIndAndSuspendInd(String email, boolean b, boolean c);
	
	@Query(value = "select a  from EmployeeDetails a  where a.emailId=:emailId and a.liveInd=true and a.suspendInd =false" )
	public EmployeeDetails getEmployeeByMailId(@Param(value="emailId") String emailId);

	public List<EmployeeDetails> findByDepartmentAndSuspendIndAndLiveInd(String department_id, boolean suspendInd, boolean liveInd);

	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.role=:role and a.liveInd=true and a.suspendInd=false" )
    public List<EmployeeDetails> getActiveEmployeesByOrgIdAndRole(@Param(value="orgId") String orgId,@Param(value="role") String role);

	public EmployeeDetails findByFullName(String name);

	public List<EmployeeDetails> findByEmployeeType(String employeeType);

	public List<EmployeeDetails> findByEmployeeTypeNot(String employeeType1);

	
	@Query(value = "select a  from EmployeeDetails a  where a.employeeId=:userId " )
    public EmployeeDetails getEmployeeByUserId(@Param(value="userId")String userId);

	@Query(value = "select a  from EmployeeDetails a  where a.department=:departmentId and a.liveInd=true " )
	public List<EmployeeDetails> getEmployeesByDepartmentId(@Param(value="departmentId")String departmentId);

	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.designation=:designationTypeId and a.liveInd=true and a.suspendInd=false" )
	public List<EmployeeDetails> getEmployeesByOrgIdAndDesignation(@Param(value="designationTypeId")String designationTypeId,@Param(value="orgId")String orgId);

	@Query(value = "select a  from EmployeeDetails a  where a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true" )
	public List<EmployeeDetails> getEmployeeListsByStartdateAndEndDateAndLiveInd(@Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	@Query("SELECT e FROM EmployeeDetails e " +
	           "WHERE (MONTH(e.dateOfJoining) = MONTH(:startDate) AND DAY(e.dateOfJoining) >= DAY(:startDate)) " +
	           "AND MONTH(e.dateOfJoining) = MONTH(:endDate) " )
//	          + "ORDER BY MONTH(e.dateOfJoining), DAY(e.dateOfJoining)")
	public List<EmployeeDetails> findByJoiningDate(@Param(value="startDate")Date startDate, @Param(value="endDate")Date endDate);

	@Query("SELECT e FROM EmployeeDetails e " +
	           "WHERE (MONTH(e.dob) = MONTH(:startDate) AND DAY(e.dob) >= DAY(:startDate)) " +
	           "AND MONTH(e.dob) = MONTH(:endDate) " )
//	           +    "ORDER BY MONTH(e.dob), DAY(e.dob)")
	public List<EmployeeDetails> findByDOB(@Param(value="startDate")Date startDate, @Param(value="endDate")Date endDate);
	@Query("SELECT e FROM EmployeeDetails e " +
	           "WHERE (MONTH(e.dateOfJoining) = MONTH(:startDate) AND DAY(e.dateOfJoining) = DAY(:startDate)) ")
//			+ "AND MONTH(e.dob) = MONTH(:endDate) ")
	public List<EmployeeDetails> getAnniversary(@Param(value="startDate")Date startDate);
	@Query("SELECT e FROM EmployeeDetails e " +
	           "WHERE (MONTH(e.dob) = MONTH(:startDate) AND DAY(e.dob) = DAY(:startDate)) ")
	public List<EmployeeDetails> getBirthday(@Param(value="startDate")Date startDate);
	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.roleType=:roleTypeId and a.liveInd=true and a.suspendInd=false" )
	List<EmployeeDetails> getActiveEmployeesByOrgIdAndRoleType(@Param(value="orgId") String orgId,@Param(value="roleTypeId") String roleTypeId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.department=:departmentId and a.roleType=:roleTypeId and a.liveInd=true and a.suspendInd=false" )
	List<EmployeeDetails> getActiveEmployeesByDepartmentAndRoleType(@Param(value="departmentId") String departmentId,@Param(value="roleTypeId") String roleTypeId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.department=:departmentId and a.liveInd=true and a.suspendInd=false and a.dateOfJoining BETWEEN :startDate AND :endDate" )
	List<EmployeeDetails> findByDepartmentAndSuspendIndAndLiveIndAndJoiningDate(@Param("departmentId") String departmentId,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	@Query(value = "select a  from EmployeeDetails a  where a.location=:locationId and a.liveInd=true and a.suspendInd=false" )
    public List<EmployeeDetails> getEmployeesByLocationId(@Param(value="locationId") String locationId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.reportingManager=:reptMngrId and a.liveInd=true and a.suspendInd=false" )
	public List<EmployeeDetails> getEmployeeDetailsByReportingManagerId(@Param(value="reptMngrId") String reptMngrId);
	
	@Query(value = "select a  from EmployeeDetails a  where (a.reportingManager=:reptMngrId OR a.userId=:reptMngrId)and a.liveInd=true and a.suspendInd=false" )
	public List<EmployeeDetails> getEmployeeDetailsByReportingManagerIdAndUserId(@Param(value="reptMngrId") String reptMngrId);

	@Query(value = "select a  from EmployeeDetails a  where a.emailId=:emailId and a.employeeId=:employeeId and a.liveInd=true " )
	public EmployeeDetails getEmployeeByMailIdAndEmployeeId(@Param(value="emailId") String emailId, @Param(value="employeeId") String employeeId);
	
	@Query(value = "select a  from EmployeeDetails a  where a.emailId=:emailId and a.liveInd=true and a.suspendInd =true" )
	public EmployeeDetails getEmailByEmailId(@Param(value="emailId") String emailId);
	
	@Query("SELECT a FROM EmployeeDetails a WHERE a.liveInd=true and a.suspendInd=false and a.employeeId IN :employeeIds")
    List<EmployeeDetails> getEmployeesByIds(@Param(value="employeeIds") List<String> employeeIds);

	public EmployeeDetails findByEmailId(String email_id);

	@Query(value = "select a  from EmployeeDetails a  where a.location=:locationDetailsId and a.department=:departmentId and a.liveInd=true and a.suspendInd=false" )
	public List<EmployeeDetails> getEmployeeDetailsByLocationIdAndDepartmentId(String locationDetailsId,
			String departmentId);

	@Query(value = "select a  from EmployeeDetails a  where a.orgId=:orgId and a.employeeType=:type and a.liveInd=true and a.suspendInd=false" )
    public List<EmployeeDetails> getEmployeesByOrgIdAndType(@Param(value="orgId") String orgId,@Param(value="type") String type);
	
	@Query(value = "select a  from EmployeeDetails a  where a.location=:location and a.liveInd=true and a.suspendInd=false" )
	public List<EmployeeDetails> getEmployeeListByLocationId(@Param(value="location") String location);

	public List<EmployeeDetails> findByLiveInd(boolean b);

	public List<EmployeeDetails> findByDepartmentAndRoleTypeAndLiveInd(String departmentId,
			String roletypetypeId,boolean b);

	EmployeeDetails findByEmployeeId(String userId);
}

