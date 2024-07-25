package com.app.employeePortal.monster.service;

import java.util.List;

import com.app.employeePortal.monster.entity.MonsterPublish;
import com.app.employeePortal.monster.mapper.MonsterBoardMapper;
import com.app.employeePortal.monster.mapper.MonsterCategoryMapper;
import com.app.employeePortal.monster.mapper.MonsterCredentialsMapper;
import com.app.employeePortal.monster.mapper.MonsterIndustryMapper;
import com.app.employeePortal.monster.mapper.MonsterOccupationMapper;
import com.app.employeePortal.monster.mapper.MonsterPublishMapper;

public interface MonsterService {

	String saveMonsterCredentials(MonsterCredentialsMapper monsterCredentialsMapper);

	MonsterCredentialsMapper getMonsterCredentialsByOrgId(String orgId);

	List<MonsterBoardMapper> jobBoardList();

	List<MonsterCategoryMapper> jobCategoryList();

	List<MonsterOccupationMapper> jobOccupationList();

	List<MonsterIndustryMapper> jobIndustryList();

	String saveToMonsterPublish(MonsterPublishMapper monsterPublishMapper);

	List<MonsterPublishMapper> getMonsterDetailsById(String requirementId);

	List<MonsterPublish> getMonsterPublishList();

	

	

}
