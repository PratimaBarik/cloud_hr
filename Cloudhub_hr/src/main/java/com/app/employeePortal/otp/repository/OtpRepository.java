package com.app.employeePortal.otp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.otp.entity.OTPEntity;

@Repository
public interface OtpRepository extends JpaRepository<OTPEntity, Long> {
	
	OTPEntity findByEmailIdAndOtp(String emailId, Integer otp);

	 Optional<OTPEntity> findByPhoneAndOtp(String phone, Integer otp);

	 Optional<OTPEntity> findByEmailId(String emailId);

	Optional<OTPEntity> findById(String id);

}
