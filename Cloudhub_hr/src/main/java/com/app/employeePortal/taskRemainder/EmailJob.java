package com.app.employeePortal.taskRemainder;

import javax.mail.MessagingException;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.app.employeePortal.email.service.EmailService;

@Component
public class EmailJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    EmailService emailService;

//    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");
        String recipientEmail = jobDataMap.getString("email");

        try {
			emailService.sendMail("support@innoverenit.com", recipientEmail, subject, body);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//calling email service for send mail
    }

//    private void sendMail(String fromEmail, String toEmail, String subject, String body) {
//        try {
//            logger.info("Sending Email to {}", toEmail);
//            MimeMessage message = mailSender.createMimeMessage();
//
//            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
//            messageHelper.setSubject(subject);
//            messageHelper.setText(body, true);
//            messageHelper.setFrom(fromEmail);
//            messageHelper.setTo(toEmail);
//
//            mailSender.send(message);
//        } catch (MessagingException ex) {
//            logger.error("Failed to send email to {}", toEmail);
//        }
//    }
}
