package com.app.employeePortal.notification.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.notification.mapper.NotificationConfigRequest;
import com.app.employeePortal.notification.mapper.NotificationConfigResponse;
import com.app.employeePortal.notification.mapper.NotificationMapper;
import com.app.employeePortal.notification.mapper.NotificationRuleMapper;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;



@RestController
@CrossOrigin(maxAge = 3600)
public class NotificationController {
	@Autowired
	NotificationService notificationservice;
	
	@Autowired	
    private TokenProvider jwtTokenUtil;
	@Autowired
	WebsiteRepository websiteRepository;
		
	
	@GetMapping("/api/v1/present/notifications/{userId}")	
	public ResponseEntity<?> getTodaysNotificationList(@PathVariable("userId") String userId,
			                                         //  @PathVariable("pageNo") int pageNo,
			                                           @RequestHeader("Authorization") String authorization,
			                                            HttpServletRequest request)throws Exception {
			                                            
	
		 // int pageSize=30;
		  List<NotificationMapper> notificationList =null;
		  
		  if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				String authToken = authorization.replace(TOKEN_PREFIX, "");
	            String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
	            boolean b =notificationservice.getInappNotificationRule(orgId);
	            System.out.println("bbbb=="+b);
	        	if (b) {
	          notificationList = notificationservice.getTodaysNotificationsByUserId(userId);
	  			return new ResponseEntity<>(notificationList, HttpStatus.OK);
	        	}else {
	        		Map map = new HashMap();
	        		map.put("notificationInd", false);
					map.put("message", "Admin has blocked in-app notification");
					return new ResponseEntity<>(map, HttpStatus.OK);
	        	}
		
	        }else {

				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		
		
	}
	
	
	@GetMapping("/api/v1/previous/notifications/{userId}")
	public ResponseEntity<?> getPreviousNotificationList(@PathVariable("userId") String userId,
			                                          //   @PathVariable("pageNo") int pageNo,
			                                             @RequestHeader("Authorization") String authorization,
			                                            HttpServletRequest request)throws Exception{
			                                            
	
		 // int pageSize=30;
		  List<NotificationMapper> notificationList =null;
			 
	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	     
	          notificationList = notificationservice.getPreviousNotificationsByUserId(userId);
	      	
	  			return new ResponseEntity<>(notificationList, HttpStatus.OK);

		
	        }else {

					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		
		
	}
	
