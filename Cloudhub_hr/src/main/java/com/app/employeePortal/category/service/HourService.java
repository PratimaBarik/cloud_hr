package com.app.employeePortal.category.service;

import java.util.List;

import com.app.employeePortal.category.mapper.HourCalculateMapper;
import com.app.employeePortal.category.mapper.HourMapper;
import com.app.employeePortal.category.mapper.HourTaskMapper;
import com.app.employeePortal.category.mapper.InvoiceMapper;

public interface HourService {

	public HourMapper saveHour(HourMapper hourMapper);

	public HourMapper getHourByHourId(String hourId);

	public List<HourCalculateMapper> getSalesBillableCandidateList(String userId, String startDate, String endDate);

	public List<HourCalculateMapper> getManagementBillableCandidateList(String userId, String startDate, String endDate);

	public List<HourCalculateMapper> getRecruiterBillableCandidateList(String userId, String startDate, String endDate);

	public List<HourMapper> getAllHourListByUserId(String userId, String startDate, String endDate);

	public List<HourMapper> getAllHourListByCandidateIdAndDateRangeForWebsite(String candidateId, String startDate, String endDate);

	public List<HourMapper> getAllHourListByCandidateIdForWebsite(String candidateId);

	public List<HourTaskMapper> getAllHourListByProjectManagerId(String userId, String taskId);

	public HourTaskMapper approveHourByHourId(HourTaskMapper hourTaskMapper);

	public List<HourTaskMapper> getHourDetailsByHourIdForCandidate(String hourId);

	HourTaskMapper getHourDetailByHourIdForCandidate(String hourId);

	public int getTotalCompletedUnitByCandidateIdAndDateRange(String candidateId, String startDate, String endDate);

	public List<HourTaskMapper> getAllHourListByCandidateIdAndTaskId(String candidateId, String taskId);

	public List<HourTaskMapper> getAllHourListByCandidateIdAndDateRange(String candidateId, String startDate,
			String endDate);

	public List<HourCalculateMapper> getCandidateTotalBillings(String candidateId, String projectId, String startDate,
			String endDate);

	public List<HourCalculateMapper> getCandidatesTotalBillingsForInvoice(String customerId, String projectId,
			String month, String year);

	public List<InvoiceMapper> createInvoice(List<InvoiceMapper> invoiceMapper);

	public InvoiceMapper getInvoiceByInvoiceId(String invoiceId);

	public List<InvoiceMapper> getInvoiceByOrgId(String orgId);

	public List<InvoiceMapper> getInvoiceByUserId(String userId);

	public List<HourCalculateMapper> getBillableCandidateListByName(String name, String startDate, String endDate);
	
}
