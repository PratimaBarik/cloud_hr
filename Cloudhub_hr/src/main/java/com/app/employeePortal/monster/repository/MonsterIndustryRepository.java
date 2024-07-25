package com.app.employeePortal.monster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.monster.entity.MonsterIndustry;
@Repository

public interface MonsterIndustryRepository extends JpaRepository<MonsterIndustry, String>{
	
}
