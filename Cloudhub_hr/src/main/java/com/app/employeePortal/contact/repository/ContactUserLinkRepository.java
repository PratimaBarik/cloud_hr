package com.app.employeePortal.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.contact.entity.ContactUserLink;

public interface ContactUserLinkRepository extends JpaRepository<ContactUserLink, String>{

	ContactUserLink findByTaskId(String taskId);

}
