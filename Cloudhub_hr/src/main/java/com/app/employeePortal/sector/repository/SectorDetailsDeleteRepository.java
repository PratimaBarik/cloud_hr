package com.app.employeePortal.sector.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.sector.entity.SectorDetailsDelete;

@Repository
public interface SectorDetailsDeleteRepository extends  JpaRepository<SectorDetailsDelete, String>  {

	List<SectorDetailsDelete> findByOrgId(String orgId);
	
	@Query(value = "select a  from SectorDetailsDelete a  where a.sectorId=:sectorId")
	public SectorDetailsDelete getSectorDetailsById(@Param(value="sectorId") String sectorId);

	
	
	
}
