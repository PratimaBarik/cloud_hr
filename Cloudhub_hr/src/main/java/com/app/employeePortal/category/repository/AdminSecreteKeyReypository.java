package com.app.employeePortal.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.AdminSecreteKey;

@Repository
public interface AdminSecreteKeyReypository extends JpaRepository<AdminSecreteKey, String> {
	AdminSecreteKey findByUserId(String userId);
}
