package com.app.employeePortal.Team.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.Team.entity.Team;
import com.app.employeePortal.Team.entity.TeamMemberLink;
import com.app.employeePortal.Team.mapper.TeamMapper;
import com.app.employeePortal.Team.mapper.TeamResponseMapper;
import com.app.employeePortal.Team.repository.TeamMemberLinkRepo;
import com.app.employeePortal.Team.repository.TeamRepository;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class TeamServiceImpl implements TeamService {
	
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	TeamMemberLinkRepo teamMemberLinkRepo;
	
	@Override
	public TeamResponseMapper saveTeam(TeamMapper teamMapper) {
		String teamId=null;
		TeamResponseMapper resultMapper= new TeamResponseMapper();
		if(teamMapper != null) {
			Team team = new Team();
			team.setCreationDate(new Date());
			team.setLiveInd(true);
			team.setTeamName(teamMapper.getTeamName());
			team.setOrgId(teamMapper.getOrgId());
			team.setUserId(teamMapper.getUserId());
			team.setTeamlogo(teamMapper.getTeamLogo());
			team.setTeamLead(teamMapper.getTeamLead());			
			teamId= teamRepository.save(team).getTeamId();
			
			//insert into teamMemberLink table
			
				if (null != teamMapper.getTeamMemberIds() && !teamMapper.getTeamMemberIds().isEmpty()) {
	                for (String employeeId : teamMapper.getTeamMemberIds()) {

				TeamMemberLink teamMemberLink=new TeamMemberLink();
				teamMemberLink.setTeamId(teamId);
				teamMemberLink.setTeamMemberId(employeeId);
				teamMemberLink.setCreationDate(new Date());
				teamMemberLinkRepo.save(teamMemberLink);
			}	                
		}
				resultMapper=getTeamByTeamId(teamId);
		} 
		return resultMapper; 
	}

	@Override
	public TeamResponseMapper getTeamByTeamId(String teamId) {
		
		Team team = teamRepository.findByTeamIdAndLiveInd(teamId,true);
		TeamResponseMapper teamMapper =new TeamResponseMapper();

		if (null != team) {
				
			teamMapper.setCreationDate(Utility.getISOFromDate(team.getCreationDate()));
			teamMapper.setLiveInd(team.isLiveInd());
			teamMapper.setTeamName(team.getTeamName());
			teamMapper.setTeamLogo(team.getTeamlogo());
			teamMapper.setOrgId(team.getOrgId());
			teamMapper.setUserId(team.getUserId());
			teamMapper.setTeamId(teamId);
			teamMapper.setTeamLead(employeeService.getEmployeeFullName(team.getTeamLead()));
			
			 List<EmployeeShortMapper> empList = new ArrayList<EmployeeShortMapper>();
			 List<TeamMemberLink> employeeList =teamMemberLinkRepo.findByTeamId(teamId);
		
			 if (null != employeeList && !employeeList.isEmpty()) {
                for (TeamMemberLink teamMemberLink : employeeList) {
                	EmployeeShortMapper mapper =employeeService.getEmployeeFullNameAndEmployeeId(teamMemberLink.getTeamMemberId());
                	
                	empList.add(mapper);
                }
                teamMapper.setTeamMemberIds(empList);
                }
		}
		
		return teamMapper;
		}
	@Override
	public List<TeamResponseMapper> getTeamByUserId(String userId) {
		
		List<TeamResponseMapper> resultMapper = new ArrayList<>();
		List<Team> list = teamRepository.findByUserIdAndLiveInd(userId,true);
		if (null != list) {
			resultMapper = list.stream().map(li->getTeamByTeamId(li.getTeamId()))
					.collect(Collectors.toList());
		}
		return resultMapper;
		}

	@Override
	public TeamResponseMapper updateTeam(String teamId, TeamMapper teamMapper) {

		Team team=teamRepository.findByTeamIdAndLiveInd(teamId,true);
		if(null!=team) {
			
			team.setTeamName(teamMapper.getTeamName());
			team.setTeamlogo(teamMapper.getTeamLogo());
			team.setTeamLead(teamMapper.getTeamLead());
			team.setOrgId(teamMapper.getOrgId());
			team.setUserId(teamMapper.getUserId());
			teamRepository.save(team);
			
			 if (null != teamMapper.getTeamMemberIds() && !teamMapper.getTeamMemberIds().isEmpty()) {
			List<TeamMemberLink> employeeList =teamMemberLinkRepo.findByTeamId(teamId);
			 if (null != employeeList && !employeeList.isEmpty()) {
               for (TeamMemberLink teamMemberLink : employeeList) {
            	   teamMemberLinkRepo.delete(teamMemberLink);
               }
               }
	                
			 for (String employeeId : teamMapper.getTeamMemberIds()) {
				TeamMemberLink teamMemberLink=new TeamMemberLink();
				teamMemberLink.setTeamId(teamId);
				teamMemberLink.setTeamMemberId(employeeId);
				teamMemberLink.setCreationDate(new Date());
				teamMemberLinkRepo.save(teamMemberLink);
			}	                
		}
			 
		}
		TeamResponseMapper resultMapper=getTeamByTeamId(teamId);
		return resultMapper;
	}

	@Override
	public void deleteTeam(String teamId) {
		
		if(null!=teamId) {
			Team team=teamRepository.findByTeamIdAndLiveInd(teamId,true);
			team.setLiveInd(false);
			teamRepository.save(team);
			
			List<TeamMemberLink> employeeList =teamMemberLinkRepo.findByTeamId(teamId);
			 if (null != employeeList && !employeeList.isEmpty()) {
              for (TeamMemberLink teamMemberLink : employeeList) {
            	  teamMemberLink.setLiveInd(false);
           	   teamMemberLinkRepo.save(teamMemberLink);
              }
              }
			
		}
	}

	@Override
	public List<TeamResponseMapper> getTeamByTeamLead(String teamLead) {
		List<TeamResponseMapper> resultMapper = new ArrayList<>();
		List<Team> list = teamRepository.getByTeamLead(teamLead);
		if (null != list) {
			resultMapper = list.stream().map(li->getTeamByTeamId(li.getTeamId()))
					.collect(Collectors.toList());
		}
		return resultMapper;
		
	}
}