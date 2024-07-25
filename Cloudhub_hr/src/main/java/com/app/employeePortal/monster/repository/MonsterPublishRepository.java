package com.app.employeePortal.monster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.monster.entity.MonsterPublish;
@Repository
public interface MonsterPublishRepository extends JpaRepository<MonsterPublish, String>{

	
	
	@Query(value = "select a  from MonsterPublish a  where a.recruitmentId=:recruitmentId " )
    public List<MonsterPublish> getMonsterDetailsById(@Param(value="recruitmentId")String recruitmentId);
	
}