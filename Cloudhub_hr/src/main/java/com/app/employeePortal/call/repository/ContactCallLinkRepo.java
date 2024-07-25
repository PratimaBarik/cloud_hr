package com.app.employeePortal.call.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.call.entity.ContactCallLink;

public interface ContactCallLinkRepo extends JpaRepository<ContactCallLink,String> {
    @Query("select a from ContactCallLink a where a.contactId=:contactId and a.liveInd=true")
    List<ContactCallLink> getCallListByContactId(@Param("contactId") String contactId, Pageable paging1);
    
    @Query("select a from ContactCallLink a where a.contactId=:contactId and a.liveInd=true")
    List<ContactCallLink> getCallListByContactIdAndLiveInd(@Param("contactId") String contactId);

    @Query("select a from ContactCallLink a where a.contactId=:contactId and a.callId=:callId and a.liveInd=true")
	ContactCallLink getByContactIdAndCallIdAndLiveInd(@Param("contactId") String contactId, @Param("callId") String callId);
}
