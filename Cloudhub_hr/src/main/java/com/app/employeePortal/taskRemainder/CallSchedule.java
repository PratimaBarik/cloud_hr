package com.app.employeePortal.taskRemainder;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallSchedule {
    private String email;
    private String body;
    private String subject;
    private Date date;
    private String timeZone;
}
