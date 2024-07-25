package com.app.employeePortal.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.expense.entity.ExpenseTypeDelete;

@Repository
public interface ExpenseTypeDeleteRepository extends JpaRepository<ExpenseTypeDelete, String> {

	List<ExpenseTypeDelete> findByOrgId(String orgId);

	ExpenseTypeDelete findByExpenseTypeId(String expenseTypeId);


}
