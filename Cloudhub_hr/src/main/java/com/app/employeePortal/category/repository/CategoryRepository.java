package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

	public Category findByCategoryIdAndLiveInd(String CategoryId,boolean b);

	public List<Category> findByOrgIdAndLiveInd(String orgId, boolean b);

	public List<Category> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

	public List<Category> findByNameAndOrgIdAndLiveInd(String name, String orgId, boolean b);
}
