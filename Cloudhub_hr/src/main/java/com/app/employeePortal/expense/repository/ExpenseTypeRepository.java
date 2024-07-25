package com.app.employeePortal.expense.repository;

import java.util.List;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.expense.entity.ExpenseType;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, String> {

	// List<ExpenseType> findByorgId(String orgId);

	ExpenseType findByExpenseTypeId(String expenseTypeId);

//	List<ExpenseType> findByExpenseTypeContaining(String name);

//	List<ExpenseType> findByExpenseTypeAndLiveInd(String expenseType,boolean b);

	List<ExpenseType> findByOrgIdAndLiveInd(String orgId, boolean liveInd);

	List<ExpenseType> findByExpenseTypeContainingAndOrgId(String name, String orgId);

	List<ExpenseType> findByExpenseTypeAndLiveIndAndOrgId(String expenseType, boolean b, String orgId);

}
