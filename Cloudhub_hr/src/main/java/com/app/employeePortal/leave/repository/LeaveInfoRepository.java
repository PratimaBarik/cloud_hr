package com.app.employeePortal.leave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leave.entity.LeaveInfo;

@Repository
public interface LeaveInfoRepository extends JpaRepository<LeaveInfo, String>{

}
