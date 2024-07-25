package com.app.employeePortal.category.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "feedback_library")
@JsonIgnoreProperties(ignoreUnknown = true)

public class FeedbackLibrary {
	
	
	
	
	@Id
	@JsonProperty("feedbackId")
	@Column(name="feedback_id")
	private String id;
	
	@JsonProperty("feedbackName")
	@Column(name="feedback_name")
	private String feedbackName;
	
	
	@JsonProperty("themeId")
	@Column(name = "theme_id")
    private String themeId;


}
