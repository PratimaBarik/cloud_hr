package com.app.employeePortal.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.category.entity.FeedbackLibrary;
import com.app.employeePortal.category.service.FeedbackLibraryService;

@RestController
@CrossOrigin(maxAge = 3600)

public class FeedbackLibraryController {
	
	@Autowired
	FeedbackLibraryService feedbackLibraryService;

	
	
	@GetMapping("/api/v1/feedback/library")
	public List<FeedbackLibrary> getAllFeedbackLibraryList() {

		List<FeedbackLibrary> feedbackLibraryList = feedbackLibraryService.feedbackLibraryList();

		return feedbackLibraryList;
	}

}
