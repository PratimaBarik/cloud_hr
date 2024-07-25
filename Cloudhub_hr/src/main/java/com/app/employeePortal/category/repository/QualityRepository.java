package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Quality;

@Repository
public interface QualityRepository extends JpaRepository<Quality, String> {

	Quality findByQualityIdAndLiveInd(String qualityId, boolean b);

	List<Quality> findByOrgIdAndLiveInd(String orgId, boolean b);

}
