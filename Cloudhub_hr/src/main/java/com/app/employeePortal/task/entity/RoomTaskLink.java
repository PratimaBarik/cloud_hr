package com.app.employeePortal.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name="room_task_link")
public class RoomTaskLink {
    @Id
    @GenericGenerator(name = "roomTaskLinkId", strategy = "com.app.employeePortal.task.generator.RoomTaskLinkGenerator")
    @GeneratedValue(generator = "roomTaskLinkId")

    @Column(name="room_task_link_id")
    private String roomTaskLinkId;

    @Column(name="task_id")
    private String taskId;

    @Column(name="room_id")
    private String roomId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

    @Column(name="complition_status")
    private String complitionStatus;

}
