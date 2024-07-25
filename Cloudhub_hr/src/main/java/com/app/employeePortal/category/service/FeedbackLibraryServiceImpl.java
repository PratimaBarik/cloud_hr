package com.app.employeePortal.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.category.entity.FeedbackLibrary;
import com.app.employeePortal.category.repository.FeedbackLibraryRepository;
@Service

public class FeedbackLibraryServiceImpl implements FeedbackLibraryService{
	
	@Autowired
	FeedbackLibraryRepository feedbackLibraryRepository;
	
        @Override
	    public List<FeedbackLibrary> feedbackLibraryList() {
		List<FeedbackLibrary> list = feedbackLibraryRepository.findAll();
		System.out.println("       list"+list);
		return list;
        }
        
}
