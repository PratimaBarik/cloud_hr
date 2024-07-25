package com.app.employeePortal.category.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "min_activity")
public class MinActivity {
    @Id
    @GenericGenerator(name = "minimum_activity_id", strategy = "com.app.employeePortal.category.generator.MinimumActivityGenerator")
    @GeneratedValue(generator = "minimum_activity_id")
    @Column(name = "minimum_activity_id")
    private String minimumActivityId;

    @Column(name = "call_activity")
    private double callActivity;

    @Column(name = "event_activity")
    private double eventActivity;

    @Column(name = "task_activity")
    private double taskActivity;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "org_id")
    private String orgId;

    @Column(name = "user_id")
    private String userId;
}