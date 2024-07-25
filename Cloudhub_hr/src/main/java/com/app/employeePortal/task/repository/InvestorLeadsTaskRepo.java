package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.task.entity.InvestorLeadsTaskLink;

public interface InvestorLeadsTaskRepo extends JpaRepository<InvestorLeadsTaskLink,String> {

	
    @Query("select a from InvestorLeadsTaskLink a where a.investorLeadsId=:investorLeadsId and a.liveInd=true")
    Page<InvestorLeadsTaskLink> getTaskListByInvestorLeadsId(@Param("investorLeadsId")String investorLeadsId, Pageable page);
    
    @Query("select a from InvestorLeadsTaskLink a where a.investorLeadsId=:investorLeadsId and a.liveInd=true")
    List<InvestorLeadsTaskLink> getTaskListByInvestorLeadsIdAndLiveInd(@Param("investorLeadsId")String investorLeadsId);
    
    @Query("select a from InvestorLeadsTaskLink a where a.investorLeadsId=:investorLeadsId and a.taskId=:taskId and a.liveInd=true")
    InvestorLeadsTaskLink getTaskListByInvestorLeadsIdAndTaskIdAndLiveInd(@Param("investorLeadsId")String investorLeadsId, @Param("taskId")String taskId);
}
