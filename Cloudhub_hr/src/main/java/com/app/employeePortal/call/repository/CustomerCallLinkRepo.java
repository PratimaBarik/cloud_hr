package com.app.employeePortal.call.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.call.entity.CustomerCallLink;

public interface CustomerCallLinkRepo extends JpaRepository<CustomerCallLink,String> {
    @Query("select a from CustomerCallLink a where a.customerId=:customerId and a.liveInd=true")
    List<CustomerCallLink> getCallListByCustomerId(@Param("customerId") String customerId, Pageable paging1);
    
    @Query("select a from CustomerCallLink a where a.customerId=:customerId and a.liveInd=true")
    List<CustomerCallLink> getCallListByCustomerIdAndLiveInd(@Param("customerId") String customerId);
    
    @Query("select a from CustomerCallLink a where a.customerId=:customerId and a.callId=:callId and a.liveInd=true")
    CustomerCallLink getByCustomerIdAndCallIdAndLiveInd(@Param("customerId")String customerId, @Param("callId")String callId);
}
