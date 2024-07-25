package com.app.employeePortal.location.controller;
import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.HashMap;
import java.util.List;

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
import com.app.employeePortal.location.mapper.LocationAllIndcatorsRequestMapper;
import com.app.employeePortal.location.mapper.LocationDetailsDTO;
import com.app.employeePortal.location.mapper.LocationDetailsViewDTO;
import com.app.employeePortal.location.service.LocationDetailsService;

@RestController
@CrossOrigin(maxAge = 3600)
public class LocationDetailsController {

	@Autowired
	LocationDetailsService locationDetailsService;
	
	@Autowired
    private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/locationDetails/save")
	public ResponseEntity<?> saveLocationDetails(@RequestHeader("Authorization") String authorization,
			@RequestBody LocationDetailsDTO locationDetailsDTO,HttpServletRequest request) {
		HashMap map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	        	String authToken = authorization.replace(TOKEN_PREFIX, "");
	        	locationDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
	        	locationDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
	        	if (!StringUtils.isEmpty(locationDetailsDTO.getLocationName())) {
	        		if (!StringUtils.isEmpty(locationDetailsDTO.getRegionsId())) {
						if (locationDetailsService.locationNameExist(locationDetailsDTO.getLocationName(), locationDetailsDTO.getRegionsId(), locationDetailsDTO.getOrgId())) {
							map.put("emailInd", true);
							map.put("message", "Location with same Name already exists.");
							return new ResponseEntity<>(map, HttpStatus.OK);
	
						} else {
		        	LocationDetailsViewDTO locationDetailsId = locationDetailsService.saveLocationDetails(locationDetailsDTO);
		        	
		        	return new ResponseEntity<>(locationDetailsId, HttpStatus.OK);
						}
	        		} else {
						map.put("message", " please provide Regions !!!");
						return new ResponseEntity<>(map, HttpStatus.OK);
					}
				} else {
					map.put("message", " please provide LocationName !!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
		
		}
		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/locationDetails/{locationDetailsId}")
	public ResponseEntity<?> getLocationDetails(@RequestHeader("Authorization") String authorization,
           @PathVariable("locationDetailsId") String locationDetailsId) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(locationDetailsService.getLocationDetails(locationDetailsId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/locationDetails/getLocationDetailsList/{orgId}")
	public ResponseEntity<?> getLocationDetailsList(@RequestHeader("Authorization") String authorization,
            HttpServletRequest request, @PathVariable("orgId") String orgId) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			List<LocationDetailsViewDTO> resultList = locationDetailsService.getLocationDetailsList(orgId);
			
			return ResponseEntity.ok(resultList);
			}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/api/v1/locationDetails/{locationDetailsId}")
	public ResponseEntity<LocationDetailsViewDTO> updateLocationDetails(
			@PathVariable("locationDetailsId") String locationDetailsId,@RequestHeader("Authorization") String authorization,
            HttpServletRequest request,@RequestBody LocationDetailsDTO locationDetailsDTO) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
        	locationDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
        	locationDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return ResponseEntity.ok(locationDetailsService.updateLocationDetails(locationDetailsId, locationDetailsDTO));
			}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/locationCount/{orgId}")
	public ResponseEntity<?> getAllLocationCount(@RequestHeader("Authorization") String authorization,
													HttpServletRequest request, @PathVariable("orgId") String orgId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(locationDetailsService.getAllLocationCount(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping("/api/v1/locationDetails/deleteLocationDetails/{locationDetailsId}")
	public ResponseEntity<?> deleteLocationDetails(@PathVariable("locationDetailsId") String locationDetailsId,@RequestHeader("Authorization") String authorization,
            HttpServletRequest request) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
        	String userId = (jwtTokenUtil.getUserIdFromToken(authToken));
        	String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			locationDetailsService.deleteLocationDetails(locationDetailsId,orgId,userId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/api/v1/locationDetails/update/all-indicators")
	public ResponseEntity<?> updateLocationIndicators(@RequestBody LocationAllIndcatorsRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			LocationDetailsViewDTO id = locationDetailsService.updateLocationIndicators(requestMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
//	@PostMapping("/api/v1/locationDetails/Distributor/save")
//	public ResponseEntity<LocationDetailsViewDTO> addDistributorWithLocation(@RequestHeader("Authorization") String authorization,
//			@RequestBody LocationDetailsDTO locationDetailsDTO,HttpServletRequest request) {
//		 
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//	        	String authToken = authorization.replace(TOKEN_PREFIX, "");
//	        	locationDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//	        	locationDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
//	        	LocationDetailsViewDTO locationDetailsId = locationDetailsService.saveLocationDetails(locationDetailsDTO);
//	        	
//	        	return new ResponseEntity<>(locationDetailsId, HttpStatus.OK);
//		
//		}
//		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}

	@GetMapping("/api/v1/locationDetails/deleteLocationHistory/{orgId}")
	public ResponseEntity<List<LocationDetailsViewDTO>> getAllDeteleLocationList(@PathVariable String orgId, @RequestHeader("Authorization") String authorization,
            HttpServletRequest request) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<LocationDetailsViewDTO> locationList = locationDetailsService.getAllDeteleLocationList(orgId);
			return ResponseEntity.ok(locationList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/locationDetails/locationReInState/{locationDetailsId}")
	public ResponseEntity<LocationDetailsViewDTO> updateLocationReInState(
			@RequestHeader("Authorization") String authorization,HttpServletRequest request,@PathVariable("locationDetailsId") String locationDetailsId) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			LocationDetailsDTO locationDetailsDTO = new  LocationDetailsDTO();
			locationDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
        	locationDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return ResponseEntity.ok(locationDetailsService.updateLocationReInState(locationDetailsId, locationDetailsDTO));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@GetMapping("/locationDetails/record/count")
//	public ResponseEntity<?> getLocationCountList(@RequestHeader("Authorization") String authorization,
//            HttpServletRequest request) {
//		
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			return ResponseEntity.ok(locationDetailsService.getLocationCountList());
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}

	@GetMapping("/api/v1/locationDetails/delete/location/record/count/{orgId}")
	public ResponseEntity<?> getDeleteLocationCountList(@PathVariable String orgId,@RequestHeader("Authorization") String authorization,
            HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(locationDetailsService.getDeleteLocationCountList(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/locationDetails/productionInd/inventoryInd/{orgId}")
	public ResponseEntity<?> getLocationDetailsListByOrgIdWhoseProductionIndAndInventoryIndTrue(@RequestHeader("Authorization") String authorization,
            HttpServletRequest request, @PathVariable("orgId") String orgId) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			List<LocationDetailsViewDTO> resultList = locationDetailsService.
					getLocationDetailsListByOrgIdWhoseProductionIndAndInventoryIndTrue(orgId);
			
			return ResponseEntity.ok(resultList);
			}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
}
