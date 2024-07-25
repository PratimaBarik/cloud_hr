package com.app.employeePortal.email.service;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.web.multipart.MultipartFile;

public interface EmailService {

	public String forgetPasswordLink(String emailId);
	
	public String prepareEmailValidationLink(String emailId,String employeeId,String orgId,String name, String orgName);
	
	public void sendMail(String from, String to, String subject, String text) throws MessagingException;
	
	public void sendMailToOurOrganizationDuringFeatureAdded(String orgId,String text);

	public void sendMailFromAdmin(String to,String subject,String body,String userId,String orgId) throws MessagingException, IOException;

//	public void sendEmailWithAttachment(String toEmail, String subject, String body, MultipartFile attachment) throws MessagingException;

	void sendEmailWithAttachment(String from, String to, String subject, String text, MultipartFile files)
			throws AddressException, MessagingException;

//	public String sendEmailWithAttachment(String from, String to, String subject, String text,MultipartFile[] files) throws AddressException, MessagingException;
//
//	void sendEmailWithAttachment(String from, String to, String subject, String text, File attachment)
//			throws AddressException, MessagingException;
}
