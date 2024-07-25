package com.app.employeePortal.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.contact.entity.ContactInfo;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, String> {

}
