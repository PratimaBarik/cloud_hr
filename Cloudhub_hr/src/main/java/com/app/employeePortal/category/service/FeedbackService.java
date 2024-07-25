package com.app.employeePortal.category.service;

import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.FeedbackMapper;

public interface FeedbackService {

	public FeedbackMapper saveFeedback(FeedbackMapper mapper);

	public List<FeedbackMapper> getFeedbackByOrgId(String orgIdFromToken);

	public FeedbackMapper updateFeedback(String FeedbackId, FeedbackMapper mapper);

	public void deleteFeedback(String FeedbackId, String userIdFromToken);

//	public List<FeedbackMapper> getFeedbackByName(String FeedbackName, String orgId);

	public HashMap getFeedbackCountByOrgId(String orgIdFromToken);

//	public boolean checkFeedbackNameInFeedback(String FeedbackName, String orgId);

}
