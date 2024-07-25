package com.app.employeePortal.customer.repository;

import com.app.employeePortal.customer.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign,String> {
    Campaign findByCustomerIdAndEventId(String customerId, String eventDetailsId);
}
