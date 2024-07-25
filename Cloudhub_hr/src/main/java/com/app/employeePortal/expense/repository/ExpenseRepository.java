package com.app.employeePortal.expense.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.expense.entity.ExpenseDetails;
@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseDetails, String> {

	@Query("select exp from ExpenseDetails exp where exp.user_id=:userId and exp.live_ind=true")
	List<ExpenseDetails> getExpenseListByUserId(@Param(value="userId")String userId);
	
	@Query("select exp from ExpenseDetails exp where exp.organization_id=:orgId and exp.live_ind=true")
	List<ExpenseDetails> getExpenseListByOrganizationId(@Param(value="orgId")String orgId);
	
	@Query(value = "select exp  from ExpenseDetails exp  where exp.expense_id=:expId and exp.live_ind=true" )
	public ExpenseDetails getExpenseDetailsById(@Param(value="expId") String expId);
	
	@Query(value = "select a  from ExpenseDetails a  where a.user_id=:userId and a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
    public List<ExpenseDetails> getExpensesByEmployeeIdWithDateRange(@Param(value="userId")String userId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	
	@Query(value = "select a  from ExpenseDetails a  where a.organization_id=:orgId and a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
    public List<ExpenseDetails> getExpensesByOrgIdWithDateRange(@Param(value="orgId")String orgId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
}
