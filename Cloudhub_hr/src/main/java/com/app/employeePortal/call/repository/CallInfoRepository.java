package com.app.employeePortal.call.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.call.entity.CallInfo;

@Repository
public interface CallInfoRepository extends JpaRepository<CallInfo, String>{

}
