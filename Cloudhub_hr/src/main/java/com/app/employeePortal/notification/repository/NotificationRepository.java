package com.app.employeePortal.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.notification.entity.NotificationDetails;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationDetails, String>{

//	@Query(value = "select a  from NotificationDetails a  where a.employee_id=:empId" )
//	public List<NotificationDetails> getNotiListByEmpId(@Param(value="empId") String empId);
	
	@Query(value = "select u  from NotificationDetails u  where u.assignedTo=:assignedTo  " )
	public List<NotificationDetails> getNotificationUser(@Param(value="assignedTo") String assignedTo );

	public NotificationDetails findByNotificationId(String notificationId);

	@Query(value = "select u  from NotificationDetails u  where u.assignedTo=:assignedTo and u.notificationDate BETWEEN :startDate AND :endDate " )
	public List<NotificationDetails> getTodayNotificationUser(@Param(value="assignedTo") String assignedTo ,
			@Param(value ="startDate") Date startDate, 
			@Param(value ="endDate") Date endDate);
	//public NotificationDetails getNotifications(String notificationId);
}
