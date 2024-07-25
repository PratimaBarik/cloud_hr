package com.app.employeePortal.email.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.email.entity.SentMailLink;
import com.app.employeePortal.email.repository.SentMailLinkRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.organization.entity.EmailCredentialDetails;
import com.app.employeePortal.organization.repository.EmailCredentialRepository;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.registration.entity.UserSession;
import com.app.employeePortal.registration.entity.UserSettings;
import com.app.employeePortal.registration.repository.UserSessionRepository;
import com.app.employeePortal.registration.repository.UserSettingsRepository;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	UserSessionRepository userSessionRepository;
	@Autowired
	UserSettingsRepository userSettingsRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired EmailCredentialRepository emailCredentialRepository;
	@Autowired SentMailLinkRepository sentMailLinkRepository;
	@Autowired OrganizationRepository organizationRepository;
	
//	 private final JavaMailSender mailSender;
//
//	    public EmailServiceImpl(JavaMailSender mailSender) {
//	        this.mailSender = mailSender;
//	    }
//	@Autowired
//	JavaMailSender mailSender;
	
//	@Autowired
//    private JavaMailSender mailSender;
	
	@Value("${app.domain.url}")
	private String appUrl;	
	
	@Autowired
	DocumentService documentService;
	
	@Override	
public void sendMail(String from, String to, String subject, String text) throws MessagingException {
		
		String SMTP_HOST_NAME = "mail.tekorero.com"; // smtp URL
		int SMTP_HOST_PORT = 587; // port number
		String SMTP_AUTH_USER = "engage@tekorero.com"; //email of sender
	    String SMTP_AUTH_PWD = "Orange123$"; // password of sender email_id
			
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.starttls.enable", "true");

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);

		Transport transport = mailSession.getTransport("smtp");

		MimeMessage message = new MimeMessage(mailSession);

		message.setSubject(subject);
		message.setContent(text, "text/html");
		message.setSentDate(new Date());

		Address[] fromAddress = InternetAddress.parse(SMTP_AUTH_USER);// Your domain email
		message.addFrom(fromAddress);
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // Send email To (Type email ID that
																					// you want to send)

		transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();

		
		  

		}


@Override
public String prepareEmailValidationLink(String emailId,String employeeId,String orgId,String name, String orgName) {
	String token = UUID.randomUUID().toString();	
	//OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(orgId);
	/*insert to user session*/
	UserSession userSession = new UserSession();
	userSession.setEmail_id(emailId);
	userSession.setUser_id(employeeId);;
	userSession.setOrganization_id(orgId);
	userSession.setToken_id(token);
	userSession.setSession_start_time(new Date().getTime());
	userSession.setCreationDate(new Date());
	userSession.setSession_end_time(new Date().getTime() + TimeUnit.HOURS.toMillis(24));
	userSession.setLive_ind(true);
	userSessionRepository.save(userSession);
	String myvar = "<div style=' display: block; margin-top: 100px; '>"+
			"    <div style='  text-align: center;'> </div>"+
			"    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"+
			"        <div class='box-2' style='  text-align: center;'>"+
			"            <h1 style='text-align: center; padding: 10px;'>Hello "+name+" </h1>"+
			"            <p style='text-align: center;'>Welcome onboard Enterprize in a Box portal"+"!!<br /><br />To finish signing up, you just need to"+
			"                confirm that we got your"+
			"                email right.</p><br />"+
			"            <hr><br />"+
			"            <div class='' style='text-align: center;'>"+
			"                <a href='https://"+appUrl+"/activationEmail/"+employeeId+"/"+token+"/"+emailId+"/"+orgId+"/'"+
			"style='display: inline-block; background-color: transparent; border: 1px solid transparent; padding: .375rem .75rem;"+
			"                    font-size: 1rem; line-height: 1.5;text-decoration: none;  border-radius: .25rem; color: #fff; background-color: #dc3545; border-color: #dc3545;"+
			"                    transition: color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out;'>Confirm"+
			"                    your Email</a><br />"+
			"            </div>"+
			"        </div>"+
			"    </div>"+
			"</div>";

System.out.println("INSIDE PREPARE EMAIL.......");
return myvar;
}

