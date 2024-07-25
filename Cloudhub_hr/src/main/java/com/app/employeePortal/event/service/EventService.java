package com.app.employeePortal.event.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.event.entity.EventType;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.event.mapper.EventViewMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;


public interface EventService {

	public EventViewMapper saveEventProcess(EventMapper eventMapper) throws IOException, TemplateException;

	public EventViewMapper getEventDetails(String eventId);

	public List<EventViewMapper> getEventDetailsByEmployeeIdPageWise(String employeeId, int pageNo, int pageSize);

	public List<EventViewMapper> getEventDetailsByOrganizationId(String organizationId);

	public EventViewMapper updateEventDetails(String eventId ,EventMapper eventMapper) throws IOException, TemplateException;

	public boolean delinkEvent(String employeeId, String eventId);

	public EventMapper saveEventType(EventMapper eventMapper);

	public List<EventMapper> getEventTypeByOrgId(String orgIdFromToken);

	public EventMapper updateEventType(String eventTypeId, EventMapper eventMapper);

	public EventMapper getEventById(String eventTypeId);

	public List<EventViewMapper> getEventListOfCandidate(String candidateId);

//    public List<EventMapper> getEventDetailsByName(String name);

	public void deleteEventTypeById(String eventTypeId);

	public HashMap getEventListsByUserIdStartdateAndEndDate(String userId, String startDate, String endDate);

	public Map<String, List<EventViewMapper>> getEventListsByEventTypeAndUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate);

	EventMapper getEventMapper(EventType eventType);

	List<EventViewMapper> getEventDetailsByEmployeeId(String employeeId);

	public Object getOpenEventCountByUserId(String userId, String organizationId);

	public Object getOpenEventListByUserId(String userId, String organizationId);

	List<EventViewMapper> getEventsByLeads(String leadsId,int pageNo);

	public List<EventViewMapper> getEventsByInvestorLeads(String investorLeadsId, int pageNo);

	public Map<String, List<EventViewMapper>> getCompletedEventListsByEventTypeAndUserIdAndStartdateAndEndDate(
			String userId, String startDate, String endDate);

	public List<Map<String, Integer>> getCountEventListsByEventTypeAndUserIdAndStartdateAndEndDate(
			String userId, String startDate, String endDate);

	public String createEventNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByEventId(String eventId);

//	public NotesMapper updateNoteDetails(NotesMapper notesMapper);

	public void deleteEventNotesById(String notesId);

	public ActivityMapper saveActivityEvent(EventMapper eventMapper);

	public HashMap getEventTypeCountByOrgId(String orgId);

	public ByteArrayInputStream exportEventTypeListToExcel(List<EventMapper> list);

	public boolean checkEventNameInEventTypeByOrgLevel(String eventType, String orgIdFromToken);
	
	public List<EventMapper> getEventDetailsByNameByOrgLevel(String eventType, String orgId);

	ActivityMapper updateActivityEventDetails(String eventId, EventMapper eventMapper)
			throws IOException, TemplateException;

	public List<EventViewMapper> getEventDetailsByNameByOrgId(String name, String orgId);

	public List<EventViewMapper> getEventDetailsByNameForTeam(String name, String userId, String orgId);

	public List<EventViewMapper> getEventDetailsByNameByUserId(String name, String userId, String orgId);

}
