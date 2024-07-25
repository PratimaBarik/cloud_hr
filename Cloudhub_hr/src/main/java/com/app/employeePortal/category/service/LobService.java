package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.app.employeePortal.category.mapper.LobMapper;

public interface LobService {

	LobMapper saveLob(LobMapper lobMapper);

	List<LobMapper> getLobMapperByOrgId(String orgId);

	LobMapper updateLob(String lobId, LobMapper lobMapper);

	void deleteLob(String lobId, String userId);

	Object getLobCountByOrgId(String orgId);

	ByteArrayInputStream exportLobListToExcel(List<LobMapper> list);

	boolean checkNameInLobByOrgLevel(String name, String orgId);

	List<LobMapper> getLobByNameByOrgLevel(String name, String orgId);
	
}
