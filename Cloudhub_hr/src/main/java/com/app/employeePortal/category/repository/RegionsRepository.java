package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Regions;

@Repository
public interface RegionsRepository extends JpaRepository<Regions, String> {

	public Regions findByRegionsId(String regionsId);

	public List<Regions> findByRegionsContainingAndLiveIndAndOrgId(String regions, boolean b,String orgId);

	public List<Regions> findByRegionsAndOrgIdAndLiveInd(String regions, String orgId, boolean b);

	public List<Regions> findByOrgIdAndLiveInd(String orgId, boolean b);


}
