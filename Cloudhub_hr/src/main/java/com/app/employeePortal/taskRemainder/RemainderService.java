package com.app.employeePortal.taskRemainder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RemainderService {

    @Autowired
    private Scheduler scheduler;

//    @PostMapping("/scheduleEmail")
    public void scheduleEmail(CallSchedule scheduleEmailRequest) {

        try {
//            String str = "2024-04-17 11:57";//remainder Date time
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//            LocalDateTime dateTime1 = LocalDateTime.parse(str, formatter);
            ZoneId zone = ZoneId.of("Asia/Kolkata");//time Zone
//
//            ZonedDateTime dateTime = ZonedDateTime.of(scheduleEmailRequest.getDate(),zone);
            
            Instant instant = scheduleEmailRequest.getDate().toInstant();

            // Create a ZonedDateTime with the specified time zone
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Kolkata")).minusMinutes(15);
            		
//        	ZonedDateTime dateTime = scheduleEmailRequest.getDate();
//        	Date dateTime = scheduleEmailRequest.getDate();
//            if(dateTime.isBefore(ZonedDateTime.now())) {
//                ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
//                        "dateTime must be after current time");
////                return ResponseEntity.badRequest().body(scheduleEmailResponse);
//            }

            JobDetail jobDetail = buildJobDetail(scheduleEmailRequest);
            Trigger trigger = buildJobTrigger(jobDetail, zonedDateTime);
            scheduler.scheduleJob(jobDetail, trigger);
//            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true,
//                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
//            return ResponseEntity.ok(scheduleEmailResponse);
        } catch (SchedulerException ex) {
           // logger.error("Error scheduling email", ex);

//            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
//                    "Error scheduling email. Please try later!");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
        }
    }

    private JobDetail buildJobDetail(CallSchedule scheduleEmailRequest) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", scheduleEmailRequest.getEmail());
        jobDataMap.put("subject", scheduleEmailRequest.getSubject());
        jobDataMap.put("body", scheduleEmailRequest.getBody());
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
