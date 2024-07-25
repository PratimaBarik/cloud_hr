package com.app.employeePortal.ruleEngine.service;

import java.util.List;

import com.app.employeePortal.ruleEngine.mapper.RecruitProMailMapper;
import com.app.employeePortal.ruleEngine.mapper.RecruitProNotificationMapper;
import com.app.employeePortal.ruleEngine.mapper.ReportSchedulingMapper;

public interface RuleEngineService {

	String saveRecruitProMailRuleLink(RecruitProMailMapper recruitProMailLink);

	RecruitProMailMapper recruitProMailMapper(String orgId);

	String saveRecruitProNotificationRuleLink(RecruitProNotificationMapper recruitProNotificationMapper);

	RecruitProNotificationMapper getRecruitProNotification(String orgId);

	void sendEmailAtEachStageUpdateForCandidate(String orgId, String opportunityId, String contactId, String stageId,
			String userId);

	String saveReportScheduling(ReportSchedulingMapper reportSchedulingMapper);

	List<ReportSchedulingMapper> getReportSchedulingListByOrgId(String departmentId, String orgId);
	
	public void getAllReport() throws Exception;

	void getDailyReport() throws Exception;

	void getMonthlyReport() throws Exception;

	void getQuarterlyReport() throws Exception;

	public void deleteReportSchedulingRule(String reportSchedulingId);

}
