package com.app.employeePortal.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.address.entity.AddressInfo;
@Repository
public interface AddressInfoRepository extends JpaRepository<AddressInfo, String>{

}
