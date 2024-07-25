package com.app.employeePortal.support.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.support.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	public List<Product> findByProductFullNameContaining(String name);

	public List<Product> findByActive(boolean active);

	public List<Product> findByActiveAndPublishInd(boolean active, boolean publishInd);

	public Product findByIdAndActive(String productId, boolean active);

	@Query(value = "select a  from Product a where a.active=true")
	public Page<Product> getProductDetails(Pageable paging);

}
