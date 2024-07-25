package com.app.employeePortal.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.expense.entity.ExpenseInfo;


@Repository
public interface ExpenseInfoRepository extends JpaRepository<ExpenseInfo, String>{

}
