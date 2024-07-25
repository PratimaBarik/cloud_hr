package com.app.employeePortal.registration.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.entity.Timezone;
import com.app.employeePortal.registration.mapper.CountryMapper;
import com.app.employeePortal.registration.mapper.CurrencyDropDownMapper;
import com.app.employeePortal.registration.mapper.CurrencyMapper;
import com.app.employeePortal.registration.mapper.DailCodeMapper;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.registration.repository.CurrencyRepository;
import com.app.employeePortal.registration.repository.TimezoneRepository;
import com.app.employeePortal.util.Utility;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	TimezoneRepository timezoneRepository;

	@Autowired
	WebsiteRepository websiteRepository;

	@Autowired
	EmployeeService employeeService;
	private String[] country_headings = { "Country_id", "country_alpha2_code", "country_alpha3_code", "country_currency_code",
			"country_currency_name", "country_dial_code", "country_name", "country_flag", "language", "capital",
			"latitude", "longitude" };
	private String[] currency_headings = { "Currency_id", "Currency_code","Currency_name"};

	@Override
	public List<CountryMapper> getAllMandatoryCountryList(String orgId) {

		List<CountryMapper> countryMapperList = new ArrayList<>();
		List<Country> list = countryRepository.findByMandatoryIndAndOrgId(true,orgId);
		if (null != list && !list.isEmpty()) {
			for (Country country : list) {
				CountryMapper countryMapper = new CountryMapper();

				countryMapper.setCapital(country.getCapital());
				countryMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				countryMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
				countryMapper.setCountry_currency_code(country.getCountry_currency_code());
				countryMapper.setCountry_currency_name(country.getCountry_currency_name());
				countryMapper.setCountry_dial_code(country.getCountry_dial_code());
				countryMapper.setCountry_flag(country.getCountry_flag());
				countryMapper.setCountry_id(country.getCountry_id());
				countryMapper.setCountry_name(country.getCountryName());
				countryMapper.setLanguage(country.getLanguage());
				countryMapper.setLatitude(country.getLatitude());
				countryMapper.setLongitude(country.getLongitude());
				countryMapper.setMandatoryInd(country.isMandatoryInd());
				countryMapper.setSalesInd(country.isSalesInd());

				countryMapperList.add(countryMapper);
			}
		}

		return countryMapperList;
	}

	@Override
	public List<Timezone> timezoneList() {
		List<Timezone> list = timezoneRepository.findAll();
		return list;
	}

	@Override
	public List<CurrencyDropDownMapper> MandatoryCurrencyListForDropDown(String orgId) {

		List<CurrencyDropDownMapper> resultList = new ArrayList<>();
		List<Currency> list = currencyRepository.findByMandatoryIndAndOrgId(true,orgId);
		if (null != list && !list.isEmpty()) {
			for (Currency currency : list) {
				CurrencyDropDownMapper currencyMapper = new CurrencyDropDownMapper();

				currencyMapper.setCurrency_code(currency.getCurrency_code());
				currencyMapper.setCurrency_id(currency.getCurrency_id());
				currencyMapper.setCurrency_name(currency.getCurrencyName());
				currencyMapper.setMandatoryInd(currency.isMandatoryInd());
				resultList.add(currencyMapper);
			}
		}
		return resultList;
	}

