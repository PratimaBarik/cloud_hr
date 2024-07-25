package com.app.employeePortal.version.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.version.entity.Version;

@Repository
public interface VersionRepository extends  JpaRepository<Version, String>{

}
