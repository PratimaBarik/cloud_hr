package com.app.employeePortal.event.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.EventDetails;

@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, String> {

	@Query(value = "select a  from EventDetails a  where a.event_id=:eventId and live_ind=true")
	public EventDetails getEventDetailsById(@Param(value = "eventId") String eventId);

	@Query(value = "select a  from EventDetails a  where a.event_id=:eventId")
	public EventDetails getEventDetailsByIdWithOutLiveInd(@Param(value = "eventId") String eventId);

	@Query(value = "select a  from EventDetails a  where a.organization_id=:orgId and live_ind=true")
	public List<EventDetails> getEventListByOrgId(@Param(value = "orgId") String orgId);

	@Query(value = "select a  from EventDetails a  where a.user_id=:userId and live_ind=true")
	public List<EventDetails> getEventListByUserId(@Param(value = "userId") String userId);

	@Query(value = "select a  from EventDetails a  where a.event_id=:event_id and a.start_date=:start_date and live_ind=true")
	public List<EventDetails> getEventListByEventIdAndStartDate(@Param(value = "event_id") String event_id,
			@Param(value = "start_date") Date start_date);

	// public List<EventDetails>findByEventIdAndStartDate(String eventId, new
	// SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01"));

	@Query(value = "select a  from EventDetails a  where a.event_id=:event_id and a.live_ind=true and a.start_date BETWEEN :startDate AND :endDate")
	List<EventDetails> getEventByEventIdAndDateRange(@Param(value = "event_id") String event_id,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);
	
	@Query(value = "select a  from EventDetails a  where a.event_id IN :eventIds and a.live_ind=true and a.start_date BETWEEN :startDate AND :endDate")
	List<EventDetails> getEventByEventIdsAndDateRange(@Param(value = "eventIds") List<String> eventIds,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from EventDetails a  where a.event_id=:eventId and live_ind=true")
	public List<EventDetails> getEventDetailssById(@Param(value = "eventId") String eventId);

	@Query(value = "select a  from EventDetails a  where a.user_id=:userId and a.live_ind=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<EventDetails> getEventListsByUserIdAndStartdateAndEndDateAndLiveInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from EventDetails a  where a.user_id=:userId and a.complitionInd=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<EventDetails> getEventListsByUserIdAndStartdateAndEndDateAndComplitionInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from EventDetails a  where a.user_id=:userId and a.complitionInd=false and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<EventDetails> getEventListByUserIdAndStartdateAndEndDateAndComplitionInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from EventDetails a  where a.event_type=:eventTypeId and a.live_ind=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<EventDetails> getEventListsByEventTypeIdAndStartdateAndEndDateAndLiveInd(
			@Param(value = "eventTypeId") String eventTypeId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select count(a) from EventDetails a  where a.event_id=:event_id and a.live_ind=true and a.start_date BETWEEN :startDate AND :endDate")
	public long countEventByEventIdAndDateRange(@Param(value = "event_id") String event_id, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);
	
	@Query(value = "select a  from EventDetails a  where a.event_id=:eventId and a.event_type=:eventTypeId and a.complitionInd=:complitionInd and a.live_ind=true" )
	public EventDetails getDataByEventAndEventTypeAndComplitionInd(String eventId, String eventTypeId, boolean complitionInd);

	@Query(value = "select a  from EventDetails a  where a.event_type=:eventTypeId and a.complitionInd=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<EventDetails> getEventListByEventTypeIdAndStartdateAndEndDateAndComplitionInd(@Param(value = "eventTypeId")String eventTypeId,
			@Param(value = "startDate") Date startDate,@Param(value = "endDate") Date endDate);

	@Query(value = "select exp  from EventDetails exp  where (exp.user_id = :userId OR exp.assignedTo = :userId) and exp.event_type=:eventTypeId and exp.live_ind=true")
	public List<EventDetails> getByEventTypeAndLiveIndAndUserId(@Param(value = "eventTypeId")String eventTypeId, @Param(value = "userId")String userId);

	@Query(value = "select a  from EventDetails a  where a.organization_id=:orgId and a.event_type=:eventTypeId and live_ind=true")
	public List<EventDetails> getByEventTypeAndOrgId(@Param(value = "eventTypeId")String eventTypeId, @Param(value = "orgId")String orgId);

	@Query("SELECT a FROM EventDetails a WHERE (a.user_id IN :userIds OR a.assignedTo IN :userIds) AND a.event_type=:eventTypeId AND a.live_ind = true ")
	public List<EventDetails> getByEventTypeAndLiveIndAndUserIds(@Param(value = "eventTypeId")String eventTypeId,@Param(value = "userIds")Set<String> userIds);

}
