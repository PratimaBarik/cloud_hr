package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.HourCalculateMapper;
import com.app.employeePortal.category.mapper.HourMapper;
import com.app.employeePortal.category.mapper.HourTaskMapper;
import com.app.employeePortal.category.mapper.InvoiceMapper;
import com.app.employeePortal.category.service.HourService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;

@RestController
@CrossOrigin(maxAge = 3600)

public class HourController {
	
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	HourService hourService;
	@Autowired
	WebsiteRepository websiteRepository;
	
	@PostMapping("/api/v1/hour/save")
	public ResponseEntity<?> createHour(@RequestBody HourMapper hourMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			//hourMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			hourMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			HourMapper hourId = hourService.saveHour(hourMapper);
			return new ResponseEntity<>(hourId, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/hour/save/{hourId}")
	public ResponseEntity<?> getHourByHourId(@PathVariable("hourId") String hourId,
		@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

		HourMapper hourMapper = hourService.getHourByHourId(hourId);
		
		return new ResponseEntity<>(hourMapper, HttpStatus.OK);
	}
	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/api/v1/hour/save/website")
	public ResponseEntity<?> createHourForWebsite(@RequestBody HourMapper hourMapper, @RequestParam(value = "url", required = true) String url
			,HttpServletRequest request){

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			hourMapper.setOrgId(web.getOrgId());
			hourMapper.setUserId(web.getUser_id());

			HourMapper hourId = hourService.saveHour(hourMapper);

			return new ResponseEntity<>(hourId, HttpStatus.OK);
			
		}else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}
	
	
	@GetMapping("/api/v1/hour/user/hour-details/{userId}")
	public ResponseEntity<?> getAllCandidateBillingsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			 if(type.equalsIgnoreCase("SalesWoner")) {
				 List<HourCalculateMapper> candidateList = hourService.getSalesBillableCandidateList(userId,startDate,endDate); 	
				Collections.sort(candidateList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(candidateList, HttpStatus.OK);
			
			}else if(type.equalsIgnoreCase("Management")) {
				List<HourCalculateMapper> candidateList = hourService.getManagementBillableCandidateList(userId,startDate,endDate); 	
			Collections.sort(candidateList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(candidateList, HttpStatus.OK);
			
			}else if(type.equalsIgnoreCase("RecruitWoner")) {
				List<HourCalculateMapper> candidateList = hourService.getRecruiterBillableCandidateList(userId,startDate,endDate); 	
			Collections.sort(candidateList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(candidateList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@GetMapping("/api/v1/hour/user/{userId}")
	public ResponseEntity<?> getAllHourListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				 List<HourMapper> hourList = hourService.getAllHourListByUserId(userId,startDate,endDate); 	
				Collections.sort(hourList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(hourList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/hour/user/table/website/{candidateId}")
	public ResponseEntity<?> getAllHourListByCandidateIdAndDateRangeForWebsite(@PathVariable("candidateId") String candidateId,
			@RequestParam(value = "url", required = true) String url,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			
				 List<HourMapper> hourList = hourService.getAllHourListByCandidateIdAndDateRangeForWebsite(candidateId,startDate,endDate); 	
				Collections.sort(hourList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(hourList, HttpStatus.OK);

		}else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@GetMapping("/api/v1/hour/user/planner/website/{candidateId}")
	public ResponseEntity<?> getAllHourListByCandidateIdForWebsite(@PathVariable("candidateId") String candidateId,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			
				 List<HourMapper> hourList = hourService.getAllHourListByCandidateIdForWebsite(candidateId); 	
				Collections.sort(hourList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(hourList, HttpStatus.OK);

		}else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@GetMapping("/api/v1/hour/project-manager/{userId}/{taskId}")
	public ResponseEntity<?> getAllHourListByProjectManagerId(@PathVariable("userId") String userId,@PathVariable("taskId") String taskId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				 List<HourTaskMapper> hourList = hourService.getAllHourListByProjectManagerId(userId,taskId);
				 if(null!=hourList && !hourList.isEmpty()) {
				Collections.sort(hourList, ( m1,  m2) -> m2.getPlannerStartDate().compareTo(m1.getPlannerStartDate()));
				return new ResponseEntity<>(hourList, HttpStatus.OK);
				 }else {
					 return new ResponseEntity<>(hourList, HttpStatus.OK);
				 }
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@PutMapping("/api/v1/hour/project-manager/approve/candidate")
	public ResponseEntity<?> approveHourByHourId(@RequestBody HourTaskMapper hourTaskMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				 HourTaskMapper hourList = hourService.approveHourByHourId(hourTaskMapper); 	
				
				return new ResponseEntity<>(hourList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/hour/candidate/task/{hourId}/website")
	public ResponseEntity<?> getHourDetailsByHourIdForCandidate(@PathVariable("hourId") String hourId,
			@RequestParam(value = "url", required = true) String url,HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			
			List<HourTaskMapper> hourList = hourService.getHourDetailsByHourIdForCandidate(hourId);
				
					 return new ResponseEntity<>(hourList, HttpStatus.OK);
			
		}else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@GetMapping("/api/v1/hour/candidate/total/complete-unit/{candidateId}/website")
	public ResponseEntity<?> getTotalCompletedUnitByCandidateIdAndDateRange(@PathVariable("candidateId") String candidateId,
			@RequestParam(value = "url", required = true) String url,@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			
			int total = hourService.getTotalCompletedUnitByCandidateIdAndDateRange(candidateId,startDate,endDate);
			map.put("totalCompletedUnit",total);
			return new ResponseEntity<>(map, HttpStatus.OK);
			
		}else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@GetMapping("/api/v1/hour/candidate/{candidateId}/{taskId}/website")
	public ResponseEntity<?> getAllHourListByCandidateIdAndTaskId(@PathVariable("candidateId") String candidateId,@PathVariable("taskId") String taskId,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			
				 List<HourTaskMapper> hourList = hourService.getAllHourListByCandidateIdAndTaskId(candidateId,taskId);
				 if(null!=hourList && !hourList.isEmpty()) {
				Collections.sort(hourList, ( m1,  m2) -> m2.getPlannerStartDate().compareTo(m1.getPlannerStartDate()));
				return new ResponseEntity<>(hourList, HttpStatus.OK);
				 }else {
					 return new ResponseEntity<>(hourList, HttpStatus.OK);
				 }
		}else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@GetMapping("/api/v1/hour/candidate/all/hour-list/{candidateId}/website")
	public ResponseEntity<?> getAllHourListByCandidateIdAndDateRange(@PathVariable("candidateId") String candidateId,
			@RequestParam(value = "url", required = true) String url,@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			
				 List<HourTaskMapper> hourList = hourService.getAllHourListByCandidateIdAndDateRange(candidateId,startDate,endDate);
				 if(null!=hourList && !hourList.isEmpty()) {
				Collections.sort(hourList, ( m1,  m2) -> m2.getPlannerStartDate().compareTo(m1.getPlannerStartDate()));
				return new ResponseEntity<>(hourList, HttpStatus.OK);
				 }else {
					 return new ResponseEntity<>(hourList, HttpStatus.OK);
				 }
		}else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@GetMapping("/api/v1/hour/candidate/hour-details/project/{candidateId}/{projectId}")
	public ResponseEntity<?> getCandidateTotalBillings(@PathVariable("candidateId") String candidateId,
			@PathVariable("projectId") String projectId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				 List<HourCalculateMapper> candidateList = hourService.getCandidateTotalBillings(candidateId,projectId,startDate,endDate); 	
				return new ResponseEntity<>(candidateList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/hour/candidate/billings/invoice/{customerId}/{projectId}/{month}/{year}")
	public ResponseEntity<?> getCandidatesTotalBillingsForInvoice(@PathVariable("customerId") String customerId,
			@PathVariable("projectId") String projectId,@PathVariable("month") String month,@PathVariable("year") String year,
			@RequestHeader("Authorization") String authorization,HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				 List<HourCalculateMapper> candidateList = hourService.getCandidatesTotalBillingsForInvoice(customerId,projectId,month,year); 	
				return new ResponseEntity<>(candidateList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/api/v1/hour/invoice/save")
	public ResponseEntity<?> createInvoice(@RequestBody InvoiceMapper invoiceMapper1,
			@RequestHeader("Authorization") String authorization) {

		List<InvoiceMapper> invoiceMapper = new ArrayList<>();
		if(null!=invoiceMapper1) {
			invoiceMapper.addAll(invoiceMapper1.getResult());
		}
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if(null!=invoiceMapper && !invoiceMapper.isEmpty()) {
				for (InvoiceMapper invoiceMapper2 : invoiceMapper) {
			invoiceMapper2.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			invoiceMapper2.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			}
			}
			List<InvoiceMapper> invoiceId = hourService.createInvoice(invoiceMapper);
			return new ResponseEntity<>(invoiceId, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	@GetMapping("/api/v1/hour/invoice/get/{invoiceId}")
	public ResponseEntity<?> getInvoiceByInvoiceId(@PathVariable("invoiceId") String invoiceId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			InvoiceMapper invoiceMapper = hourService.getInvoiceByInvoiceId(invoiceId);
		
			return new ResponseEntity<>(invoiceMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	@GetMapping("/api/v1/hour/invoice/get-All/organization/{OrgId}")
	public ResponseEntity<?> getInvoiceByOrgId(@PathVariable("OrgId") String OrgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvoiceMapper> invoiceMapper = hourService.getInvoiceByOrgId(OrgId);
			if(null!=invoiceMapper && !invoiceMapper.isEmpty()) {
			Collections.sort(invoiceMapper, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(invoiceMapper, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(invoiceMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	@GetMapping("/api/v1/hour/invoice/get-All/user/{userId}")
	public ResponseEntity<?> getInvoiceByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvoiceMapper> invoiceMapper = hourService.getInvoiceByUserId(userId);
			if(null!=invoiceMapper && !invoiceMapper.isEmpty()) {
			Collections.sort(invoiceMapper, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(invoiceMapper, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(invoiceMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	@GetMapping("/api/v1/hour/user/hour-details/search/{name}")
	public ResponseEntity<?> getAllCandidateBillingsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				 List<HourCalculateMapper> candidateList = hourService.getBillableCandidateListByName(name,startDate,endDate); 
				 if(null!=candidateList && !candidateList.isEmpty()) {
				Collections.sort(candidateList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(candidateList, HttpStatus.OK);
					}else {
						return new ResponseEntity<>(candidateList, HttpStatus.OK);
					}
	}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
}
