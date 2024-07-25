package com.app.employeePortal.Team.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Team.entity.TeamMemberLink;
@Repository
public interface TeamMemberLinkRepo extends JpaRepository<TeamMemberLink, String>{

	List<TeamMemberLink> findByTeamId(String teamId);
	
	
}
