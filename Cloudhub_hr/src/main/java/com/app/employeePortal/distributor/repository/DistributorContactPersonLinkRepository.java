package com.app.employeePortal.distributor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.distributor.entity.DistributorContactPersonLink;


public interface DistributorContactPersonLinkRepository extends JpaRepository<DistributorContactPersonLink, String> {

	List<DistributorContactPersonLink> findByDistributorId(String distributorId);

}
