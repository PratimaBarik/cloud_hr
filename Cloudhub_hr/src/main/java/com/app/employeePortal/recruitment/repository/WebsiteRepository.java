package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.Website;
@Repository
public interface WebsiteRepository extends JpaRepository<Website, String>{


	List<Website> findByOrgId(String orgId);

	//Website findByUrl(String url);

	@Query(value = "select a  from Website a  where a.url=:url" )
	Website getByUrl(@Param(value="url")String url);
	
}
