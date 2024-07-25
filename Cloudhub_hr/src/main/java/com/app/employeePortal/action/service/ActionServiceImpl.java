package com.app.employeePortal.action.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.action.entity.ActionDetails;
import com.app.employeePortal.action.mapper.ActionMapper;
import com.app.employeePortal.action.repository.ActionDetailsRepositoty;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class ActionServiceImpl implements ActionService {
	@Autowired
	ActionDetailsRepositoty actionDetailsRepository;
	
	
	@Override
	public String saveAction(ActionMapper actionMapper) {
		
		String actionId=null;
		if(actionMapper != null) {
			ActionDetails actionDetails = new ActionDetails();

			actionDetails.setActionName(actionMapper.getActionName());
			actionDetails.setOrganizationId(actionMapper.getOrganizationId());
			actionDetails.setUserId(actionMapper.getUserId());
			actionDetails.setCreationDate(new Date());
			

			actionId= actionDetailsRepository.save(actionDetails).getActionId();
			
				}
		return actionId;
		}


	@Override
	public List<ActionMapper> getActionByOrgId(String orgId) {
			return actionDetailsRepository.getActionByOrgId(orgId).stream().map(actionDetails1 -> {
				ActionMapper actionMapper1 = new ActionMapper();
				actionMapper1.setActionName(actionDetails1.getActionName());
				actionMapper1.setOrganizationId(actionDetails1.getOrganizationId());
				actionMapper1.setUserId(actionDetails1.getUserId());
				actionMapper1.setActionId(actionDetails1.getActionId());
				actionMapper1.setCreationDate(Utility.getISOFromDate(actionDetails1.getCreationDate()));
				return actionMapper1;
			}).collect(Collectors.toList());
	}


	@Override
	public Object getRecordOfToday(String userId) {


		// Millseconds in a day
		final long ONE_DAY_MILLI_SECONDS = (24 * 60 * 60 * 1000) - 1;

		// date format
		LocalDateTime now = LocalDateTime.now();
		Date currDate = new GregorianCalendar(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth()).getTime();
		long nextDayMilliSeconds = currDate.getTime() + ONE_DAY_MILLI_SECONDS;
		Date nextDate = new Date(nextDayMilliSeconds);
		HashMap map = new HashMap();

		List<ActionDetails> actionList = actionDetailsRepository
				.findByUserIdAndCreationDateBetween(userId, currDate, nextDate);
		System.out.println("Action size" + actionList.size());
		map.put("Action No", actionList.size());

		/*List<ActionDetails> actionList = actionDetailsRepository
				.findByUserIdAndCreationDate(userId, currDate, nextDate);
		System.out.println("Action size" + actionList.size());
		map.put("Action No", actionList.size());
		*/
		return map;
			
	}
	
}