@Override
public String forgetPasswordLink(String emailId) {

	String token = UUID.randomUUID().toString();
	UserSettings userSettings = userSettingsRepository.getUserSettingsByEmail(emailId,true);
	String orgId = null;
	String name = null;
	//String empId = null;
	if(null!=userSettings) {
		System.out.println("userid="+userSettings.getUserId());
	EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userSettings.getUserId());
	System.out.println("employeeDetails"+employeeDetails.toString());
	orgId = employeeDetails.getOrgId();
	System.out.println("orgId="+employeeDetails.getOrgId());
	System.out.println("empId="+employeeDetails.getEmployeeId());
	name = employeeDetails.getFirstName();
	//empId = employeeDetails.getEmployeeId();
	}
	UserSession userSession = new UserSession();
	userSession.setEmail_id(emailId);
	userSession.setUser_id(userSettings.getUserId());;
	userSession.setOrganization_id(orgId);
	userSession.setToken_id(token);
	userSession.setSession_start_time(new Date().getTime());
	userSession.setCreationDate(new Date());
	userSession.setSession_end_time(new Date().getTime() + TimeUnit.HOURS.toMillis(24));
	userSession.setLive_ind(true);
	userSessionRepository.save(userSession);
	 String myvar = "<div style=' display: block; margin-top: 100px; '>"+
				"   <div style=' text-align: center;'></div>"+
				"   <div style=' margin: 0 auto; width: 300px; background-color: #f4f4f4; height: 250px; border: 1px #ccc solid; padding: 50px;'>"+
				"      <div class='box-2' style=' text-align: center;'>"+
				"         <h1 style='text-align: center; padding: 10px;'>Hello "+name+" </h1>"+
				"         <p style='text-align: center;'>You recently requested to reset your password for your Korero account."+
				"            Click the button below to reset it."+
				"         </p>"+
				"         <br />"+
				"         <hr>"+
				"         <br />"+
				"            <div class='' style='text-align: center;'>"+
				"                <a href='https://hrapp.tekorero.com/setPassword'"+
				"style='display: inline-block; background-color: transparent; border: 1px solid transparent; padding: .375rem .75rem;"+
				"                    font-size: 1rem; line-height: 1.5; text-decoration: none; border-radius: .25rem; color: #fff; background-color: #dc3545; border-color: #dc3545;"+
				"                    transition: color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out;'>Reset"+
				"                    Password</a><br />"+
				"            </div>"+
				"        </div>"+
				"    </div>"+
				"</div>";


	
	 return myvar;
}


