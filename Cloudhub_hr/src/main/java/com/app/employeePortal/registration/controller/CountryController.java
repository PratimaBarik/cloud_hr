package com.app.employeePortal.registration.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.registration.entity.Timezone;
import com.app.employeePortal.registration.mapper.CountryMapper;
import com.app.employeePortal.registration.mapper.CurrencyDropDownMapper;
import com.app.employeePortal.registration.mapper.CurrencyMapper;
import com.app.employeePortal.registration.mapper.DailCodeMapper;
import com.app.employeePortal.registration.service.CountryService;

@RestController
@CrossOrigin(maxAge = 3600)

public class CountryController {

	@Autowired
	CountryService countryService;
	@Autowired
	WebsiteRepository websiteRepository;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@GetMapping("/api/v1/countries")
	public ResponseEntity<?> getAllMandatoryCountryList(@RequestHeader("Authorization") String authorization,HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
		List<CountryMapper> countryList = countryService.getAllMandatoryCountryList(orgId);

		return new ResponseEntity<>(countryList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/timezones")
	public List getAllTimezoneyList() {

		List<Timezone> timezoneList = countryService.timezoneList();

		return timezoneList;
	}

	@GetMapping("/api/v1/currencies")
	public ResponseEntity<?> MandatoryCurrencyListForDropDown(@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
		String authToken = authorization.replace(TOKEN_PREFIX, "");
		String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
		List<CurrencyDropDownMapper> currencyList = countryService.MandatoryCurrencyListForDropDown(orgId);

		return new ResponseEntity<>(currencyList, HttpStatus.OK);
	}

	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/currencies/website")
	public ResponseEntity<?> getAllMandatoryCurrenciesToWebsite(
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {

		// List<DepartmentMapper> typeList = null;

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			List<CurrencyDropDownMapper> currenciesMappernew = countryService.MandatoryCurrencyListForDropDown(web.getOrgId());
			// Collections.sort(currenciesMappernew, ( m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(currenciesMappernew, HttpStatus.OK);

		} else {
			map.put("website", false);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/countries/website")
	public ResponseEntity<?> getAllCountriesToWebsite(@RequestParam(value = "url", required = true) String url,
			HttpServletRequest request) {

		// List<DepartmentMapper> typeList = null;

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			List<CountryMapper> currenciesMappernew = countryService.getAllMandatoryCountryList(web.getOrgId());			
			// Collections.sort(currenciesMappernew, ( m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(currenciesMappernew, HttpStatus.OK);

		} else {
			map.put("website", false);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/countries/list")
	public ResponseEntity<?> getAllCountryListForCatagory(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CountryMapper> countryList = countryService.countryLists(orgId);

			return new ResponseEntity<>(countryList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/countries/mandatory/{countryId}")
	public ResponseEntity<?> mandatoryCountry(@RequestBody CountryMapper country,
			@PathVariable("countryId") String countryId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			country.setUserId(userId);
			country.setOrgId(orgId);
			CountryMapper countryList = countryService.mandatoryCountry(countryId, country);

			return new ResponseEntity<>(countryList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/countries/mandatory/all/{mandatoryInd}")
	public ResponseEntity<?> updateAllmandatoryCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("mandatoryInd") boolean mandatoryInd, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CountryMapper> countryList = countryService.updateAllmandatoryCountry(mandatoryInd, userId, orgId);

			return new ResponseEntity<>(countryList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/countries/salesInd/{countryId}")
	public ResponseEntity<?> updateCountryForSalesInd(@RequestBody CountryMapper country,
			@PathVariable("countryId") String countryId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			country.setUserId(userId);
			country.setOrgId(orgId);
			CountryMapper countryList = countryService.updateCountryForSalesInd(countryId, country);

			return new ResponseEntity<>(countryList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/countries/currency/list")
	public ResponseEntity<?> getAllCurrencyListForCatagory(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CurrencyMapper> currencyList = countryService.getAllCurrencyListForCatagory(orgId);

			return new ResponseEntity<>(currencyList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/countries/currency/mandatory/{currencyId}")
	public ResponseEntity<?> mandatoryCurrency(@RequestBody CurrencyMapper currencyMapper,
			@PathVariable("currencyId") String currencyId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			currencyMapper.setUserId(userId);
			currencyMapper.setOrgId(orgId);
			CurrencyMapper currency = countryService.mandatoryCurrency(currencyId, currencyMapper);

			return new ResponseEntity<>(currency, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/countries/currency/mandatory/all/{mandatoryInd}")
	public ResponseEntity<?> updateAllmandatoryCurrency(@RequestHeader("Authorization") String authorization,
			@PathVariable("mandatoryInd") boolean mandatoryInd, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CurrencyMapper> currencyList = countryService.updateAllmandatoryCurrency(mandatoryInd, userId, orgId);

			return new ResponseEntity<>(currencyList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/countries/all/dail-code/list")
	public ResponseEntity<?> getAllCountryDailCodeList(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<DailCodeMapper> countryList = countryService.getAllCountryDailCodeList(orgId);

			return new ResponseEntity<>(countryList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/countries/all/mandatory/dail-code/list")
	public ResponseEntity<?> getAllMandatoryCountryDailCodeList(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<DailCodeMapper> countryList = countryService.getAllMandatoryCountryDailCodeList(orgId);

			return new ResponseEntity<>(countryList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/countries/search/{countryName}")
	public ResponseEntity<?> getCountryByCountryName(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request, @PathVariable("countryName") String countryName) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CountryMapper> countryList = countryService.getCountryByCountryName(countryName,orgId);
			if (null != countryList && !countryList.isEmpty()) {
				return new ResponseEntity<>(countryList, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/countries/currency/search/{currencyName}")
	public ResponseEntity<?> getCurrencyByCurrencyName(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request, @PathVariable("currencyName") String currencyName) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CurrencyMapper> currencyList = countryService.getCurrencyByCurrencyName(currencyName,orgId);
			if (null != currencyList && !currencyList.isEmpty()) {
				return new ResponseEntity<>(currencyList, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/countries/currency/sale/{currencyId}")
	public ResponseEntity<?> UpdateSalesCurrency(@RequestBody CurrencyMapper currencyMapper,
			@PathVariable("currencyId") String currencyId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			currencyMapper.setUserId(userId);
			currencyMapper.setOrgId(orgId);
			CurrencyMapper currency = countryService.UpdateSalesCurrency(currencyId, currencyMapper);

			return new ResponseEntity<>(currency, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/currencies/sales")
	public ResponseEntity<?> SalesCurrencyListForDropDown(@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
		List<CurrencyDropDownMapper> currencyList = countryService.SalesCurrencyListForDropDown(orgId);

		return new ResponseEntity<>(currencyList, HttpStatus.OK);
	}
	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/countries/currency/investor/{currencyId}")
	public ResponseEntity<?> UpdateInvestorCurrency(@RequestBody CurrencyMapper currencyMapper,
			@PathVariable("currencyId") String currencyId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			currencyMapper.setUserId(userId);
			currencyMapper.setOrgId(orgId);
			CurrencyMapper currency = countryService.UpdateInvestorCurrency(currencyId, currencyMapper);

			return new ResponseEntity<>(currency, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/currencies/investor")
	public ResponseEntity<?> InvestorCurrencyListForDropDown(@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
		List<CurrencyDropDownMapper> currencyList = countryService.InvestorCurrencyListForDropDown(orgId);

		return new ResponseEntity<>(currencyList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/countries/currency/count")
	public ResponseEntity<?> getAllCurrencyCount(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(countryService.getAllCurrencyCount(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/countries/country/count")
	public ResponseEntity<?> getCountryCount(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(countryService.getCountryCount(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@PostMapping("/api/v1/currency")
//	public ResponseEntity<?> transferTo2ndOrganisation(){
//
//		countryService.transferTo2ndOrganisation();
//		return new ResponseEntity<>("created Succesfully", HttpStatus.OK);
//
//	}

}
