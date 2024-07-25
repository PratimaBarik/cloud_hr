package com.app.employeePortal.leads.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.leads.entity.RoundRobbinConfig;

public interface RoundRobbinConfigRepo extends JpaRepository<RoundRobbinConfig, Long>{

	RoundRobbinConfig findByName(String name);

}
