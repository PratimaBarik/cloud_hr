package com.app.employeePortal.email.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.File;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.app.employeePortal.email.service.EmailService;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1/email")
public class EmailController {
	 
	@Autowired EmailService mailService;
	@PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam ("from") String from,
                                            @RequestParam("to") String to,
                                            @RequestParam("subject") String subject,
                                            @RequestParam("text") String text,
                                            @RequestParam(name = "file", required = false) MultipartFile files,@RequestHeader("Authorization") String authorization)throws MessagingException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
		            mailService.sendEmailWithAttachment(from, to, subject, text, files);
		            String msg = "Email sent successfully";
		            return new ResponseEntity<>(msg, HttpStatus.OK);
//		        }
//		        } else {
//		            status = emailService.sendEmail(fromEmail, toEmail, subject, message);
//		        }
		        
    
	}
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/send/witoutAttach")
    public ResponseEntity<String> sendEmailWithOutAttachment(@RequestParam ("from") String from,
                                            @RequestParam("to") String to,
                                            @RequestParam("subject") String subject,
                                            @RequestParam("text") String text,
                                            @RequestHeader("Authorization") String authorization)throws MessagingException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
		            mailService.sendMail(from, to, subject, text);
		            String msg = "Email sent successfully";
		            return new ResponseEntity<>(msg, HttpStatus.OK);
//		        }
//		        } else {
//		            status = emailService.sendEmail(fromEmail, toEmail, subject, message);
//		        }
		        
    
	}
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
//	@PostMapping("/sendEmailWithAttachment")
//    public String sendEmail(
//            @RequestParam String toEmail,
//            @RequestParam String subject,
//            @RequestParam String body,
//            @RequestParam("attachment") MultipartFile attachment,@RequestHeader("Authorization") String authorization) {
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//		try {
//			mailService.sendEmailWithAttachment(toEmail, subject, body, attachment);
//            return "Email sent successfully";
//        } catch (MessagingException e) {
//            return "Error while sending email: MessagingException " + e.getMessage();
//        }
//    }	
//	return null; 
//	}

}
