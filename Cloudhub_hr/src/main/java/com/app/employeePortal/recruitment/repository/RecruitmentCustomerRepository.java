package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.RecruitmentCustomerDetails;

public interface RecruitmentCustomerRepository extends JpaRepository<RecruitmentCustomerDetails, String>{

	public List<RecruitmentCustomerDetails> findByCustomerId(String customerId);

	@Query(value = "select a  from RecruitmentCustomerDetails a  where a.customerId=:customerId and a.recruitmentId=:recruitmentId and liveInd=true" )
	public RecruitmentCustomerDetails getRecriutmentsByCustomerId(@Param(value="customerId") String customerId,@Param(value="recruitmentId") String recruitmentId);

	
}