@Override
public void sendMailToOurOrganizationDuringFeatureAdded(String orgId,String text) {
	
	EmailCredentialDetails emailCredentialDetails   = emailCredentialRepository.getEmailCredentialsByOrgId(orgId);
		
	        if (null != emailCredentialDetails) {

	            String SMTP_HOST_NAME = emailCredentialDetails.getHost();
	            
	            int SMTP_HOST_PORT = emailCredentialDetails.getPort();
	            String SMTP_AUTH_USER = emailCredentialDetails.getEmail();
	            String SMTP_AUTH_PWD = emailCredentialDetails.getPassword();

	            if (SMTP_HOST_NAME.equalsIgnoreCase("mail")) {

	                try {
	                    Properties props = new Properties();
	                    props.put("mail.transport.protocol", "smtp");
	                    props.put("mail.smtp.host", SMTP_HOST_NAME);
	                    props.put("mail.smtp.auth", "false");
	                    props.put("mail.smtp.starttls.enable", "true");
	                    Session session = Session.getDefaultInstance(props);
	                    session.setDebug(true);


	                    Session mailSession = Session.getDefaultInstance(props);
	                    mailSession.setDebug(true);

	                    Transport transport = null;
	                    try {
	                        transport = session.getTransport("smtp");
	                    } catch (NoSuchProviderException e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }


	                    // Transport transport = mailSession.getTransport();
	                    MimeMessage message = new MimeMessage(mailSession);
	                    message.setFrom(new InternetAddress(SMTP_AUTH_USER));

	                    message.setSubject("New Featrure is added ");
	                    message.setContent(text, "text/html");
	                    message.setSentDate(new Date());

	                    Address[] fromAddress = InternetAddress.parse(SMTP_AUTH_USER);
	                    message.addFrom(fromAddress);
	                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("sales@tekorero.com"));


	                    transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
	                    transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
	                    transport.close();


	                    //insertToSentEmailLink(userId, orgId);


	                } catch (AddressException ae) {
	                    ae.printStackTrace();
	                } catch (MessagingException me) {
	                    me.printStackTrace();
	                }
	            } else if (SMTP_HOST_NAME.contains("outlook.com")) {
	            	
	            	System.out.println("outlook mail.com");

	                try {
		    		   /* Properties props = new Properties();
		    		    props.put("mail.smtp.auth", "true");
		    		    props.put("mail.smtp.starttls.enable", "true");
		    		    props.put("mail.smtp.host", SMTP_HOST_NAME);
		    		    props.put("mail.smtp.port", SMTP_HOST_PORT);*/

	                    Properties props = new Properties();
//		               props.put("mail.smtp.socketFactory.port", "587");
//		               props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		               props.put("mail.smtp.socketFactory.fallback", "true");
	                    props.put("mail.smtp.host", "smtp-mail.outlook.com");
	                    props.put("mail.smtp.port", "587");
	                    props.put("mail.smtp.starttls.enable", "true");
	                    props.put("mail.smtp.auth", "true");

	                    Session session = Session.getInstance(props,
	                            new javax.mail.Authenticator() {
	                                protected PasswordAuthentication getPasswordAuthentication() {
	                                    return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
	                                }
	                            });

	                    session.setDebug(true);

	                    Message message = new MimeMessage(session);
	                    message.setFrom(new InternetAddress(SMTP_AUTH_USER));

	                    message.addRecipient(Message.RecipientType.TO,
	                            new InternetAddress("priyabhasinim@gmail.com"));

	                    message.setSubject("features added");
	                    //message.setText(body);
	                    message.setSentDate(new Date());
	                    //   message.setContent(body,"text/html");
	                    message.setText(text);
	                    message.setSentDate(new Date());
	                    message.setContent(text, "text/html");

	                    Transport.send(message);



	                } catch (AddressException ae) {
	                    ae.printStackTrace();
	                } catch (MessagingException me) {
	                    me.printStackTrace();
	                }


	            }
	        }
	}
	

