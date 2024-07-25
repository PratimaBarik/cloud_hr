package com.app.employeePortal.category.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.CurrencyConversion;
import com.app.employeePortal.category.mapper.CurrencyConversionRequestMapper;
import com.app.employeePortal.category.mapper.CurrencyConversionResponseMapper;
import com.app.employeePortal.category.repository.CurrencyConversionRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class CurrencyConversionServiceImpl implements CurrencyConversionService {

	@Autowired
	CurrencyConversionRepository currencyConversionRepository;
	@Autowired
	EmployeeService employeeService;

	@Override
	public CurrencyConversionResponseMapper SaveCurrencyConversion(CurrencyConversionRequestMapper requestMapper) {

		String id = null;
		CurrencyConversion currencyConversion = currencyConversionRepository.
				findByReportingCurrencyAndConversionCurrencyAndLiveInd(
						requestMapper.getReportingCurrency(),requestMapper.getConversionCurrency(), true);
		if(null!=currencyConversion) {
			currencyConversion.setLiveInd(false);
			currencyConversionRepository.save(currencyConversion);
			
			CurrencyConversion currencyConversion1 = new CurrencyConversion();
			currencyConversion1.setConversionCurrency(requestMapper.getConversionCurrency());
			currencyConversion1.setConversionFactor(requestMapper.getConversionFactor());
			currencyConversion1.setReportingCurrency(requestMapper.getReportingCurrency());
			currencyConversion1.setReportingFactor(requestMapper.getReportingFactor());
			currencyConversion1.setOrgId(requestMapper.getOrgId());
			currencyConversion1.setUserId(requestMapper.getUserId());
			currencyConversion1.setCreationDate(new Date());
			currencyConversion1.setLiveInd(true);
			id = currencyConversionRepository.save(currencyConversion1).getCurrencyConversionId();
			
		}else {
			CurrencyConversion currencyConversion1 = new CurrencyConversion();
			currencyConversion1.setConversionCurrency(requestMapper.getConversionCurrency());
			currencyConversion1.setConversionFactor(requestMapper.getConversionFactor());
			currencyConversion1.setReportingCurrency(requestMapper.getReportingCurrency());
			currencyConversion1.setReportingFactor(requestMapper.getReportingFactor());
			currencyConversion1.setOrgId(requestMapper.getOrgId());
			currencyConversion1.setUserId(requestMapper.getUserId());
			currencyConversion1.setCreationDate(new Date());
			currencyConversion1.setLiveInd(true);
			id = currencyConversionRepository.save(currencyConversion1).getCurrencyConversionId();
		}
		return getCurrencyConversionById(id);
	}

	@Override
	public CurrencyConversionResponseMapper getCurrencyConversionById(String currencyConversionId) {

		CurrencyConversion currencyConversion = currencyConversionRepository
				.findByCurrencyConversionId(currencyConversionId);
		CurrencyConversionResponseMapper resultMapper = new CurrencyConversionResponseMapper();

		if (null != currencyConversion) {

			resultMapper.setCurrencyConversionId(currencyConversion.getCurrencyConversionId());
			resultMapper.setConversionCurrency(currencyConversion.getConversionCurrency());
			resultMapper.setConversionFactor(currencyConversion.getConversionFactor());
			resultMapper.setReportingCurrency(currencyConversion.getReportingCurrency());
			resultMapper.setReportingFactor(currencyConversion.getReportingFactor());
			resultMapper.setOrgId(currencyConversion.getOrgId());
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(currencyConversion.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(currencyConversion.getCreationDate()));
			resultMapper.setCreationDate(Utility.getISOFromDate(currencyConversion.getCreationDate()));
			resultMapper.setUserId(currencyConversion.getUserId());
			
		}
		return resultMapper;
	}

	@Override
	public List<CurrencyConversionResponseMapper> getCurrencyConversionByOrgId(String orgId) {

		List<CurrencyConversionResponseMapper> resultMapper = new ArrayList<>();
		List<CurrencyConversion> list = currencyConversionRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			list.stream().map(li -> {
				CurrencyConversionResponseMapper mapper = getCurrencyConversionByCurrencyConversionId(
						li.getCurrencyConversionId());
				if(null!=mapper) {
					resultMapper.add(mapper);
				}
				return mapper;
		}).collect(Collectors.toList());
		}
		
		resultMapper.stream()
		.sorted((p1, p2) -> p1.getCreationDate().compareTo(p2.getCreationDate())).collect(Collectors.toList());

		resultMapper.get(0).setUpdationDate(resultMapper.get(0).getCreationDate());
		resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(resultMapper.get(0).getUserId()));
		
		return resultMapper;
	}
	
	public CurrencyConversionResponseMapper getCurrencyConversionByCurrencyConversionId(String currencyConversionId) {

		CurrencyConversion currencyConversion = currencyConversionRepository
				.findByCurrencyConversionId(currencyConversionId);
		CurrencyConversionResponseMapper resultMapper = new CurrencyConversionResponseMapper();

		if (null != currencyConversion) {

			resultMapper.setCurrencyConversionId(currencyConversion.getCurrencyConversionId());
			resultMapper.setConversionCurrency(currencyConversion.getConversionCurrency());
			resultMapper.setConversionFactor(currencyConversion.getConversionFactor());
			resultMapper.setReportingCurrency(currencyConversion.getReportingCurrency());
			resultMapper.setReportingFactor(currencyConversion.getReportingFactor());
			resultMapper.setOrgId(currencyConversion.getOrgId());
			//resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(currencyConversion.getUserId()));
			resultMapper.setCreationDate(Utility.getISOFromDate(currencyConversion.getCreationDate()));
			//resultMapper.setUpdationDate(Utility.getISOFromDate(currencyConversion.getCreationDate()));
			resultMapper.setUserId(currencyConversion.getUserId());
			
		}
		return resultMapper;
	}

//	@Override
//	public CurrencyConversionResponseMapper updateCurrencyConversion(String currencyConversionId,
//			CurrencyConversionRequestMapper requestMapper) {
//		CurrencyConversion currencyConversion = currencyConversionRepository
//				.findByCurrencyConversionId(currencyConversionId);
//		if (null != currencyConversion) {
//
//			performanceManagement.setCreationDate(new Date());
//			performanceManagement.setLiveInd(requestMapper.isLiveInd());
//			performanceManagement.setKpi(requestMapper.getKpi());
//			performanceManagement.setDepartmentId(requestMapper.getDepartmentId());
//			performanceManagement.setFrequency(requestMapper.getFrequency());
//			performanceManagement.setOrgId(requestMapper.getOrgId());
//			performanceManagement.setUpdatedBy(requestMapper.getUserId());
//			performanceManagement.setUpdationDate(new Date());
//			performanceManagement.setUserId(requestMapper.getUserId());
//			performanceManagementRepository.save(performanceManagement);
//		}
//		PerformanceMgmtRespMapper resultMapper = getPerformanceManagementById(performanceManagementId);
//		return resultMapper;
//	}
//
//	@Override
//	public void deletePerformanceManagement(String PerformanceManagementId, String userId) {
//
//		if (null != PerformanceManagementId) {
//			PerformanceManagement PerformanceManagement = performanceManagementRepository
//					.findByPerformanceManagementId(PerformanceManagementId);
//
//			PerformanceManagement.setUpdationDate(new Date());
//			PerformanceManagement.setUpdatedBy(userId);
//			PerformanceManagement.setLiveInd(false);
//			performanceManagementRepository.save(PerformanceManagement);
//		}
//	}

}