package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.CellDetails;
@Repository
public interface CellProductionLinkRepository extends JpaRepository<CellDetails, String>{
}
