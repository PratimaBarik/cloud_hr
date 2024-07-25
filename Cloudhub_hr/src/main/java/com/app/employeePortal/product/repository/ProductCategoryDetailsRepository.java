package com.app.employeePortal.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.product.entity.ProductCategoryDetails;
@Repository
public interface ProductCategoryDetailsRepository extends JpaRepository<ProductCategoryDetails, String>{

	
//	public List<ProductCategoryDetails> findByCategoryNameStartingWith(String categoryName);
//	
//	public ProductCategoryDetails  findByCategoryNameIgnoreCase(String categoryName);


}
