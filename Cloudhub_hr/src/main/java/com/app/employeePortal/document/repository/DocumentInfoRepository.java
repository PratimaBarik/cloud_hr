package com.app.employeePortal.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.document.entity.DocumentInfo;

@Repository
public interface DocumentInfoRepository extends JpaRepository<DocumentInfo, String>{

}
