package com.app.employeePortal.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.Phone;
import com.app.employeePortal.attendance.entity.PhoneTimes;
@Repository
public interface PhoneTimesRepository extends JpaRepository<PhoneTimes, String>{
	PhoneTimes findByPhoneId(String phoneId);

	List<PhoneTimes> getByPhoneId(String phn1);

	PhoneTimes findByPhoneIdAndPauseInd(String phoneId, boolean b);
	
	@Query("SELECT e FROM PhoneTimes e WHERE e.createAt = (SELECT MAX(e2.createAt) FROM PhoneTimes e2) and e.phoneId=:phoneId")
	PhoneTimes getTheLatestPhoneTime(String phoneId);

	PhoneTimes findFirstByIdOrderByCreateAtDesc(String id);
}
