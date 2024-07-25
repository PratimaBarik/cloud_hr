package com.app.employeePortal.call.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.call.mapper.CallMapper;
import com.app.employeePortal.call.mapper.CallViewMapper;
import com.app.employeePortal.call.mapper.DonotCallMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

public interface CallService {

	public CallViewMapper saveCallProcess(CallMapper callMapper) throws IOException, TemplateException;

	public CallViewMapper getCallDetails(String callId);

	public List<CallViewMapper> getCallDetailsByEmployeeIdPageWise(String employeeId, int pageNo, int pageSize);

	public List<CallViewMapper> getCallDetailsByOrganizationId(String organizationId);

	public CallViewMapper updateCallDetails(String callId ,CallMapper callMapper) throws IOException, TemplateException;

	public boolean delinkCall(String employeeId, String callId);

	public List<CallViewMapper> getcallListOfACandidate(String candidateId);

	boolean doNotCallCandidate(CallMapper callMapper, String candidateId);

	DonotCallMapper getUpdatedDoNotCallDetail(String candidateId);

    public List<CallViewMapper> getCallDetailsByName(String name);

    public boolean checkCallNameInCallType(String callType);

	public HashMap getCallListsByUserIdStartdateAndEndDate(String userId, String startDate,
			String endDate);

	List<CallViewMapper> getCallDetailsByEmployeeId(String employeeId);


    List<CallViewMapper> getCallDetailsByLeadsId(String leadsId, int pageNo, int pageSize);

	public List<CallViewMapper> getCallDetailsByInvestorLeadsId(String investorLeadsId, int pageNo, int pageSize);

	public HashMap getCompletedCallsCountByUserIdAndStartdateAndEndDate(String userId, String startDate, String endDate);

	public List<CallViewMapper> getCompletedCallsListByUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate);

	public String saveCallNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByCallId(String callId);

//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper);

	public void deleteCallNotesById(String notesId);

	public List<CallViewMapper> getCompletedCallsTypeListByUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate, String callType);

	public HashMap getCompletedCallsTypeCountByUserIdAndStartdateAndEndDate(String userId,
			String startDate, String endDate, String callType);

	public ActivityMapper saveActivityCall(CallMapper callMapper)throws IOException, TemplateException;

	ActivityMapper updateActivityCallDetails(String callId, CallMapper callMapper)
			throws IOException, TemplateException;

	public List<CallViewMapper> getCallDetailsByNameByOrgLevel(String name, String orgId);

	public List<CallViewMapper> getCallDetailsByNameForTeam(String name, String userId);

	public List<CallViewMapper> getCallDetailsByNameByUserId(String name, String userId);
}
