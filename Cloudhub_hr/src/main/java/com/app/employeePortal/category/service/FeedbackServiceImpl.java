package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.Feedback;
import com.app.employeePortal.category.mapper.FeedbackMapper;
import com.app.employeePortal.category.repository.FeedbackRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackRepository feedbackRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };

	@Override
	public FeedbackMapper saveFeedback(FeedbackMapper mapper) {
		String feedbackId = null;
		if (mapper != null) {
			Feedback feedback = new Feedback();
			feedback.setCreationDate(new Date());
			feedback.setLiveInd(true);
			feedback.setOrgId(mapper.getOrgId());
			feedback.setUpdatedBy(mapper.getUserId());
			feedback.setUpdationDate(new Date());
			feedback.setUserId(mapper.getUserId());
			feedback.setName(mapper.getName());
			feedback.setDescription(mapper.getDescription());
			feedbackId = feedbackRepository.save(feedback).getFeedbackId();

		}
		FeedbackMapper resultMapper = getFeedbackByFeedbackId(feedbackId);
		return resultMapper;
	}

	public FeedbackMapper getFeedbackByFeedbackId(String feedbackId) {

		Feedback feedback = feedbackRepository.findByFeedbackIdAndLiveInd(feedbackId, true);
		FeedbackMapper feedbackMapper = new FeedbackMapper();

		if (null != feedback) {

			feedbackMapper.setCreationDate(Utility.getISOFromDate(feedback.getCreationDate()));
			feedbackMapper.setLiveInd(true);
			feedbackMapper.setOrgId(feedback.getOrgId());
			feedbackMapper.setUpdatedBy(employeeService.getEmployeeFullName(feedback.getUserId()));
			feedbackMapper.setUpdationDate(Utility.getISOFromDate(feedback.getUpdationDate()));
			feedbackMapper.setUserId(feedback.getUserId());
			feedbackMapper.setFeedbackId(feedback.getFeedbackId());
			feedbackMapper.setName(feedback.getName());
			feedbackMapper.setDescription(feedback.getDescription());
		}

		return feedbackMapper;
	}

	@Override
	public List<FeedbackMapper> getFeedbackByOrgId(String orgId) {
		List<FeedbackMapper> resultMapper = new ArrayList<>();
		List<Feedback> list = feedbackRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getFeedbackByFeedbackId(li.getFeedbackId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Feedback> list1 = feedbackRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public FeedbackMapper updateFeedback(String feedbackId, FeedbackMapper mapper) {
		Feedback feedback = feedbackRepository.findByFeedbackIdAndLiveInd(feedbackId, true);
		if (null != feedback) {

			feedback.setLiveInd(true);
			feedback.setOrgId(mapper.getOrgId());
			feedback.setUpdatedBy(mapper.getUserId());
			feedback.setUpdationDate(new Date());
			feedback.setUserId(mapper.getUserId());
			feedback.setName(mapper.getName());
			feedback.setDescription(mapper.getDescription());
			feedbackRepository.save(feedback);
		}
		FeedbackMapper resultMapper = getFeedbackByFeedbackId(feedbackId);
		return resultMapper;
	}

	@Override
	public void deleteFeedback(String feedbackId, String userId) {
		if (null != feedbackId) {
			Feedback feedback = feedbackRepository.findByFeedbackIdAndLiveInd(feedbackId, true);

			feedback.setUpdationDate(new Date());
			feedback.setUpdatedBy(userId);
			feedback.setLiveInd(false);
			feedbackRepository.save(feedback);
		}
	}

//	@Override
//	public List<feedbackMapper> getfeedbackByName(String feedbackName, String orgId) {
//		List<feedback>list=feedbackRepository.findByfeedbackNameContainingAndOrgId(feedbackName,orgId);
//		List<feedbackMapper> resultList=new ArrayList<feedbackMapper>();
//		if(null!=list&&!list.isEmpty()) {
//			list.stream().map(feedback->{
//				feedbackMapper mapper=getfeedbackByfeedbackId(feedback.getfeedbackId());
//				if(null!=mapper) {
//					resultList.add(mapper);
//				}
//				return resultList;
//			}).collect(Collectors.toList());
//		}
//		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
//
//		List<feedback> list1 = feedbackRepository.findAll();
//		if (null != list1 && !list1.isEmpty()) {
//			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
//
//			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
//			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
//		}
//		return resultList;
//	}

	@Override
	public HashMap getFeedbackCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Feedback> list = feedbackRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("FeedbackCount", list.size());
		return map;
	}


//	@Override
//	public boolean checkfeedbackNameInfeedback(String feedbackName, String orgId) {
//		List<feedback> feedback = feedbackRepository.findByfeedbackNameAndLiveIndAndOrgId(feedbackName, true,
//				orgId);
//		if (feedback.size() > 0) {
//			return true;
//		}
//		return false;
//	}

}