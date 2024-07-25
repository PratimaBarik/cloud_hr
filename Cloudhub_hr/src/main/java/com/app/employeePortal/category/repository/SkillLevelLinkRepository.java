package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.SkillLevelLink;
@Repository
public interface SkillLevelLinkRepository extends JpaRepository<SkillLevelLink, String>{

	List<SkillLevelLink> findByLiveInd(boolean b);

	List<SkillLevelLink> findByCountryIdAndLiveInd(String countryId, boolean b);

}
