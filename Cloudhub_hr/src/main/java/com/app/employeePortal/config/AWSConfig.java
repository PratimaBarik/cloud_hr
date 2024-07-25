//package com.app.employeePortal.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
//
//import io.awspring.cloud.ses.SimpleEmailServiceJavaMailSender;
//
//@Configuration
//public class AWSConfig {
//
//    @Value("${cloud.aws.credentials.access-key}")
//    private String accessKeyId;
//
//    @Value("${cloud.aws.credentials.secret-key}")
//    private String secretAccessKey;
//
//    @Value("${cloud.aws.region.static}")
//    private String region;
//
//    @Bean
//    public JavaMailSender javaMailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
//        return new SimpleEmailServiceJavaMailSender(amazonSimpleEmailService);
//    }
//
//    @Bean
//    public AmazonSimpleEmailService amazonSimpleEmailService() {
//        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
//        return AmazonSimpleEmailServiceClientBuilder
//                .standard()
//                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredential