package com.app.employeePortal.distributor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.distributor.entity.Distributor;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, String> {

	Distributor findByInvestorAndActive(String investorId, boolean b);

}
