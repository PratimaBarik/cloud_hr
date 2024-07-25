package com.app.employeePortal.mileage.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.mileage.entity.MileageDetails;

public interface MileageRepository extends JpaRepository<MileageDetails, String>{
	
	@Query("select m from MileageDetails m where m.user_id=:userId and m.live_ind=true")
	List<MileageDetails> getMileageListByUserId(@Param(value="userId")String userId);
	
	@Query("select m from MileageDetails m where m.organization_id=:orgId and m.live_ind=true")
	List<MileageDetails> getMileageListByOrganizationId(@Param(value="orgId")String orgId);

	@Query(value = "select m from MileageDetails m  where m.mileage_id=:mileageId and m.live_ind=true" )
	public MileageDetails getMileageDetailsById(@Param(value="mileageId") String mileageId);
	
	@Query(value = "select a  from MileageDetails a  where a.user_id=:userId and a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
    public List<MileageDetails> getMileageByUserIdWithDateRange(@Param(value="userId")String userId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	
	@Query(value = "select a  from MileageDetails a  where a.organization_id=:orgId and a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
    public List<MileageDetails> getMileagesByOrgIdWithDateRange(@Param(value="orgId")String orgId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
}
