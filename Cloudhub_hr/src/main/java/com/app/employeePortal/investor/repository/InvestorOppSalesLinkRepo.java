package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorOppSalesUserLink;

public interface InvestorOppSalesLinkRepo extends JpaRepository<InvestorOppSalesUserLink,String> {
    @Query(value = "select a  from InvestorOppSalesUserLink a  where a.invOpportunityId=:invOpportunityId" )
    List<InvestorOppSalesUserLink> getSalesUsersByOppId(@Param("invOpportunityId") String invOpportunityId);
    
    @Query(value = "select a  from InvestorOppSalesUserLink a  where a.employeeId=:userId and a.liveInd =true" )
    List<InvestorOppSalesUserLink> getSalesUserLinkByUserId(@Param("userId") String userId);
    
    @Query(value = "select a  from InvestorOppSalesUserLink a  where a.employeeId=:userId and a.liveInd =true" )
    Page<InvestorOppSalesUserLink> getSalesUserLinkByUserIdWithPagination(@Param("userId") String userId, Pageable paging);
}
