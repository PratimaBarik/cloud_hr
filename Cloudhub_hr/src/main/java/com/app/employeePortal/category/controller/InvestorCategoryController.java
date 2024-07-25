package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.InvestorCategoryMapper;
import com.app.employeePortal.category.service.InvestorCategoryService;
import com.app.employeePortal.sector.mapper.SectorMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class InvestorCategoryController {
	@Autowired
	InvestorCategoryService investorCategoryService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/investorCategory")
	public ResponseEntity<?> createInvestorCategory(@RequestBody InvestorCategoryMapper investorCategoryMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			Map map = new HashMap();
			investorCategoryMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorCategoryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(investorCategoryMapper.getName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = investorCategoryService
						.checkNameInInvestorCategoryByOrgLevel(investorCategoryMapper.getName(), orgId);
				if (b == true) {
					map.put("sectorInd", b);
					map.put("message", "InvestorCategory can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					InvestorCategoryMapper investorCategoryMapperid = investorCategoryService
							.saveInvestorCategory(investorCategoryMapper);
					return new ResponseEntity<>(investorCategoryMapperid, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide InvestorCategory !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorCategory/{investorCategoryId}")
	public ResponseEntity<?> geInvestorCategoryById(@PathVariable("investorCategoryId") String investorCategoryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			InvestorCategoryMapper investorCategoryMapper = investorCategoryService
					.getInvestorCategoryById(investorCategoryId);

			return new ResponseEntity<>(investorCategoryMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorCategory/All/{orgId}")
	public ResponseEntity<?> getInvestorCategoryByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorCategoryMapper> investorCategoryMapper = investorCategoryService
					.getInvestorCategoryByOrgId(orgId);

			return new ResponseEntity<>(investorCategoryMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investorCategory/{investorCategoryId}")

	public ResponseEntity<?> updateShipBy(@PathVariable("investorCategoryId") String investorCategoryId,
			@RequestBody InvestorCategoryMapper investorCategoryMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			investorCategoryMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorCategoryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(investorCategoryMapper.getName())) {
				Map map = new HashMap<>();
				boolean b = investorCategoryService
						.checkNameInInvestorCategoryByOrgLevel(investorCategoryMapper.getName(), orgId);
				if (b == true) {
					map.put("investorCategoryInd", b);
					map.put("message", "InvestorCategory can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<InvestorCategoryMapper>(
					investorCategoryService.updateInvestorCategory(investorCategoryId, investorCategoryMapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/investorCategory/{investorCategoryId}")
	public ResponseEntity<?> deleteInvestorCategory(@PathVariable("investorCategoryId") String investorCategoryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			investorCategoryService.deleteInvestorCategory(investorCategoryId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorCategory/search/{name}")
	public ResponseEntity<?> getInvestorCategoryByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<InvestorCategoryMapper> mapper = investorCategoryService.getInvestorCategoryByName(name);
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorCategory/count/{orgId}")
	public ResponseEntity<?> getInvestorCategoryCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorCategoryService.getInvestorCategoryCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
