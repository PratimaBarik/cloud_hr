package com.app.employeePortal.distributor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.distributor.entity.YearlyDistributor;

@Repository
public interface YearlyDistributorRepository extends JpaRepository<YearlyDistributor, String> {

	YearlyDistributor findByYear(String year);

	//TodayDistributor findByCreateAt(Date removeTime);
}
