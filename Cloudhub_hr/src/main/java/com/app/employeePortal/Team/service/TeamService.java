package com.app.employeePortal.Team.service;

import java.util.List;

import com.app.employeePortal.Team.mapper.TeamMapper;
import com.app.employeePortal.Team.mapper.TeamResponseMapper;

public interface TeamService {

	TeamResponseMapper saveTeam(TeamMapper teamMapper);

	List<TeamResponseMapper> getTeamByUserId(String userId);

	TeamResponseMapper getTeamByTeamId(String teamId);

	public TeamResponseMapper updateTeam(String teamId, TeamMapper sourceMapper);

	public void deleteTeam(String teamId);

	List<TeamResponseMapper> getTeamByTeamLead(String teamLead);

	

}
