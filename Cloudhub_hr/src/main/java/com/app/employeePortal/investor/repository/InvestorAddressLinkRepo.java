package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.customer.entity.CustomerAddressLink;
import com.app.employeePortal.investor.entity.InvestorAddressLink;

public interface InvestorAddressLinkRepo extends JpaRepository<InvestorAddressLink,String> {
    @Query(value = "select a  from InvestorAddressLink a  where a.investorId=:investorId" )
    List<InvestorAddressLink> getAddressListByInvestorId(@Param("investorId") String investorId);

    InvestorAddressLink findByInvestorId(String investorId);

    @Query(value = "select a  from InvestorAddressLink a  where a.addressId=:addressId" )
	public List<InvestorAddressLink> getByAddressId(@Param(value="addressId")String addressId);
}
