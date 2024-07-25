package com.app.employeePortal.registration.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.entity.Timezone;
import com.app.employeePortal.registration.mapper.CountryMapper;
import com.app.employeePortal.registration.mapper.CurrencyDropDownMapper;
import com.app.employeePortal.registration.mapper.CurrencyMapper;
import com.app.employeePortal.registration.mapper.DailCodeMapper;

public interface CountryService {

	public List<CountryMapper> getAllMandatoryCountryList(String orgId);

	public List<Timezone> timezoneList();

	public List<CurrencyDropDownMapper> MandatoryCurrencyListForDropDown(String orgId);

//	public boolean ipAddressExists(String url);

	public List<Currency> getCurrenciesByUrl();

//	public List<CountryMapper> getCountriesByUrl();

	public List<CountryMapper> countryLists(String orgId);

	public CountryMapper mandatoryCountry(String countryId, CountryMapper country);

	List<CountryMapper> updateAllmandatoryCountry(boolean mandatoryInd, String userId, String orgId);

	public CountryMapper updateCountryForSalesInd(String countryId, CountryMapper country);

	public List<CurrencyMapper> getAllCurrencyListForCatagory(String orgId);

	public CurrencyMapper mandatoryCurrency(String currencyId, CurrencyMapper currencyMapper);

	public List<CurrencyMapper> updateAllmandatoryCurrency(boolean mandatoryInd, String userId, String orgId);

	public List<DailCodeMapper> getAllCountryDailCodeList(String orgId);

	public List<DailCodeMapper> getAllMandatoryCountryDailCodeList(String orgId);

	public List<CountryMapper> getCountryByCountryName(String countryName, String orgId);

	public List<CurrencyMapper> getCurrencyByCurrencyName(String currencyName, String orgId);

	public CurrencyMapper UpdateSalesCurrency(String currencyId, CurrencyMapper currencyMapper);

	public List<CurrencyDropDownMapper> SalesCurrencyListForDropDown(String orgId);

	public CurrencyMapper UpdateInvestorCurrency(String currencyId, CurrencyMapper currencyMapper);

	public List<CurrencyDropDownMapper> InvestorCurrencyListForDropDown(String orgId);

	public HashMap getAllCurrencyCount(String orgId);

	public HashMap getCountryCount(String orgId);

	public ByteArrayInputStream exportCountryListToExcel(List<CountryMapper> list);

	public ByteArrayInputStream exportCurrencyListToExcel(List<CurrencyMapper> list);

//	public void transferTo2ndOrganisation();

}
