package com.app.employeePortal.otp.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.otp.mapper.OTPValidationResponse;
import com.app.employeePortal.otp.mapper.OtpMapper;
import com.app.employeePortal.otp.service.OtpService;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/otp")
@CrossOrigin(maxAge = 3600)
public class OTPController {

    final OtpService otpService;

    public OTPController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generateOTP")
    public  ResponseEntity<?>sendotp(@RequestBody OtpMapper otpDTO) throws TemplateException, IOException {
    	return ResponseEntity.ok(otpService.sendOTP(otpDTO));
    }
    
    @PostMapping("/validateOtp")
    public ResponseEntity<OTPValidationResponse> validateOTP(@RequestBody OtpMapper otpDTO) {
        return ResponseEntity.ok(otpService.validateOTP2(otpDTO));
    }
    
    @PostMapping("/user/generateOTP")
    public  ResponseEntity<?>sendotpForUser(@RequestBody OtpMapper otpDTO) throws TemplateException, IOException {
    	return ResponseEntity.ok(otpService.sendotpForUser(otpDTO));
    }
    
    @PostMapping("/user/validateOtp")
    public ResponseEntity<OTPValidationResponse> validateOTPForUser(@RequestBody OtpMapper otpDTO) {
        return ResponseEntity.ok(otpService.validateOTPForUser(otpDTO));
    }
    
    @PostMapping("/user/validateOtp/email/link")
    public ResponseEntity<OTPValidationResponse> validateOTPForUserEmailLink(@RequestBody OtpMapper otpDTO) {
        return ResponseEntity.ok(otpService.validateOTPForUserEmailLink(otpDTO));
    }
    
    @PostMapping("/user/link/one-to-another/generateOTP")
    public  ResponseEntity<?>sendotpForLinkUser(@RequestBody OtpMapper otpDTO) throws TemplateException, IOException {
    	return ResponseEntity.ok(otpService.sendotpForLinkUser(otpDTO));
    }

}
