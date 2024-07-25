package com.app.employeePortal.distributor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.distributor.entity.MonthlyDistributor;

@Repository
public interface MonthlyDistributorRepository extends JpaRepository<MonthlyDistributor, String> {

	MonthlyDistributor findByMonth(String month);
}
