package com.app.employeePortal.registration.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.organization.service.OrganizationService;
import com.app.employeePortal.registration.entity.UserSession;
import com.app.employeePortal.registration.entity.UserSettings;
import com.app.employeePortal.registration.mapper.EmailValidationMapper;
import com.app.employeePortal.registration.mapper.NewAdminRegisterMapper;
import com.app.employeePortal.registration.mapper.UserPasswordRq;
import com.app.employeePortal.registration.repository.UserSessionRepository;
import com.app.employeePortal.registration.repository.UserSettingsRepository;

@Service(value = "userService")
@Transactional
public class RegistrationServiceImpl implements RegistrationService ,UserDetailsService {

	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	UserSessionRepository userSessionRepository;
	
	@Autowired
	UserSettingsRepository userSettingsRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	OrganizationRepository organizationRepository;
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String emailId)
			throws UsernameNotFoundException {

		UserSettings userSettings = userSettingsRepository.getUserSettingsByEmail(emailId,true);
		System.out.println("emailValidationMapper...... " +  userSettings);


		if (userSettings == null) {

			throw new UsernameNotFoundException("Invalid username or password.");

		}
		return new org.springframework.security.core.userdetails.User(userSettings.getEmail(), userSettings.getPassword(),
				getAuthority(emailId));

	}

	private List<SimpleGrantedAuthority> getAuthority(String emailId) {

		UserSettings userSeting = userSettingsRepository.getUserSettingsByEmail(emailId,true);

		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userSeting.getUserType()));
	}
	@Override
	public boolean emailExist(String emailId) {
		
		List<UserSettings> list = userSettingsRepository.getUserSettingsByEmailId(emailId.toLowerCase());
		if (null != list && !list.isEmpty()) {

				return true;
			
		}
		return false;	
		}

//	@Override
//	public String adminRegistrationProcess(AdminRegisterMapper adminRegisterMapper) {
//
//		String orgId  =  organizationService.saveToOrganizationProcess(adminRegisterMapper.getOrganization());
//		adminRegisterMapper.getEmployee().setUserType("ADMIN");
//		adminRegisterMapper.getEmployee().setRole("ADMIN");
//		EmployeeTableMapper empId = employeeService.saveToEmployeeProcess(adminRegisterMapper.getEmployee(),orgId);
//		
//		return orgId;
//	}

	@Override
	public String adminRegistrationProcess(NewAdminRegisterMapper adminRegisterMapper) {

		String orgId  =  organizationService.saveToOrganizationProcess(adminRegisterMapper);
		adminRegisterMapper.setUserType("ADMIN");
		adminRegisterMapper.setRole("ADMIN");
		 employeeService.saveToEmployeeProcess(adminRegisterMapper,orgId);
		
		return orgId;
	}


	@Override
	public boolean checkEmployeeStatus(EmailValidationMapper emailValidationMapper) {
		System.out.println("emailValidationMapper....... " +  emailValidationMapper);
		String email =emailValidationMapper.getEmailId() ;
		String token=emailValidationMapper.getToken();
		String empId = emailValidationMapper.getEmpId();
		String orgId = emailValidationMapper.getOrganizationId();
		List<UserSession> result =userSessionRepository.getUserSession(email, token,  empId, orgId) ;

		System.out.println("email validation result... " + result);
		if (null != result && !result.isEmpty()) {

			return true;

		} else {

			return false;
		}
	}

	@Override
	public boolean validateEmailAddress(EmailValidationMapper emailValidationMapper) {
		System.out.println("emailValidationMapper........ " +  emailValidationMapper);

		boolean status = checkEmployeeStatus(emailValidationMapper);
		if (status) {

			UserSettings userSettings = userSettingsRepository.getUserSettingsByEmail(emailValidationMapper.getEmailId(),true);

			userSettings.setEmailValInd(true);
			userSettings.setUserActiveInd(true);
			userSettingsRepository.save(userSettings);

		}

		return status;
	}

	@Override
	public boolean setPassword(UserPasswordRq passwordRq) {
		if(null!=passwordRq.getEmailId()) {
			UserSettings dbuserSettings =userSettingsRepository.getUserSettingsByEmail(passwordRq.getEmailId(),true) ;
			if(null != dbuserSettings) {
			dbuserSettings.setPassword(new BCryptPasswordEncoder().encode(passwordRq.getPassword()));
			dbuserSettings.setPasswordActiveInd(true);
			userSettingsRepository.save(dbuserSettings);

			return true;
			}
		}
		
		return false;
	}

	@Override
	public void forgetPassword(String email) {
		if (emailExist(email)) {


			// prepare forget password link//
			String from = "engage@tekorero.com";
			String subject = "Forgot Password!!!";
			String passwordLink = emailService.forgetPasswordLink(email);

			try {

				emailService.sendMail(from, email, subject, passwordLink);

			} catch (Exception e) {

				e.printStackTrace();
			}


		}
	}

	@Override
	public HashMap getUserSettingsByEmail(String email) {
		String emailId = email.toLowerCase();
		System.out.println("emailId ==LowerCase== "+emailId);
		UserSettings userSettings =userSettingsRepository.getUserSettingsByEmail(emailId,true);
		System.out.println("userSettings.........."+userSettings);
		String organizationId = null;
		HashMap map = new HashMap();

		if(null!=userSettings) {
			System.out.println("valid user found in userSettings..........");
			try {
				System.out.println("REG:: userSettings=="+userSettings.toString());
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userSettings.getUserId(),true);
				System.out.println("valid user found in EmployeeDetails..........");
				System.out.println("employeeDetails:::.........."+employeeDetails.toString());
				organizationId = employeeDetails.getOrgId();
				map.put("userId", userSettings.getUserId());
				map.put("organizationId", organizationId);
				map.put("userType",employeeDetails.getEmployeeType());
			
			} catch (Exception e) {
				System.out.println("user not found in EmployeeDetails..........");
				e.printStackTrace();
			}
			
		}else {
			System.out.println("user not found in userSettings..........");
		}

		
		return map;
	}

	@Override
	public String prepareEmailValidationLink(String token, String employeeId, String name, String emailId,
			String organizationId) {
		OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(organizationId);
		 String myvar = "<div style=' display: block; margin-top: 100px; '>"+
					"    <div style='  text-align: center;'> </div>"+
					"    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"+
					"        <div class='box-2' style='  text-align: center;'>"+
					"            <h1 style='text-align: center; padding: 10px;'>Hello "+name+" </h1>"+
					"            <p style='text-align: center;'>Thank you for joining"+ organizationDetails.getName()+"!!<br /><br />To finish signing up, you just need to"+
					"                confirm that we got your"+
					"                email right.</p><br />"+
					"            <hr><br />"+
					"            <div class='' style='text-align: center;'>"+
					"                <a href='https://hrapp.tekorero.com/setPassword'"+
					"style='display: inline-block; background-color: transparent; border: 1px solid transparent; padding: .375rem .75rem;"+
					"                    font-size: 1rem; line-height: 1.5;text-decoration: none;  border-radius: .25rem; color: #fff; background-color: #005075; border-color: #dc3545;"+
					"                    transition: color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out;'>Confirm"+
					"                    your Email</a><br />"+
					"            </div>"+
					"        </div>"+
					"    </div>"+
					"</div>";
	
		
		return myvar;
	}


	
	
	

}
