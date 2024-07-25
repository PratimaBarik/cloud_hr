package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.task.entity.InvestorTaskLink;

public interface InvestorTaskRepo extends JpaRepository<InvestorTaskLink,String> {
    
    @Query("select a from InvestorTaskLink a where a.investorId=:investorId and a.liveInd=true")
    List<InvestorTaskLink> getTaskListByInvestorIdAndLiveInd(@Param("investorId")String investorId);
    
    @Query("select a from InvestorTaskLink a where a.investorId=:investorId and a.taskId=:taskId and a.liveInd=true")
    InvestorTaskLink getTaskListByInvestorIdAndTaskIdAndLiveInd(@Param("investorId")String investorId, @Param("taskId")String taskId);

}
