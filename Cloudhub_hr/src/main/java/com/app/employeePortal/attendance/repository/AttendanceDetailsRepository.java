package com.app.employeePortal.attendance.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.attendance.entity.AttendanceDetails;

@Repository
public interface AttendanceDetailsRepository extends JpaRepository<AttendanceDetails, String> {

	@Query(value = "select a  from AttendanceDetails a  where  a.userId=:userId and a.startDate=:startDate and a.startInd=true")
	AttendanceDetails getDataBetweenDateAndUserId(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate);

	@Query(value = "select a  from AttendanceDetails a  where  a.userId=:userId and a.startDate BETWEEN :startDate1 AND :endDate1")
	List<AttendanceDetails> getBetweenDatesAndUserId(@Param(value = "userId") String userId,
			@Param(value = "startDate1") Date startDate1, @Param(value = "endDate1") Date endDate1);

	@Query(value = "select a  from AttendanceDetails a  where  a.userId=:userId and  a.creationDate BETWEEN :startDate AND :endDate ")
	List<AttendanceDetails> getcurrentAttendanceByUserId(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate,
			@Param(value ="endDate") Date endDate);
	
	@Query(value = "select a  from AttendanceDetails a  where  a.userId=:userId and a.location=:location and  a.creationDate BETWEEN :startDate AND :endDate ")
	List<AttendanceDetails> getcurrentAttendanceByUserIdAndLocation(@Param(value = "userId") String userId,@Param(value = "location") String location,
			@Param(value = "startDate") Date startDate,
			@Param(value ="endDate") Date endDate);

	List<AttendanceDetails> findByUserIdAndCreationDateBetween(String userId, Date date, Date date1);
	
}
