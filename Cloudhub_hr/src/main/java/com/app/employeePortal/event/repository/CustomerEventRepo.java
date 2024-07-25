package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.event.entity.CustomerEventLink;

public interface CustomerEventRepo extends JpaRepository<CustomerEventLink,String> {
    @Query(value = "select a  from CustomerEventLink a  where a.customerId=:customerId and live_ind=true" )
    List<CustomerEventLink> getByCustomerId(@Param(value="customerId")String customerId, Pageable paging);
    
    @Query(value = "select a  from CustomerEventLink a  where a.customerId=:customerId and liveInd=true " )
    List<CustomerEventLink> getByCustomerIdAndLiveInd(@Param(value="customerId")String customerId);
    
    @Query("select a from CustomerEventLink a where a.customerId=:customerId and a.eventId=:eventId and a.liveInd=true")
    CustomerEventLink getByCustomerIdAndEventIdAndLiveInd(@Param("customerId")String customerId, @Param("eventId")String eventId);
}
