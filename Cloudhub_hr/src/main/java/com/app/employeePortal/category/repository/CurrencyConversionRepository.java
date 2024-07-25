package com.app.employeePortal.category.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.CurrencyConversion;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, String> {

//	List<CurrencyConversion> findByActive(boolean active);
//
//	List<CurrencyConversion> findByCurrency1AndActive(String currency1,boolean active);
//
//	CurrencyConversion findByCurrency1AndCurrency2(String currency1, String currency2);

	CurrencyConversion findByReportingCurrencyAndConversionCurrencyAndLiveInd(String reportingCurrency,
			String conversionCurrency,boolean b);

	CurrencyConversion findByCurrencyConversionId(String currencyConversionId);

	List<CurrencyConversion> findByOrgIdAndLiveInd(String orgId, boolean b);
	
}
