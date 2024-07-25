package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.task.entity.LeadsTaskLink;

public interface LeadsTaskRepo extends JpaRepository<LeadsTaskLink,String> {
    @Query("select a from LeadsTaskLink a where a.leadsId=:leadsId and a.liveInd=true")
    Page<LeadsTaskLink> getTaskListByLeadsId(@Param("leadsId")String leadsId, Pageable page);
    
    @Query("select a from LeadsTaskLink a where a.leadsId=:leadsId and a.liveInd=true")
    List<LeadsTaskLink> getTaskListByLeadsIdAndLiveInd(@Param("leadsId")String leadsId);
    
    @Query("select a from LeadsTaskLink a where a.leadsId=:leadsId and a.taskId=:taskId and a.liveInd=true")
    LeadsTaskLink getTaskListByLeadsIdAndTaskIdAndLiveInd(@Param("leadsId")String leadsId, @Param("taskId")String taskId);
}
