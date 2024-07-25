package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Industry;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, String> {

	public Industry findByIndustryIdAndLiveInd(String industryId,boolean b);

	public List<Industry> findByOrgIdAndLiveInd(String orgId, boolean b);

	public List<Industry> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

	public List<Industry> findByNameAndLiveIndAndOrgId(String name, boolean b, String orgId);
}