	 @PatchMapping("/api/v1/notification/{notificationId}")
	    public ResponseEntity<?> patchUpdateNotification(@PathVariable("notificationId") String notificationId, @RequestBody NotificationMapper notificationMapper,
	                                                     @RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {


	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

	            String authToken = authorization.replace(TOKEN_PREFIX, "");


	            notificationMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
	            //notificationMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));


	            notificationservice.patchNotification(notificationId, notificationMapper);

	            NotificationMapper notificationMapperNew = notificationservice.getNotificationResponseByNotificationId(notificationId);
	            notificationMapperNew.setNotificationId(notificationId);


	            return new ResponseEntity<NotificationMapper>(notificationMapperNew, HttpStatus.OK);
	        }


	        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

	    }
	 @PutMapping("/api/v1/notification/rule/update")
     public ResponseEntity<?> updateNotifiactionRule(@RequestBody NotificationRuleMapper notificationRuleMapper,
             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		 NotificationRuleMapper notificationRuleMapperr = null;
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            String authToken = authorization.replace(TOKEN_PREFIX, "");

            notificationRuleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            notificationRuleMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));


            notificationRuleMapperr = notificationservice.updateNotifiactionRule(notificationRuleMapper);
             return new ResponseEntity<>(notificationRuleMapperr, HttpStatus.OK);

         }
         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	 }
	 
	 @GetMapping("/api/v1/notification/rule/{orgId}")
     public ResponseEntity<?> getNotificationRuleByUserId(@RequestHeader("Authorization") String authorization,
             @PathVariable("orgId") String orgId, HttpServletRequest request) {
         if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
             //String authToken = authorization.replace(TOKEN_PREFIX, "");

             return ResponseEntity.ok(notificationservice.getNotificationRuleByOrgId(orgId));
         }
         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	
		@GetMapping("/api/v1/present/notifications/web/{userId}")
		public ResponseEntity<?> getTodaysNotificationListforWeb(@PathVariable("userId") String userId,
				@RequestParam(value = "url", required = true) String url, HttpServletRequest request) throws Exception {
			Map map = new HashMap();
			Website web = websiteRepository.getByUrl(url);
			if (null != web) {
				boolean b = notificationservice.getInappNotificationRule(web.getOrgId());
				System.out.println("bbbb==" + b);
				if (b) {
					List<NotificationMapper> notificationList = notificationservice
							.getTodaysNotificationsByUserId(userId);
					return new ResponseEntity<>(notificationList, HttpStatus.OK);
				} else {
					map.put("notificationInd", false);
					map.put("message", "Admin has blocked in-app notification");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}

			} else {
				map.put("website", true);
				map.put("message", " website not Present !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		
		@GetMapping("/api/v1/previous/notifications/web/{userId}")
		public ResponseEntity<?> getPreviousNotificationListForWeb(@PathVariable("userId") String userId,
				@RequestParam(value = "url", required = true) String url, HttpServletRequest request) throws Exception {

			Map map = new HashMap();
			Website web = websiteRepository.getByUrl(url);
			if (null != web) {
				boolean b = notificationservice.getInappNotificationRule(web.getOrgId());
				System.out.println("bbbb==" + b);
				if (b) {

					List<NotificationMapper> notificationList = notificationservice
							.getPreviousNotificationsByUserId(userId);
					return new ResponseEntity<>(notificationList, HttpStatus.OK);
				} else {
					map.put("notificationInd", false);
					map.put("message", "Admin has blocked in-app notification");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			} else {
				map.put("website", true);
				map.put("message", " website not Present !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		
		@PutMapping("/api/v1/notification/config")
	     public ResponseEntity<?> updateNotifiactionConfig(@Valid @RequestBody NotificationConfigRequest mapper,
	             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	            String authToken = authorization.replace(TOKEN_PREFIX, "");
	            String userId = jwtTokenUtil.getUserIdFromToken(authToken);
	            String OrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
	            NotificationConfigResponse response = notificationservice.updateNotifiactionConfig(mapper,userId,OrgId);
	             return new ResponseEntity<>(response, HttpStatus.OK);
	         }
	         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 }
		
		@GetMapping("/api/v1/notification/config/{name}")
	     public ResponseEntity<?> getNotifiactionConfig(@PathVariable String name,
	      @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	            String authToken = authorization.replace(TOKEN_PREFIX, "");
	            String OrgId = jwtTokenUtil.getOrgIdFromToken(authToken);

	            NotificationConfigResponse response = notificationservice.getNotifiactionConfig(OrgId,name);
	             return new ResponseEntity<>(response, HttpStatus.OK);
	         }
	         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 }
		
		@GetMapping("/api/v1/notification/count/{userId}")
		public ResponseEntity<?> getNotificationCountUserId(@RequestHeader("Authorization") String authorization,
				@PathVariable("userId") String userId) {
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				return ResponseEntity.ok(notificationservice.getNotificationCountUserId(userId));
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		@GetMapping("/api/v1/notification/config/get-all")
	     public ResponseEntity<?> getNotifiactionConfigByOrgId(
	      @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	            String authToken = authorization.replace(TOKEN_PREFIX, "");
	            String OrgId = jwtTokenUtil.getOrgIdFromToken(authToken);

	            List<NotificationConfigResponse> response = notificationservice.getNotifiactionConfigByOrgId(OrgId);
				 if(null!=response && !response.isEmpty()) {
					 Collections.sort(response, (v1, v2) -> v1.getName().compareTo(v2.getName()));
				 }
	             return new ResponseEntity<>(response, HttpStatus.OK);
	         }
	         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 }
}
