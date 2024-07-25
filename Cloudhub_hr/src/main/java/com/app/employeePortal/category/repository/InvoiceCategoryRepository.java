package com.app.employeePortal.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.InvoiceCategory;

@Repository
public interface InvoiceCategoryRepository extends JpaRepository<InvoiceCategory, String> {

	InvoiceCategory findByOrgId(String orgId);
}
