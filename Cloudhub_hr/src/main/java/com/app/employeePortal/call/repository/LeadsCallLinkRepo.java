package com.app.employeePortal.call.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.call.entity.LeadsCallLink;

public interface LeadsCallLinkRepo extends JpaRepository<LeadsCallLink,String> {
    @Query("select a from LeadsCallLink a where a.leadsId=:leadsId and a.liveInd=true")
    Page<LeadsCallLink> getCallListByLeadsId(@Param("leadsId") String leadsId, Pageable paging1);
    
    @Query("select a from LeadsCallLink a where a.leadsId=:leadsId and a.liveInd=true")
    List<LeadsCallLink> getCallListByLeadsIdAndLiveInd(@Param("leadsId") String leadsId);
    
    @Query("select a from LeadsCallLink a where a.leadsId=:leadsId and a.callId=:callId and a.liveInd=true")
    LeadsCallLink getByLeadsIdAndCallIdAndLiveInd(@Param("leadsId") String leadsId, @Param("callId") String callId);
}
