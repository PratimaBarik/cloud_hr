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
@Table(name = "theme")
@JsonIgnoreProperties(ignoreUnknown = true)

public class Theme {
	

	@Id
	@JsonProperty("themeId")
	@Column(name = "theme_id")
    private String themeId;
	
	
	@JsonProperty("themeName")
	@Column(name="theme_name")
	private String themeName;
	
	
	
}
