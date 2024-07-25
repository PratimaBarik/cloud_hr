//package com.app.employeePortal.email.service;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SesEmailService {
//
//	 private final JavaMailSender mailSender;
//
//	    public SesEmailService(JavaMailSender mailSender) {
//	        this.mailSender = mailSender;
//	    }
//
//	    public void sendEmail(String from, String to, String subject, String message) throws MessagingException {
//
//	        MimeMessage msg = mailSender.createMimeMessage();
//	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//	        helper.setTo(to);
//	        helper.setSubject(subject);
//	        helper.setText(message, true);
//	        helper.setFrom(from);
//	        mailSender.send(msg);
//	       // helper.send(bcc);
//	  }
//	    
//	    public String forgotPasswordEmail(String email) throws MessagingException {
//	    	String from="support@loadtriping.com";
//	    	String sub="Forget Password";
//	    	String msg="hy";
//	    	String mail=email;
//	    	sendEmail(from,sub,mail,msg);
//			return "Email send Succecfully";
//	    }
//}
