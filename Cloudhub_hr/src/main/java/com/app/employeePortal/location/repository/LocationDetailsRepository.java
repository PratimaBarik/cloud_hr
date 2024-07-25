package com.app.employeePortal.location.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.location.entity.LocationDetails;

@Repository
public interface LocationDetailsRepository extends JpaRepository<LocationDetails, String> {

	LocationDetails findByLocationDetailsIdAndActiveInd(String locationDetailsId, boolean b);
	
	@Query("select a from LocationDetails a where a.orgId=:orgId and a.activeInd=:b Order By a.creationDate Desc")
	List<LocationDetails> findByOrgIdAndActiveInd(@Param("orgId") String orgId,@Param("b") boolean b);
	
	@Query("select a from LocationDetails a where a.orgId=:orgId and a.activeInd=true and a.productionInd=true and a.inventoryInd=true Order By a.creationDate Desc")
	List<LocationDetails> findByOrgIdAndActiveIndAndProductionIndAndInventoryInd(@Param("orgId") String orgId);

	List<LocationDetails> findByUserIdAndActiveInd(String userId, boolean b);
	
	List<LocationDetails> findByRegionsIdAndActiveInd(String regionsId, boolean b);

    Long countByOrgIdAndActiveInd(String orgId, boolean b);
    
	List<LocationDetails> findByOrgId(String orgId);
	
	@Query(value = "select a  from LocationDetails a  where a.userId=:userId and a.creationDate BETWEEN :startDate AND :endDate and a.activeInd=true" )
    public List<LocationDetails> getLocationByUserIdWithDateRange(@Param(value="userId")String userId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	
	List<LocationDetails> findByLocationNameAndRegionsIdAndOrgIdAndActiveInd(String locationName, String regionsId,String orgId, boolean b);

    LocationDetails findByLocationNameAndOrgIdAndActiveInd(String asString, String orgId, boolean b);
}
