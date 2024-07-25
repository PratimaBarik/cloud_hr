package com.app.employeePortal.Team.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Team.entity.Team;
@Repository
public interface TeamRepository extends JpaRepository<Team, String>{

	public Team findByTeamIdAndLiveInd(String teamId, boolean b);
	
	public List<Team> findByUserIdAndLiveInd(String userId, boolean b);

	public Team findByTeamLeadAndLiveInd(String teamLead, boolean liveInd);

    @Query(value = "select a  from Team a  where a.teamLead=:teamLead and a.liveInd=true ")
	public List<Team> getByTeamLead(@Param("teamLead")String teamLead);
	
}
