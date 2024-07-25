package com.app.employeePortal.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.email.entity.SentMailLink;

public interface SentMailLinkRepository extends JpaRepository<SentMailLink, String>{

}
