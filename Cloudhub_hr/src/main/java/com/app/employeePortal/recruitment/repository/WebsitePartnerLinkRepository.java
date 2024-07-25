package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.WebsitePartnerLink;

@Repository
public interface WebsitePartnerLinkRepository extends JpaRepository<WebsitePartnerLink, String>{

	@Query(value = "select a  from WebsitePartnerLink a  where a.url=:url" )
	WebsitePartnerLink getByUrl(@Param(value="url")String url);

	List<WebsitePartnerLink> findByOrgId(String orgId);
	
}
