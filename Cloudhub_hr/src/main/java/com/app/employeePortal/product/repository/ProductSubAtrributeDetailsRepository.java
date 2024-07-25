package com.app.employeePortal.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.product.entity.ProductSubAtrributeDetails;

@Repository
public interface ProductSubAtrributeDetailsRepository extends JpaRepository<ProductSubAtrributeDetails, String> {

//	public List<ProductSubAtrributeDetails> findBySubAttributeNameStartingWith(String subattributeName);
//
//	public ProductSubAtrributeDetails findBySubAttributeNameIgnoreCase(String subAttribute);


}
