package com.app.employeePortal.distributor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.distributor.entity.DistributorAddressLink;
@Repository
public interface DistributorAddressLinkRepository extends JpaRepository<DistributorAddressLink, String>{

	List<DistributorAddressLink> findByDistributorId(String id);

}
