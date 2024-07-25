package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Nav;
@Repository
public interface NavRepository extends JpaRepository<Nav, String>{

	Nav findByNavDetailId(int navDetailId);

	List<Nav> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<Nav> findByNavNameAndOrgIdAndLiveInd(String navName, String orgId, boolean b);
	
}
