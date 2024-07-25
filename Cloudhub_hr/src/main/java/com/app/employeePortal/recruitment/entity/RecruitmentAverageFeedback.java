package com.app.employeePortal.recruitment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "recruitment_average_feedback")
public class RecruitmentAverageFeedback {
    
    @Id
    @GenericGenerator(name = "recruitment_average_feedback_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentAverageFeedbackGenerator")
    @GeneratedValue(generator = "recruitment_average_feedback_id")
    
    @Column(name = "recruitment_average_feedback_id")
    private String recruitmentAverageFeedbackId;
    
    @Column(name = "candidate_id")
    private String candidateId;
    
    @Column(name = "average")
    private float average;
}
