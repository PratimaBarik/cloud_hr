package com.app.employeePortal.attendance.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.attendance.entity.ManufactureTimes;
@Repository
public interface ManufactureTimesRepository extends JpaRepository<ManufactureTimes, String>{

	List<ManufactureTimes> findByProductionProductIdAndUserIdAndActive(String productionProductId, String userId , boolean b);

	ManufactureTimes findByProductionProductIdAndUserIdAndActiveAndStartInd(String productionProductId, String userId, boolean b, boolean c);

	List<ManufactureTimes> findByProductionProductIdAndUserId(String productionProductId, String userId);
	
	List<ManufactureTimes> findByProductionProductIdAndActiveAndStartInd(String productionProductId, boolean b, boolean c);
	
	List<ManufactureTimes> findByUserIdAndCreateAtBetween(String userId, Date startDate, Date endDate);
	
	ManufactureTimes findByUserIdAndActiveAndStartInd(String userId, boolean b, boolean c);
	
//	@Query(value = "select a  from ManufactureTimes a  where a.userId=:userId AND a.createAt BETWEEN :startDate AND :endDate " )
//    List<ManufactureTimes> findByUserIdAndCreateAtBetween(@Param(value="userId") String userId, @Param(value ="startDate") Date startdate,@Param(value ="endDate") Date enddate);
	
//	ManufactureTimes findByPhoneId(String phoneId);
//
//	List<ManufactureTimes> getByPhoneId(String phn1);
//
//	ManufactureTimes findByPhoneIdAndPauseInd(String phoneId, boolean b);
//	
//	@Query("SELECT e FROM PhoneTimes e WHERE e.createAt = (SELECT MAX(e2.createAt) FROM PhoneTimes e2) and e.phoneId=:phoneId")
//	PhoneTimes getTheLatestPhoneTime(String phoneId);
//
//	PhoneTimes findFirstByIdOrderByCreateAtDesc(String id);

}
