package com.app.employeePortal.address.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.address.entity.AddressDetails;

@Repository
public interface AddressRepository extends JpaRepository<AddressDetails, String>{
	
	@Query(value = "select a  from AddressDetails a  where a.addressId=:addressId and a.liveInd=true" )
    public AddressDetails getAddressDetailsByAddressId(@Param(value="addressId")String addressId);
	
    @Query(value = "select a  from AddressDetails a  where a.addressId=:addressId" )
    public AddressDetails getAddressDetailsByAddressIdWithOutLiveInd(@Param(value="addressId")String addressId);
    
	public List<AddressDetails> getAddressDetailsByCountry(String country);
	
}

	
