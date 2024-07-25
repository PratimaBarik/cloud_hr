package com.app.employeePortal.monster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.monster.entity.MonsterBoard;
@Repository
public interface MonsterBoardRepository extends JpaRepository<MonsterBoard, String>{

}

