package com.app.employeePortal.category.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.ClubMapper;

import freemarker.template.TemplateException;


public interface ClubService {

	ClubMapper saveClub(ClubMapper mapper);

	List<ClubMapper> getClubByOrgId(String orgIdFromToken);

	ClubMapper updateClub(String clubId, ClubMapper mapper);

	void deleteClub(String clubId, String userIdFromToken);

	List<ClubMapper> getClubByClubName(String clubName, String orgIdFromToken);

	HashMap getClubCountByOrgId(String orgIdFromToken);

	ClubMapper updateInvToCusIndByClubId(String clubId, ClubMapper mapper) throws IOException, TemplateException;

	boolean checkNameInClub(String clubName, String orgIdFromToken);

	boolean checkNameInClubInUpdate(String clubName, String orgIdFromToken, String clubId);

}
