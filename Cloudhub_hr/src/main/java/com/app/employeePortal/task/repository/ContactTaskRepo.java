package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.task.entity.ContactTaskLink;

public interface ContactTaskRepo extends JpaRepository<ContactTaskLink,String> {
    @Query("select a from ContactTaskLink a where a.contactId=:contactId and a.liveInd=true")
    List<ContactTaskLink> getTaskListByContactId(@Param("contactId")String contactId, Pageable page);
    
    @Query("select a from ContactTaskLink a where a.contactId=:contactId and a.liveInd=true")
    List<ContactTaskLink> getTaskListByContactIdAndLiveInd(@Param("contactId")String contactId);
    
    @Query("select a from ContactTaskLink a where a.contactId=:contactId and a.taskId=:taskId and a.liveInd=true")
    ContactTaskLink getTaskListByContactIdAndTaskIdAndLiveInd(@Param("contactId")String contactId, @Param("taskId")String taskId);
}
