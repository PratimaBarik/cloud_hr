package com.app.employeePortal.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.product.entity.ProductAttributeDetails;
@Repository
public interface ProductAttributeDetailsRepository extends JpaRepository<ProductAttributeDetails, String>{

//	public List<ProductAttributeDetails> findByAttributeNameStartingWith(String attributeName);
//	
//	public ProductAttributeDetails  findByAttributeNameIgnoreCase(String attributeName);

}
