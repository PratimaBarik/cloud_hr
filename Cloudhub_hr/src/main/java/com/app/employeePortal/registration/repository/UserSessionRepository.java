package com.app.employeePortal.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String>{

	@Query(value = "select u  from UserSession u  where u.email_id=:email and u.token_id=:token and u.user_id=:empId and u.organization_id=:orgId " )
	public List<UserSession> getUserSession(@Param(value="email") String email,
											@Param(value="token")String token,
											@Param(value="empId") String empId,
											@Param(value="orgId") String orgId);
	
	
}
