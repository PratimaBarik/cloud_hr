package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.task.entity.CustomerTaskLink;

public interface CustomerTaskRepo extends JpaRepository<CustomerTaskLink,String> {
   
	@Query("select a from CustomerTaskLink a where a.customerId=:customerId and a.liveInd=true")
    List<CustomerTaskLink> getTaskListByCustomerId(@Param("customerId")String customerId, Pageable page);
    
    @Query("select a from CustomerTaskLink a where a.customerId=:customerId and a.liveInd=true")
    List<CustomerTaskLink> getTaskListByCustomerIdAndLiveInd(@Param("customerId")String customerId);
    
    @Query("select a from CustomerTaskLink a where a.customerId=:customerId and a.taskId=:taskId and a.liveInd=true")
    CustomerTaskLink getTaskListByCustomerIdAndTaskIdAndLiveInd(@Param("customerId")String customerId, @Param("taskId")String taskId);
}
