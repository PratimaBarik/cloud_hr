package com.app.employeePortal.organization.repository;

import com.app.employeePortal.organization.entity.InvoiceToUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceToUserRepository extends JpaRepository<InvoiceToUser,String> {
    InvoiceToUser findByOrgId(String orgId);
}
