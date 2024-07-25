package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

	Feedback findByFeedbackIdAndLiveInd(String feedbackId, boolean b);

	List<Feedback> findByOrgIdAndLiveInd(String orgId, boolean b);

}
