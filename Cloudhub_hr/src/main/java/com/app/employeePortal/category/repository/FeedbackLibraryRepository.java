package com.app.employeePortal.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.FeedbackLibrary;
@Repository
public interface FeedbackLibraryRepository extends JpaRepository<FeedbackLibrary, String>{

	

}
