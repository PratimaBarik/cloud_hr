package com.app.employeePortal.action.service;

import java.util.List;

import com.app.employeePortal.action.mapper.ActionMapper;

public interface ActionService {

	String saveAction(ActionMapper actionMapper);

	List<ActionMapper> getActionByOrgId(String orgId);

	Object getRecordOfToday(String userId);
	

}
