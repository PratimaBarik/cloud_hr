package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerAddressLink;
@Repository
public interface CustomerAddressLinkRepository extends JpaRepository<CustomerAddressLink, String>{

	@Query(value = "select a  from CustomerAddressLink a  where a.customerId=:customerId" )
    public List<CustomerAddressLink> getAddressListByCustomerId(@Param(value="customerId")String customerId);

	public CustomerAddressLink findByCustomerId(String customerId);

	public CustomerAddressLink findByAddressId(String addressId);
	
	@Query(value = "select a  from CustomerAddressLink a  where a.addressId=:addressId" )
	public List<CustomerAddressLink> getByAddressId(@Param(value="addressId")String addressId);

}