@Override
public void sendMailFromAdmin(String to, String subject, String body, String userId, String orgId)
		throws MessagingException, IOException {
	EmailCredentialDetails emailCredentialDetails = emailCredentialRepository.getCredentialDetailsByUserId(userId);
	
	if(null !=emailCredentialDetails) {
	
	String SMTP_HOST_NAME =emailCredentialDetails.getHost();
	
	int SMTP_HOST_PORT = emailCredentialDetails.getPort(); 
	String SMTP_AUTH_USER = emailCredentialDetails.getEmail();
	String SMTP_AUTH_PWD = emailCredentialDetails.getPassword();
	
	System.out.println("password@@@@@@@@"+SMTP_AUTH_PWD);
	System.out.println("userId@@@@@@@@@@@"+SMTP_AUTH_USER);
	
	if(SMTP_HOST_NAME.equalsIgnoreCase("mail")) {
		
		System.out.println("inside maillll");
		 try {
	        	Properties props = new Properties();
	        	props.put("mail.transport.protocol", "smtp");
	    		props.put("mail.smtp.host", SMTP_HOST_NAME);
	    		props.put("mail.smtp.auth", "false");
	    		props.put("mail.smtp.starttls.enable", "true");
	    	    Session session = Session.getDefaultInstance(props); 
	    	    session.setDebug(true);

	    	    
	    	    Session mailSession = Session.getDefaultInstance(props);
	    	    mailSession.setDebug(true);
	    	    
	    	    Transport transport = null;
				try {
					 transport = session.getTransport("smtp");
				} catch (NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    	    
	    	    
	    	   // Transport transport = mailSession.getTransport();
	    	    MimeMessage message = new MimeMessage(mailSession);
	    	    message.setFrom(new InternetAddress(SMTP_AUTH_USER));
	  	            
	                message.setSubject(subject);
	                message.setContent(body,"text/html");
		       	    message.setSentDate(new Date());

		       	    Address[] fromAddress = InternetAddress.parse(SMTP_AUTH_USER);
		    		message.addFrom(fromAddress);
		    		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
		       	    
		       	    
	        		transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER,SMTP_AUTH_PWD);
	        		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		    		transport.close();
	      
		    	
		    		insertToSentEmailLink(userId,orgId);

		            
	        }
	        catch (AddressException ae) {
	            ae.printStackTrace();
	        }
	        catch (MessagingException me) {
	            me.printStackTrace();
	        }	
    	}else if(SMTP_HOST_NAME.contains("outlook.com")) {
    		
    		System.out.println("inside outlook");
		
    		
    		System.out.println("portttt"+SMTP_HOST_PORT);
    		try {
    		   /* Properties props = new Properties();
    		    props.put("mail.smtp.auth", "true");
    		    props.put("mail.smtp.starttls.enable", "true");
    		    props.put("mail.smtp.host", SMTP_HOST_NAME);
    		    props.put("mail.smtp.port", SMTP_HOST_PORT);*/
    			
    			 Properties props = new Properties();
//               props.put("mail.smtp.socketFactory.port", "587");
//               props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//               props.put("mail.smtp.socketFactory.fallback", "true");
               props.put("mail.smtp.host", "smtp-mail.outlook.com");
               props.put("mail.smtp.port", "587");
               props.put("mail.smtp.starttls.enable","true");
               props.put("mail.smtp.auth", "true");
    		    


    		   /* Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(SMTP_AUTH_USER,SMTP_AUTH_PWD);
                            }
                        });*/
               
               Session session = Session.getInstance(props,
            		   new javax.mail.Authenticator() {
            		     protected PasswordAuthentication getPasswordAuthentication() {
            		         return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
            		     }
            		   });

            		 //session = Session.getInstance(props, this);
               
    		    session.setDebug(true);
    		    
    		    Message message = new MimeMessage(session);
    	        message.setFrom(new InternetAddress(SMTP_AUTH_USER));
	            //message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    	        
    	        message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(to));

                message.setSubject(subject);
                //message.setText(body);
           	    message.setSentDate(new Date());
             //   message.setContent(body,"text/html");
           	    message.setText(body);
        	    message.setSentDate(new Date());
             message.setContent(body,"text/html");
          
               Transport.send(message);
     
	    		insertToSentEmailLink(userId,orgId);

               
       }
       catch (AddressException ae) {
           ae.printStackTrace();
       }
       catch (MessagingException me) {
           me.printStackTrace();
       }	
		

    	}
}
}


private String insertToSentEmailLink(String userId, String orgId) {
	SentMailLink sentMailLink = new SentMailLink();
	
	sentMailLink.setOrgId(orgId);
	sentMailLink.setUserId(userId);
	sentMailLink.setCreationDate(new Date());
	sentMailLink.setLiveInd(true);
	
	String sentEmailId = sentMailLinkRepository.save(sentMailLink).getId();
	
	return sentEmailId;
	}


//@Override
//public void sendEmailWithAttachment(String toEmail, String subject, String body, MultipartFile attachment) throws MessagingException {
//	 MimeMessage message = mailSender.createMimeMessage();
//
//     MimeMessageHelper helper = new MimeMessageHelper(message, true);
//     helper.setTo(toEmail);
//     helper.setSubject(subject);
//     helper.setText(body);
//
//     // Attach the file
//     if (attachment != null && !attachment.isEmpty()) {
//         helper.addAttachment(attachment.getOriginalFilename(), attachment);
//     }
//
//     mailSender.send(message);
// }
	



