package com.app.employeePortal.call.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.call.entity.CallDetails;

@Repository
public interface CallDetailsRepository extends JpaRepository<CallDetails, String> {

	@Query(value = "select a  from CallDetails a  where a.call_id=:callId and live_ind=true")
	public CallDetails getCallDetailsById(@Param(value = "callId") String callId);

	@Query(value = "select a  from CallDetails a  where a.call_id=:callId and live_ind=true")
	public CallDetails getCallDetailsByIdWithOutLiveInd(@Param(value = "callId") String callId);

	@Query(value = "select a  from CallDetails a  where a.organization_id=:orgId and live_ind=true")
	public List<CallDetails> getCallListByOrgId(@Param(value = "orgId") String orgId);

	@Query(value = "select a  from CallDetails a  where a.call_id=:callId and a.callType=:callType and live_ind=true ")
	public CallDetails getDonotCallDetailsById(@Param(value = "callId") String callId,
			@Param(value = "callType") String callType);

	@Query(value = "select a  from CallDetails a  where a.call_id=:call_id and live_ind=true and a.call_start_date BETWEEN :startDate AND :endDate")
	public List<CallDetails> getCallListByCallIdAndStartDate(@Param(value = "call_id") String call_id,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);
	
	@Query(value = "select a  from CallDetails a  where a.call_id IN :callIds and live_ind=true and a.call_start_date BETWEEN :startDate AND :endDate")
	public List<CallDetails> getCallListByCallIdsAndStartDate(@Param(value = "callIds") List<String> callIds,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from CallDetails a  where a.call_id=:call_id and a.call_start_date=:start_date and live_ind=true")
	public List<CallDetails> getCallListByCallIdAndStartDate(@Param(value = "call_id") String call_id,
			@Param(value = "start_date") Date start_date);

	public List<CallDetails> findByCallTypeContaining(String name);

	@Query(value = "select a  from CallDetails a  where a.callType=:callType")
	public List<CallDetails> getByCallType(@Param(value = "callType") String callType);

	@Query(value = "select a  from CallDetails a  where a.user_id=:userId and a.live_ind=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<CallDetails> getCallListsByUserIdAndStartdateAndEndDateAndLiveInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from CallDetails a  where a.user_id=:userId and a.complitionInd=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<CallDetails> getCallListsByUserIdAndStartdateAndEndDateAndComplitionInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from CallDetails a  where a.user_id=:userId and a.complitionInd=false and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<CallDetails> getCallListByUserIdAndStartdateAndEndDateAndComplitionInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from CallDetails a  where a.user_id=:userId and a.complitionInd=false and a.creation_date BETWEEN :startDate AND :endDate and a.callType=:callType")
	public List<CallDetails> getCallTypeListsByUserIdAndStartdateAndEndDateAndComplitionInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate,@Param(value ="callType") String callType);

	@Query(value = "select a  from CallDetails a  where a.organization_id=:orgId and a.callType=:name and live_ind=true")
	public List<CallDetails> getByOrgIdAndCallTypeAndLiveInd(@Param(value = "orgId") String orgId, @Param(value = "name")String name);

	@Query("SELECT a FROM CallDetails a WHERE (a.user_id IN :userIds OR a.assignedTo IN :userIds) AND a.callType=:name AND a.live_ind = true ")
	public List<CallDetails> getByUserIdsAndCallTypeAndLiveInd(@Param(value = "userIds")Set<String> userIds, @Param(value = "name")String name);

	@Query(value = "select exp  from CallDetails exp  where (exp.user_id = :userId OR exp.assignedTo = :userId) and exp.callType=:name and exp.live_ind=true")
	public List<CallDetails> getByUserIdAndCallTypeAndLiveInd(@Param(value = "userId")String userId, @Param(value = "name")String name);

}
