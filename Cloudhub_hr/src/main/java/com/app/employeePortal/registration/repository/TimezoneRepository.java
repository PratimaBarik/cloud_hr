package com.app.employeePortal.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.Timezone;

@Repository
public interface TimezoneRepository extends JpaRepository<Timezone, String>{

	Timezone findByTimezoneId(String timeZone);

}
