package com.app.employeePortal.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.UserSettings;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, String>{
	
	@Query(value = "select a  from UserSettings a  where a.email=:email" )
    public List<UserSettings> getUserSettingsByEmailId(@Param(value="email")String email);
	
	@Query(value = "select a  from UserSettings a  where a.email=:email and a.liveInd=:liveInd" )
    public UserSettings getUserSettingsByEmail(@Param(value="email")String email,@Param(value="liveInd")boolean liveInd);

//	@Query(value = "select a  from UserSettings a  where LOWER(a.email)=LOWER(:email) and a.liveInd=:liveInd" )
//    public UserSettings getUserSettingsByEmailId(@Param(value="email")String email,@Param(value="liveInd")boolean liveInd);
//	
//	public UserSettings findByEmailIgnoreCaseAndLiveInd(String email, boolean b);
//	
	@Query(value = "select a  from UserSettings a  where a.userId=:userId" )
    public UserSettings getUserSettingsByUserId(@Param(value="userId")String userId);

	public UserSettings findByUserIdAndLiveInd(String userId, boolean b);
}
