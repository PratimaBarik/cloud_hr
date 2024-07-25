package com.app.employeePortal.sector.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.sector.entity.SectorDetails;

@Repository
public interface SectorDetailsRepository extends  JpaRepository<SectorDetails, String>  {
	
	@Query(value = "select a  from SectorDetails a  where a.orgId=:organizationId" )
	public List<SectorDetails> getSectorTypeByOrgId(@Param(value="organizationId") String organizationId);
	
	//@Query(value = "select a  from SectorDetails a  where a.userId=:userId and a.liveInd=true"  )
	public List<SectorDetails> findByUserIdAndLiveInd(String userId,boolean liveInd);
	
	@Query(value = "select a  from SectorDetails a  where a.sectorId=:sectorId and a.liveInd=true" )
	public SectorDetails getSectorDetailsBySectorId(@Param(value="sectorId")String sectorId);
	
	@Query(value = "select a  from SectorDetails a  where a.sectorId=:sectorId")
	public SectorDetails getSectorDetailsById(@Param(value="sectorId") String sectorId);

	@Query(value = "select a  from SectorDetails a  where a.sectorName=:name and a.liveInd=true" )
	public SectorDetails getSectorDetailsBySectorName(@Param(value="name")String name);
	
	@Query(value = "select a  from SectorDetails a  where a.sectorName=:name and a.orgId=:organizationId and a.liveInd=true" )
	public SectorDetails getSectorDetailsBySectorNameAndOrgId(@Param(value="name")String name, @Param(value="organizationId") String organizationId);

//    public List<SectorDetails> findBySectorNameContaining(String name);

    public List<SectorDetails> findBySectorName(String sectorName);

	public List<SectorDetails> findByOrgIdAndLiveInd(String orgId, boolean liveInd);


//    List<SectorDetails> findBySectorNameAndLiveInd(String sectorName, boolean b);
    
    List<SectorDetails> findBySectorNameAndLiveIndAndOrgId(String sectorName, boolean b, String orgId);

	public List<SectorDetails> findBySectorNameContainingAndOrgId(String name, String orgId);

	@Query(value = "select a  from SectorDetails a  where a.sectorName=:name and a.userId=:userId and a.liveInd=true" )
	public SectorDetails getSectorDetailsBySectorNameAndUserId(@Param(value="name")String name,@Param(value="userId")String userId);
}
