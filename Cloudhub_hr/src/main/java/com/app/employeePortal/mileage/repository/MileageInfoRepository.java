package com.app.employeePortal.mileage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.mileage.entity.MileageInfo;

public interface MileageInfoRepository extends JpaRepository<MileageInfo, String>{

}