//	@Override
//	public boolean ipAddressExists(String url) {
//		Website web = websiteRepository.getByUrl(url);
//		if (null != web) {
//			System.out.println("web>:>:::>::::::>>" + web.toString());
//			return true;
//		}
//		return false;
//	}

	@Override
	public List<Currency> getCurrenciesByUrl() {
		List<Currency> list = currencyRepository.findAll();
		return list;
	}

	

	@Override
	public List<CountryMapper> countryLists(String orgId) {
		List<CountryMapper> countryMapperList = new ArrayList<>();
		List<Country> list = countryRepository.findByOrgId(orgId);
		System.out.println("Size=======i ====" + list.size());
		if (null != list && !list.isEmpty()) {
			for (Country country : list) {
				CountryMapper countryMapper = new CountryMapper();
				System.out.println("Country name=======inside ====for" + country.getCountryName());
				countryMapper.setCapital(country.getCapital());
				countryMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				countryMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
				countryMapper.setCountry_currency_code(country.getCountry_currency_code());
				countryMapper.setCountry_currency_name(country.getCountry_currency_name());
				countryMapper.setCountry_dial_code(country.getCountry_dial_code());
				countryMapper.setCountry_flag(country.getCountry_flag());
				countryMapper.setCountry_id(country.getCountry_id());
				countryMapper.setCountry_name(country.getCountryName());
				countryMapper.setLanguage(country.getLanguage());
				countryMapper.setLatitude(country.getLatitude());
				countryMapper.setLongitude(country.getLongitude());
				countryMapper.setMandatoryInd(country.isMandatoryInd());
				countryMapper.setSalesInd(country.isSalesInd());

				countryMapperList.add(countryMapper);
			}

		}

		List<Country> list1 = countryRepository.findByOrgId(orgId);
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			countryMapperList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			countryMapperList.get(0).setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			System.out.println("Country name=======" + countryMapperList.get(0).getCountry_name());
			System.out.println("name=======" + countryMapperList.get(0).getName());
		}
		System.out.println("Country name=======out" + countryMapperList.get(0).getCountry_name());
		System.out.println("name=====out" + countryMapperList.get(0).getName());

		return countryMapperList;
	}

	@Override
	public CountryMapper mandatoryCountry(String countryId, CountryMapper country) {

		Country country1 = countryRepository.getByCountryId(countryId);
		if (null != country1) {
			country1.setMandatoryInd(country.isMandatoryInd());
			country1.setUpdatedBy(country.getUserId());
			country1.setUpdationDate(new Date());
			countryRepository.save(country1);
		}
		CountryMapper country2 = countryList(countryId);

		List<Country> list = countryRepository.findByOrgId(country.getOrgId());
		if (null != list && !list.isEmpty()) {
			Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			country2.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			country2.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
		}
		return country2;
	}

	@Override
	public List<CountryMapper> updateAllmandatoryCountry(boolean mandatoryInd, String userId, String orgId) {
		List<Country> countryList = countryRepository.findByOrgId(orgId);
		if (null != countryList && !countryList.isEmpty()) {
			for (Country country : countryList) {
				country.setMandatoryInd(mandatoryInd);
				country.setUpdatedBy(userId);
				country.setUpdationDate(new Date());
				countryRepository.save(country);
			}
		}
		List<CountryMapper> list = countryLists(orgId);
		return list;
	}

	public CountryMapper countryList(String id) {
		CountryMapper countryMapper = new CountryMapper();

		Country list = countryRepository.getById(id);
		if (null != list) {

			countryMapper.setCapital(list.getCapital());
			countryMapper.setCountry_alpha2_code(list.getCountry_alpha2_code());
			countryMapper.setCountry_alpha3_code(list.getCountry_alpha3_code());
			countryMapper.setCountry_currency_code(list.getCountry_currency_code());
			countryMapper.setCountry_currency_name(list.getCountry_currency_name());
			countryMapper.setCountry_dial_code(list.getCountry_dial_code());
			countryMapper.setCountry_flag(list.getCountry_flag());
			countryMapper.setCountry_id(list.getCountry_id());
			countryMapper.setCountry_name(list.getCountryName());
			countryMapper.setLanguage(list.getLanguage());
			countryMapper.setLatitude(list.getLatitude());
			countryMapper.setLongitude(list.getLongitude());
			countryMapper.setMandatoryInd(list.isMandatoryInd());

		}
		return countryMapper;
	}

	@Override
	public CountryMapper updateCountryForSalesInd(String countryId, CountryMapper country) {
		Country country1 = countryRepository.getByCountryId(countryId);
		if (null != country1) {
			country1.setSalesInd(country.isSalesInd());
			country1.setUpdatedBy(country.getUserId());
			country1.setUpdationDate(new Date());
			countryRepository.save(country1);
		}
		CountryMapper country2 = countryList(countryId);

		List<Country> list = countryRepository.findByOrgId(country.getOrgId());
		if (null != list && !list.isEmpty()) {
			Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			country2.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			country2.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
		}
		return country2;
	}

	@Override
	public List<CurrencyMapper> getAllCurrencyListForCatagory(String orgId) {
		List<CurrencyMapper> currencyMapperList = new ArrayList<>();
		List<Currency> list = currencyRepository.findByOrgId(orgId);
		System.out.println("Size=======i ====" + list.size());
		if (null != list && !list.isEmpty()) {
			for (Currency currency : list) {
				CurrencyMapper currencyMapper = currencyList(currency.getCurrency_id());
				if (null != currencyMapper) {
					currencyMapperList.add(currencyMapper);
				}
			}

		}

		List<Currency> list1 = currencyRepository.findByOrgId(orgId);
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			currencyMapperList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			currencyMapperList.get(0).setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			System.out.println("Currency name=======" + currencyMapperList.get(0).getCurrency_name());
			System.out.println("name=======" + currencyMapperList.get(0).getName());
		}
		System.out.println("Currency name=======out" + currencyMapperList.get(0).getCurrency_name());
		System.out.println("name=====out" + currencyMapperList.get(0).getName());

		return currencyMapperList;
	}

	public CurrencyMapper currencyList(String id) {
		CurrencyMapper currencyMapper = new CurrencyMapper();

		Currency currency = currencyRepository.getById(id);
		if (null != currency) {

			currencyMapper.setCurrency_code(currency.getCurrency_code());
			currencyMapper.setCurrency_id(currency.getCurrency_id());
			currencyMapper.setCurrency_name(currency.getCurrencyName());
			currencyMapper.setMandatoryInd(currency.isMandatoryInd());
			currencyMapper.setInvestorInd(currency.isInvestorInd());
			currencyMapper.setSalesInd(currency.isSalesInd());
			currencyMapper.setOrgId(currency.getOrgId());

		}
		return currencyMapper;
	}

	@Override
	public CurrencyMapper mandatoryCurrency(String currencyId, CurrencyMapper currencyMapper) {
		CurrencyMapper currencyMapper1 = new CurrencyMapper();
		Currency currency = currencyRepository.getByCurrencyId(currencyId);
		if (null != currency) {
			currency.setMandatoryInd(currencyMapper.isMandatoryInd());
			currency.setUpdatedBy(currencyMapper.getUserId());
			currency.setOrgId(currencyMapper.getOrgId());
			currency.setUpdationDate(new Date());
			currencyMapper1 = currencyList(currencyRepository.save(currency).getCurrency_id());
		}

		List<Currency> list = currencyRepository.findByOrgId(currencyMapper.getOrgId());
		if (null != list && !list.isEmpty()) {
			Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			currencyMapper1.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			currencyMapper1.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
		}
		return currencyMapper1;
	}

	@Override
	public List<CurrencyMapper> updateAllmandatoryCurrency(boolean mandatoryInd, String userId, String orgId) {
		List<Currency> currencyList = currencyRepository.findByOrgId(orgId);
		if (null != currencyList && !currencyList.isEmpty()) {
			for (Currency currency : currencyList) {
				currency.setMandatoryInd(mandatoryInd);
				currency.setUpdatedBy(userId);
				currency.setUpdationDate(new Date());
				currencyRepository.save(currency);
			}
		}
		List<CurrencyMapper> list = getAllCurrencyListForCatagory(orgId);
		return list;
	}
	
	@Override
	public List<DailCodeMapper> getAllCountryDailCodeList(String orgId) {
		List<DailCodeMapper> countryMapperList = new ArrayList<>();
		List<Country> list = countryRepository.findByOrgId(orgId);
		System.out.println("Size=======i ====" + list.size());
		if (null != list && !list.isEmpty()) {
			for (Country country : list) {
				DailCodeMapper countryMapper = new DailCodeMapper();
				System.out.println("Country name=======inside ====for" + country.getCountryName());

				countryMapper.setCountry_dial_code(country.getCountry_dial_code());

				countryMapperList.add(countryMapper);
			}
		}
		return countryMapperList;
	}

	@Override
	public List<DailCodeMapper> getAllMandatoryCountryDailCodeList(String orgId) {
		List<DailCodeMapper> countryMapperList = new ArrayList<>();
		List<Country> list = countryRepository.findByMandatoryIndAndOrgId(true,orgId);
		if (null != list && !list.isEmpty()) {
			for (Country country : list) {
				DailCodeMapper countryMapper = new DailCodeMapper();
				countryMapper.setCountry_dial_code(country.getCountry_dial_code());
				countryMapperList.add(countryMapper);
			}
		}
		return countryMapperList;
	}

	@Override
	public List<CountryMapper> getCountryByCountryName(String countryName, String orgId) {
		List<Country> list = countryRepository.findByCountryNameContainingAndOrgId(countryName, orgId);
		if (null != list && !list.isEmpty()) {
			return list.stream().map(country -> {
				CountryMapper countryMapper = countryList(country.getCountry_id());
				if (null != countryMapper) {
					return countryMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<CurrencyMapper> getCurrencyByCurrencyName(String currencyName, String orgId) {
		List<Currency> list = currencyRepository.findByCurrencyNameContainingAndOrgId(currencyName, orgId);
		if (null != list && !list.isEmpty()) {
			return list.stream().map(currency -> {
				CurrencyMapper currencyMapper = currencyList(currency.getCurrency_id());
				if (null != currencyMapper) {
					return currencyMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public CurrencyMapper UpdateSalesCurrency(String currencyId, CurrencyMapper currencyMapper) {
		CurrencyMapper currencyMapper1 = new CurrencyMapper();
		Currency currency = currencyRepository.getByCurrencyId(currencyId);
		if (null != currency) {
			currency.setSalesInd(currencyMapper.isSalesInd());
			currency.setUpdatedBy(currencyMapper.getUserId());
			currency.setOrgId(currencyMapper.getOrgId());
			currency.setUpdationDate(new Date());
			currencyMapper1 = currencyList(currencyRepository.save(currency).getCurrency_id());
		}

		List<Currency> list = currencyRepository.findByOrgId(currencyMapper.getOrgId());
		if (null != list && !list.isEmpty()) {
			Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			currencyMapper1.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			currencyMapper1.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
		}
		return currencyMapper1;
	}

	@Override
	public List<CurrencyDropDownMapper> SalesCurrencyListForDropDown(String orgId) {

		List<CurrencyDropDownMapper> resultList = new ArrayList<>();
		List<Currency> list = currencyRepository.findBySalesIndAndOrgId(true, orgId);
		if (null != list && !list.isEmpty()) {
			for (Currency currency : list) {
				CurrencyDropDownMapper currencyMapper = new CurrencyDropDownMapper();

				currencyMapper.setCurrency_code(currency.getCurrency_code());
				currencyMapper.setCurrency_id(currency.getCurrency_id());
				currencyMapper.setCurrency_name(currency.getCurrencyName());
				currencyMapper.setSalesInd(currency.isSalesInd());
				resultList.add(currencyMapper);
			}
		}
		return resultList;
	}

	@Override
	public CurrencyMapper UpdateInvestorCurrency(String currencyId, CurrencyMapper currencyMapper) {
		CurrencyMapper currencyMapper1 = new CurrencyMapper();
		Currency currency = currencyRepository.getByCurrencyId(currencyId);
		if (null != currency) {
			currency.setInvestorInd(currencyMapper.isInvestorInd());
			currency.setUpdatedBy(currencyMapper.getUserId());
			currency.setOrgId(currencyMapper.getOrgId());
			currency.setUpdationDate(new Date());
			currencyMapper1 = currencyList(currencyRepository.save(currency).getCurrency_id());
		}

		List<Currency> list = currencyRepository.findByOrgId(currencyMapper.getOrgId());
		if (null != list && !list.isEmpty()) {
			Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			currencyMapper1.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			currencyMapper1.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
		}
		return currencyMapper1;
	}

	@Override
	public List<CurrencyDropDownMapper> InvestorCurrencyListForDropDown(String orgId) {

		List<CurrencyDropDownMapper> resultList = new ArrayList<>();
		List<Currency> list = currencyRepository.findByinvestorIndAndOrgId(true, orgId);
		if (null != list && !list.isEmpty()) {
			for (Currency currency : list) {
				CurrencyDropDownMapper currencyMapper = new CurrencyDropDownMapper();

				currencyMapper.setCurrency_code(currency.getCurrency_code());
				currencyMapper.setCurrency_id(currency.getCurrency_id());
				currencyMapper.setCurrency_name(currency.getCurrencyName());
				currencyMapper.setInvestorInd(currency.isInvestorInd());
				resultList.add(currencyMapper);
			}
		}
		return resultList;
	}

	@Override
	public HashMap getAllCurrencyCount(String orgId) {
		HashMap map = new HashMap();
		List<Currency> list = currencyRepository.findByOrgId(orgId);
		map.put("CurrencyCount", list.size());
		return map;
	}

	@Override
	public HashMap getCountryCount(String orgId) {
		HashMap map = new HashMap();
		List<Country> list = countryRepository.findByOrgId(orgId);
		map.put("CountryCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportCountryListToExcel(List<CountryMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < country_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(country_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}
		System.out.println("list size===" + list.size());
		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (CountryMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getCountry_id());
				row.createCell(1).setCellValue(mapper.getCountry_alpha2_code());
				row.createCell(2).setCellValue(mapper.getCountry_alpha3_code());
				row.createCell(3).setCellValue(mapper.getCountry_currency_code());
				row.createCell(4).setCellValue(mapper.getCountry_currency_name());
				row.createCell(5).setCellValue(mapper.getCountry_dial_code());
				row.createCell(6).setCellValue(mapper.getCountry_name());
				row.createCell(7).setCellValue(mapper.getCountry_flag());
				row.createCell(8).setCellValue(mapper.getLanguage());
				row.createCell(9).setCellValue(mapper.getCapital());
				row.createCell(10).setCellValue(mapper.getLatitude());
				row.createCell(11).setCellValue(mapper.getLongitude());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < country_headings.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	@Override
	public ByteArrayInputStream exportCurrencyListToExcel(List<CurrencyMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < currency_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(currency_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (CurrencyMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getCurrency_id());
				row.createCell(1).setCellValue(mapper.getCurrency_code());
				row.createCell(2).setCellValue(mapper.getCurrency_name());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < currency_headings.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());

	}

//	@Override
//	public void transferTo2ndOrganisation() {
//		List<Currency> list = currencyRepository.findAll();
//		if(null != list && !list.isEmpty()) {
//			for (Currency currency : list) {
////				currencyRepository.delete(currency);
//				Currency db = new Currency();
//				String input = currency.getCurrency_id();
//				System.out.println("input=============="+input);
//				// Split the input string into alphabetic and numeric parts
//		        String alphaPart = input.replaceAll("[^A-Za-z]", "");
//		        System.out.println("alphaPart--------"+alphaPart);
//		        String numericPart = input.replaceAll("[^0-9]", "");
//		        System.out.println("numericPart---------"+numericPart);
//		        // Convert the numeric part to an integer and add 40
//		        int numericValue = Integer.parseInt(numericPart) + 41;
//		        System.out.println("numericValue=========="+numericValue);
//		        // Construct the new string
//		        String newNumericPart = String.format("%03d", numericValue); // Ensure numeric part is padded with leading zeros if needed
//		        
//		        System.out.println("newNumericPart=========="+newNumericPart);
//		        String result1 = alphaPart +newNumericPart;
//		        System.out.println("result1-----------"+result1);
//		        db.setCurrency_id(result1);
//				db.setCurrency_code(currency.getCurrency_code());
//				db.setCurrencyName(currency.getCurrencyName());
//				db.setInvestorInd(currency.isInvestorInd());
//				db.setMandatoryInd(currency.isMandatoryInd());
//				db.setSalesInd(currency.isSalesInd());
////				db.setUpdatedBy(currency.getUpdatedBy());
////				db.setOrgId(currency.getOrgId());
////				db.setUpdationDate(currency.getUpdationDate());
//				db.setOrgId("OIF3134409544482024");
//				db.setUpdatedBy("EMP16918052295222021");
//				db.setUpdationDate(new Date());
//				currencyRepository.save(db);
//				
//			}
//		}
//		
////		List<Country> list2 = countryRepository.findAll();
////		if (null != list2 && !list2.isEmpty()) {
////			for (Country country : list2) {
////				Country country2 =new Country();
////				String input = country.getCountry_id();
////				System.out.println("input=============="+input);
////				// Split the input string into alphabetic and numeric parts
////		        String alphaPart = input.replaceAll("[^A-Za-z]", "");
////		        System.out.println("alphaPart--------"+alphaPart);
////		        String numericPart = input.replaceAll("[^0-9]", "");
////		        System.out.println("numericPart---------"+numericPart);
////		        // Convert the numeric part to an integer and add 40
////		        int numericValue = Integer.parseInt(numericPart) + 247;
////		        System.out.println("numericValue=========="+numericValue);
////		        // Construct the new string
////		        String newNumericPart = String.format("%04d", numericValue); // Ensure numeric part is padded with leading zeros if needed
////		        
////		        System.out.println("newNumericPart=========="+newNumericPart);
////		        String result = alphaPart +newNumericPart;
////		        System.out.println("result-----------"+result);
////				country2.setCapital(country.getCapital());
////				country2.setCountry_alpha2_code(country.getCountry_alpha2_code());
////				country2.setCountry_alpha3_code(country.getCountry_alpha3_code());
////				country2.setCountry_currency_code(country.getCountry_currency_code());
////				country2.setCountry_currency_name(country.getCountry_currency_name());
////				country2.setCountry_dial_code(country.getCountry_dial_code());
////				country2.setCountry_flag(country.getCountry_flag());
////		        country2.setCountry_id(result);
////				country2.setCountryName(country.getCountryName());
////				country2.setLanguage(country.getLanguage());
////				country2.setLatitude(country.getLatitude());
////				country2.setLongitude(country.getLongitude());
////				country2.setMandatoryInd(country.isMandatoryInd());
////				country2.setSalesInd(country.isSalesInd());
////				country2.setOrgId("OIF3134409544482024");
////				country2.setUpdatedBy("EMP16918052295222021");
////				country2.setUpdationDate(new Date());
////				countryRepository.save(country2);
////			}
////		}
//	}

}
