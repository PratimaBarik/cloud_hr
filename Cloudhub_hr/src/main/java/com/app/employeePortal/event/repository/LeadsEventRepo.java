package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.event.entity.LeadsEventLink;

public interface LeadsEventRepo extends JpaRepository<LeadsEventLink,String> {
    @Query(value = "select a  from LeadsEventLink a  where a.leadsId=:leadsId and live_ind=true" )
    Page<LeadsEventLink> getByLeadsId(@Param(value="leadsId")String leadsId, Pageable paging);
    
    @Query(value = "select a  from LeadsEventLink a  where a.leadsId=:leadsId and live_ind=true" )
    List<LeadsEventLink> getByLeadsIdAndLiveInd(@Param(value="leadsId")String leadsId);
    
    @Query("select a from LeadsEventLink a where a.leadsId=:leadsId and a.eventId=:eventId and a.liveInd=true")
    LeadsEventLink getByLeadsIdAndEventIdAndLiveInd(@Param("leadsId")String leadsId, @Param("eventId")String eventId);

}
