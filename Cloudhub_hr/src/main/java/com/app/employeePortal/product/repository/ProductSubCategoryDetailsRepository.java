package com.app.employeePortal.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.product.entity.ProductSubCategoryDetails;
@Repository
public interface ProductSubCategoryDetailsRepository extends JpaRepository<ProductSubCategoryDetails, String>{

//	public List<ProductSubCategoryDetails> findBySubCategoryNameStartingWith(String subcategoryName);
//
//	public ProductSubCategoryDetails findBySubCategoryNameIgnoreCase(String category);
}
