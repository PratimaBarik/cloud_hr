package com.app.employeePortal.registration.service;

import java.util.HashMap;

import com.app.employeePortal.registration.mapper.EmailValidationMapper;
import com.app.employeePortal.registration.mapper.NewAdminRegisterMapper;
import com.app.employeePortal.registration.mapper.UserPasswordRq;

public interface RegistrationService {
	
	public boolean emailExist(String emailId) ;

//	public String adminRegistrationProcess(AdminRegisterMapper adminRegisterMapper);
	
	public String adminRegistrationProcess(NewAdminRegisterMapper adminRegisterMapper);
	
	public boolean validateEmailAddress(EmailValidationMapper emailValidationMapper);

	public boolean setPassword(UserPasswordRq passwordRq);

	public void forgetPassword(String email);

	public HashMap getUserSettingsByEmail(String email);
	
	public boolean checkEmployeeStatus(EmailValidationMapper emailValidationMapper);

	public String prepareEmailValidationLink(String token, String employeeId, String name, String emailId,
			String organizationId);

}
 