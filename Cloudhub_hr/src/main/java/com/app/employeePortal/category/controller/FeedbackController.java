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
import com.app.employeePortal.category.mapper.FeedbackMapper;
import com.app.employeePortal.category.service.FeedbackService;

@RestController
@CrossOrigin(maxAge = 3600)

public class FeedbackController {
	@Autowired
	FeedbackService feedbackService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/feedback")
	public ResponseEntity<?> createFeedback(@RequestBody FeedbackMapper mapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

//			if (!StringUtils.isEmpty(mapper.getfeedbackName())) {
//				boolean b = feedbackService.checkfeedbackNameInfeedback(mapper.getfeedbackName(),
//						jwtTokenUtil.getOrgIdFromToken(authToken));
//				if (b == true) {
//					System.out.println("true");
//					map.put("feedbackInd", b);
//					map.put("message", "feedback can not be created as same name already exists!!!");
//					return new ResponseEntity<>(map, HttpStatus.OK);
//				} else {
//					feedbackMapper Id = feedbackService.savefeedback(mapper);
//					return new ResponseEntity<>(Id, HttpStatus.OK);
//				}
//			} else {
//				map.put("message", "please provide a feedback");
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}

			FeedbackMapper Id = feedbackService.saveFeedback(mapper);
			return new ResponseEntity<>(Id, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/feedback/All")
	public ResponseEntity<?> getFeedbackByOrgId(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<FeedbackMapper> mapper = feedbackService.getFeedbackByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/feedback/{feedbackId}")

	public ResponseEntity<?> updateFeedback(@PathVariable("feedbackId") String feedbackId,
			@RequestBody FeedbackMapper mapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<FeedbackMapper>(feedbackService.updateFeedback(feedbackId, mapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/feedback/{feedbackId}")
	public ResponseEntity<?> deleteFeedback(@PathVariable("feedbackId") String feedbackId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			feedbackService.deleteFeedback(feedbackId, jwtTokenUtil.getUserIdFromToken(authToken));

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@GetMapping("/api/v1/feedback/search/{feedbackName}")
//	public ResponseEntity<?> getfeedbackByName(@PathVariable("feedbackName") String feedbackName,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		Map map = new HashMap();
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			List<feedbackMapper> mapper = feedbackService.getfeedbackByName(feedbackName,
//					jwtTokenUtil.getOrgIdFromToken(authToken));
//			if (null != mapper && !mapper.isEmpty()) {
//				return new ResponseEntity<>(mapper, HttpStatus.OK);
//			} else {
//				map.put("message", " No Records Found !!!");
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}

	@GetMapping("/api/v1/feedback/count/All")
	public ResponseEntity<?> getFeedbackCountByOrgId(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(feedbackService.getFeedbackCountByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken)));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
