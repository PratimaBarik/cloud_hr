package com.app.employeePortal.monster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.monster.entity.MonsterCredentials;
@Repository
public interface MonsterCredentialsRepository extends JpaRepository<MonsterCredentials, String>{

	

	MonsterCredentials findByOrgId(String orgId);
	
	

}
