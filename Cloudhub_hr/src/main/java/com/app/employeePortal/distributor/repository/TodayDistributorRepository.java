package com.app.employeePortal.distributor.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.distributor.entity.TodayDistributor;

@Repository
public interface TodayDistributorRepository extends JpaRepository<TodayDistributor, String> {

	TodayDistributor findByCreateAt(Date removeTime);
}
