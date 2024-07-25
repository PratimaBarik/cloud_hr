package com.app.employeePortal.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.SecreteKey;

@Repository
public interface SecreteKeyRepository extends JpaRepository<SecreteKey, String> {
}