//@Override
//public void sendEmailWithAttachment(String from, String to, String subject, String text, File attachment) throws AddressException, MessagingException {
//	String SMTP_HOST_NAME = "mail.tekorero.com"; // smtp URL
//	int SMTP_HOST_PORT = 587; // port number
//	String SMTP_AUTH_USER = "engage@tekorero.com"; //email of sender
//    String SMTP_AUTH_PWD = "Orange123$"; // password of sender email_id
//
//	    Properties props = new Properties();
//	    props.put("mail.transport.protocol", "smtp");
//	    props.put("mail.smtp.host", SMTP_HOST_NAME);
//	    props.put("mail.smtp.auth", "false");
//	    props.put("mail.smtp.starttls.enable", "true");
//
//	    Session mailSession = Session.getDefaultInstance(props);
//	    mailSession.setDebug(true);
//
//	    Transport transport = mailSession.getTransport("smtp");
//
//	    MimeMessage message = new MimeMessage(mailSession);
//
//	    message.setSubject(subject);
//	    message.setContent(text, "text/html");
//	    message.setSentDate(new Date());
//
//	    Address[] fromAddress = InternetAddress.parse(SMTP_AUTH_USER);// Your domain email
//	    message.addFrom(fromAddress);
//	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // Send email To (Type email ID that you want to send)
//
//	    // Create the multipart message
//	    MimeMultipart multipart = new MimeMultipart();
//
//	    // Add the text part
//	    MimeBodyPart textPart = new MimeBodyPart();
//	    textPart.setText(text, "utf-8", "html");
//	    multipart.addBodyPart(textPart);
//
//	    // Add the file attachment
//	    if (attachment != null && attachment.exists()) {
//	        MimeBodyPart attachmentPart = new MimeBodyPart();
//	        DataSource source = new FileDataSource(attachment);
//	        attachmentPart.setDataHandler(new DataHandler(source));
//	        attachmentPart.setFileName(attachment.getName());
//	        multipart.addBodyPart(attachmentPart);
//	    }
//
//	    // Set the content of the message to the multipart
//	    message.setContent(multipart);
//
//	    transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
//	    transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
//	    transport.close();
//	
//}

@Override
public void sendEmailWithAttachment(String from, String to, String subject, String text, MultipartFile attachment) throws AddressException, MessagingException {
	String SMTP_HOST_NAME = "mail.tekorero.com"; // smtp URL
	int SMTP_HOST_PORT = 587; // port number
	String SMTP_AUTH_USER = "engage@tekorero.com"; //email of sender
    String SMTP_AUTH_PWD = "Orange123$"; // password of sender email_id

    Properties props = new Properties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", SMTP_HOST_NAME);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.port", SMTP_HOST_PORT);
    props.put("mail.smtp.ssl.trust", SMTP_HOST_NAME);

    Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
        }
    });
    mailSession.setDebug(true);

    Transport transport = mailSession.getTransport("smtp");

    MimeMessage message = new MimeMessage(mailSession);

    message.setSubject(subject);
    message.setSentDate(new Date());

    Address fromAddress = new InternetAddress(from); // sender's email address
    message.setFrom(fromAddress);
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // Send email To

    // Create the multipart message
    MimeMultipart multipart = new MimeMultipart();

    // Add the text part
    MimeBodyPart textPart = new MimeBodyPart();
    textPart.setContent(text, "text/html; charset=utf-8");
    multipart.addBodyPart(textPart);

    // Add the file attachment  
    if (attachment != null) {
    	File actualfile = documentService.convertMultiPartFileToFile(attachment);
        MimeBodyPart attachmentPart = new MimeBodyPart();
        DataSource source = new FileDataSource(actualfile);
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName(attachment.getName());
        multipart.addBodyPart(attachmentPart);
    }

    // Set the content of the message to the multipart
    message.setContent(multipart);

    transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
    transport.sendMessage(message, message.getAllRecipients());
    transport.close();
}



//@Override
//public String sendEmailWithAttachment(String from, String to, String subject, String text, MultipartFile[] files)
//		throws AddressException, MessagingException {
//	// TODO Auto-generated method stub
//	return null;
//}

//public String sendEmailWithAttachment(String from, String to, String subject, String message, MultipartFile[] files) throws MessagingException {
//    MimeMessage msg = mailSender.createMimeMessage();
//    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//    helper.setTo(to);
//    helper.setSubject(subject);
//    helper.setText(message, true);
//    helper.setFrom(from);
//    Arrays.asList(files).stream().forEach(multipartFile -> {
//        try {
//            helper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    });
//    mailSender.send(msg);
//    return "mail Sent successfully";
//}
	
}



