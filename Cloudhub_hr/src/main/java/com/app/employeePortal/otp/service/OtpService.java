
package com.app.employeePortal.otp.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.otp.entity.OTPEntity;
import com.app.employeePortal.otp.mapper.OTPValidationResponse;
import com.app.employeePortal.otp.mapper.OtpMapper;
import com.app.employeePortal.otp.mapper.SendOTPResponse;
import com.app.employeePortal.otp.repository.OtpRepository;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class OtpService {

	private final OtpRepository otpRepository;

    private final Configuration configuration;
    
    @Autowired
    CandidateDetailsRepository candidateDetailsRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    

    private final String subject = "OTP Verification";

	    public int generateOTP() {
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000);
	        return otp;
	    }
	    

	    public OtpService(OtpRepository otpRepository, Configuration configuration) {
			this.otpRepository = otpRepository;
			this.configuration = configuration;
		}

	    public SendOTPResponse sendOTP(OtpMapper otpDTO) throws TemplateException, IOException {
	    	//SendOTPResponse sendOTPResponse = new SendOTPResponse();
	    	CandidateDetails candidateDetails = candidateDetailsRepository.findByEmail(otpDTO.getEmailId());
	    	 if (candidateDetails != null) {
	        OTPEntity otpEntity = otpRepository.findByEmailId(otpDTO.getEmailId()).map(otp -> {
	        	System.out.println("dddddddddddddddddddddd");
	            otp.setOtp(generateOTP());
	            otp.setValidateIND(false);
	            return otp;
	        }).orElse(OTPEntity.builder()
	                .emailId(otpDTO.getEmailId())
	                .otp(generateOTP())
	                .validateIND(false)
	                .build());
	        OTPEntity otp = otpRepository.save(otpEntity);
	        System.out.println("OTP="+otp.getOtp());

	        if (otpDTO.getEmailId() != null) {
	            	
	        		String fromEmail = "support@innoverenit.com";
	        		String to = otpDTO.getEmailId();
	                String message = getEmailContent(otp.getOtp());
	                String serverUrl ="https://develop.tekorero.com/kite/email/send";
	        	    HttpHeaders headers = new HttpHeaders();
	        	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
	        	    
	        	    body.add("fromEmail", fromEmail);
	        	    body.add("message", message);
	        	    body.add("subject",subject);
	        	    body.add("toEmail", to);
	        	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
	        	   RestTemplate restTemplate = new RestTemplate();
	        	   ResponseEntity<String> response = restTemplate
	        	     .postForEntity(serverUrl, requestEntity, String.class);
	        	   	        } 
	        return SendOTPResponse.builder()
	        		.status(true)
	        		.message(" OTP sent successfully.")
	        		.build();
	    	 }
	    	  return SendOTPResponse.builder()
		        		.status(false)
		        		.message("Invalid email address!!!!")
		        		.build();
	    	 }
	    
		
		String getEmailContent(Integer otp) throws IOException, TemplateException {
	        StringWriter stringWriter = new StringWriter();
	        Map<String, Object> model = new HashMap<>();
	        model.put("otp", otp);
	        configuration.getTemplate("email.ftlh").process(model, stringWriter);
	        System.out.println("................................................");
	        System.out.println("emailcontent"+stringWriter.getBuffer().toString());
	        System.out.println("................................................");
	        return stringWriter.getBuffer().toString();
	       
	    }


	    public OTPValidationResponse validateOTP2(OtpMapper otpDTO) {
	    	OTPEntity oTPEntity = otpRepository.findByEmailIdAndOtp(otpDTO.getEmailId(), otpDTO.getOtp());

	    	if(oTPEntity != null) {
	    		String userId = candidateDetailsRepository.findByEmail(otpDTO.getEmailId()).getCandidateId();
	    		if(userId != null) {
	    		oTPEntity.setValidateIND(true);
		        otpRepository.save(oTPEntity);
		        return OTPValidationResponse.builder()
	                    .status(true)
	                    .userId(userId)
	                    .build();
	    		}
	    	}else {
	    		return OTPValidationResponse.builder()
	                    .status(false)
	                    .build();
	    	}
	    	return OTPValidationResponse.builder()
                    .status(false)
                    .build();
	    }


		public Object sendotpForUser(OtpMapper otpDTO)throws TemplateException, IOException {
	    	//SendOTPResponse sendOTPResponse = new SendOTPResponse();
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByMailId(otpDTO.getEmailId());
	    	 if (employeeDetails != null) {
	        OTPEntity otpEntity = otpRepository.findByEmailId(otpDTO.getEmailId()).map(otp -> {
	        	System.out.println("dddddddddddddddddddddd");
	            otp.setOtp(generateOTP());
	            otp.setValidateIND(false);
	            return otp;
	        }).orElse(OTPEntity.builder()
	                .emailId(otpDTO.getEmailId())
	                .otp(generateOTP())
	                .validateIND(false)
	                .build());
	        OTPEntity otp = otpRepository.save(otpEntity);
	        System.out.println("OTP="+otp.getOtp());

	        if (otpDTO.getEmailId() != null) {
	            	
	        		String fromEmail = "support@innoverenit.com";
	        		String to = otpDTO.getEmailId();
	                String message = getEmailContentForForgotPassword(otp.getOtp());
	                String serverUrl ="https://develop.tekorero.com/kite/email/send";
	                String subject = "OTP For Forgot Password";
	                
	        	    HttpHeaders headers = new HttpHeaders();
	        	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
	        	    
	        	    body.add("fromEmail", fromEmail);
	        	    body.add("message", message);
	        	    body.add("subject",subject);
	        	    body.add("toEmail", to);
	        	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
	        	   RestTemplate restTemplate = new RestTemplate();
	        	   ResponseEntity<String> response = restTemplate
	        	     .postForEntity(serverUrl, requestEntity, String.class);
	        	   	        } 
	        return SendOTPResponse.builder()
	        		.status(true)
	        		.message(" OTP sent successfully.")
	        		.build();
	    	 }
	    	  return SendOTPResponse.builder()
		        		.status(false)
		        		.message("Invalid email address!!!!")
		        		.build();
		}
		
		String getEmailContentForForgotPassword(Integer otp) throws IOException, TemplateException {
	        StringWriter stringWriter = new StringWriter();
	        Map<String, Object> model = new HashMap<>();
	        model.put("otp", otp);    
	        configuration.getTemplate("forgotPasswordEmail.ftlh").process(model, stringWriter);
	        System.out.println("................................................");
	        System.out.println("emailcontent"+stringWriter.getBuffer().toString());
	        System.out.println("................................................");
	        return stringWriter.getBuffer().toString();
	       
	    }

		public OTPValidationResponse validateOTPForUser(OtpMapper otpDTO) {
			OTPEntity oTPEntity = otpRepository.findByEmailIdAndOtp(otpDTO.getEmailId(), otpDTO.getOtp());

	    	if(oTPEntity != null) {
	    		String userId = employeeRepository.getEmployeeByMailId(otpDTO.getEmailId()).getEmployeeId();
	    		if(userId != null) {
	    		oTPEntity.setValidateIND(true);
		        otpRepository.save(oTPEntity);
		        return OTPValidationResponse.builder()
	                    .status(true)
	                    .userId(userId)
	                    .build();
	    		}
	    	}else {
	    		return OTPValidationResponse.builder()
	                    .status(false)
	                    .build();
	    	}
	    	return OTPValidationResponse.builder()
                    .status(false)
                    .build();
		}


		public Object sendotpForLinkUser(OtpMapper otpDTO) throws IOException, TemplateException {
			//SendOTPResponse sendOTPResponse = new SendOTPResponse();
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByMailId(otpDTO.getEmailId());
	    	 if (employeeDetails != null) {
	        OTPEntity otpEntity = otpRepository.findByEmailId(otpDTO.getEmailId()).map(otp -> {
	        	System.out.println("dddddddddddddddddddddd");
	            otp.setOtp(generateOTP());
	            otp.setValidateIND(false);
	            return otp;
	        }).orElse(OTPEntity.builder()
	                .emailId(otpDTO.getEmailId())
	                .otp(generateOTP())
	                .validateIND(false)
	                .build());
	        OTPEntity otp = otpRepository.save(otpEntity);
	        System.out.println("OTP="+otp.getOtp());

	        if (otpDTO.getEmailId() != null) {
	            	
	        		String fromEmail = "support@innoverenit.com";
	        		String to = otpDTO.getEmailId();
	                String message = getEmailContentForLinkUser(otp.getOtp());
	                String serverUrl ="https://develop.tekorero.com/kite/email/send";
	                String subject = "OTP For Link User";
	                
	        	    HttpHeaders headers = new HttpHeaders();
	        	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
	        	    
	        	    body.add("fromEmail", fromEmail);
	        	    body.add("message", message);
	        	    body.add("subject",subject);
	        	    body.add("toEmail", to);
	        	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
	        	   RestTemplate restTemplate = new RestTemplate();
	        	   ResponseEntity<String> response = restTemplate
	        	     .postForEntity(serverUrl, requestEntity, String.class);
	        	   	        } 
	        return SendOTPResponse.builder()
	        		.status(true)
	        		.message(" OTP sent successfully.")
	        		.build();
	    	 }
	    	  return SendOTPResponse.builder()
		        		.status(false)
		        		.message("Invalid email address!!!!")
		        		.build();
		}
		
		String getEmailContentForLinkUser(Integer otp) throws IOException, TemplateException {
	        StringWriter stringWriter = new StringWriter();
	        Map<String, Object> model = new HashMap<>();
	        model.put("otp", otp);    
	        configuration.getTemplate("linkUser.ftlh").process(model, stringWriter);
	        System.out.println("................................................");
	        System.out.println("emailcontent"+stringWriter.getBuffer().toString());
	        System.out.println("................................................");
	        return stringWriter.getBuffer().toString();
	       
	    }


		public OTPValidationResponse validateOTPForUserEmailLink(OtpMapper otpDTO) {
			OTPEntity oTPEntity = otpRepository.findByEmailIdAndOtp(otpDTO.getEmailId(), otpDTO.getOtp());

	    	if(oTPEntity != null) {
	    		String userId = employeeRepository.getEmployeeByMailId(otpDTO.getEmailId()).getEmployeeId();
	    		if(userId != null) {
	    		oTPEntity.setValidateIND(true);
		        otpRepository.save(oTPEntity);
		        return OTPValidationResponse.builder()
	                    .status(true)
	                    .userId(userId)
	                    .build();
	    		}
	    	}else {
	    		return OTPValidationResponse.builder()
	                    .status(false)
	                    .build();
	    	}
	    	return OTPValidationResponse.builder()
                    .status(false)
                    .build();
		}
}
