package com.app.employeePortal.voucher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.voucher.entity.VoucherInfo;

@Repository
public interface VoucherInfoRepository extends JpaRepository<VoucherInfo, String>{

}
