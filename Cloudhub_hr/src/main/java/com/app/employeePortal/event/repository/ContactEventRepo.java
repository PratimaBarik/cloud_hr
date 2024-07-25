package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.event.entity.ContactEventLink;

public interface ContactEventRepo extends JpaRepository<ContactEventLink,String> {
    @Query(value = "select a  from ContactEventLink a  where a.contactId=:contactId and live_ind=true" )
    List<ContactEventLink> getByContactId(@Param(value="contactId")String contactId, Pageable paging);
    
    @Query(value = "select a  from ContactEventLink a  where a.contactId=:contactId and live_ind=true" )
    List<ContactEventLink> getByContactIdAndLiveInd(@Param(value="contactId")String contactId);
    
    @Query("select a from ContactEventLink a where a.contactId=:contactId and a.eventId=:eventId and a.liveInd=true")
    ContactEventLink getByContactIdAndEventIdAndLiveInd(@Param("contactId")String contactId, @Param("eventId")String eventId);
}
