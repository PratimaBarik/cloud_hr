package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.FilterDetails;

@Repository
public interface FilterDetailsRepository extends JpaRepository<FilterDetails, String> {

	List<FilterDetails> findByUserId(String userId);

}
