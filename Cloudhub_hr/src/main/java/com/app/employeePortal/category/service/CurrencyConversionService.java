package com.app.employeePortal.category.service;

import java.util.List;

import com.app.employeePortal.category.mapper.CurrencyConversionRequestMapper;
import com.app.employeePortal.category.mapper.CurrencyConversionResponseMapper;

public interface CurrencyConversionService {

	public CurrencyConversionResponseMapper SaveCurrencyConversion(CurrencyConversionRequestMapper requestMapper);

	public CurrencyConversionResponseMapper getCurrencyConversionById(String currencyConversionId);

	public List<CurrencyConversionResponseMapper> getCurrencyConversionByOrgId(String orgId);


//	public CurrencyConversionResponseMapper updateCurrencyConversion(String currencyConversionId,
//			CurrencyConversionRequestMapper requestMapper);
//
//	public void deletePerformanceManagement(String currencyConversionId, String userId);

	
	
}
