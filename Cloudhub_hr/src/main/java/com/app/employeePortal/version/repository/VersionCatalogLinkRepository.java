package com.app.employeePortal.version.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.version.entity.VersionCatalogLink;

@Repository
public interface VersionCatalogLinkRepository extends  JpaRepository< VersionCatalogLink, String>{

}
