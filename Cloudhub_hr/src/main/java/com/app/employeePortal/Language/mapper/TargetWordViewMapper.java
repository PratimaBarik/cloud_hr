package com.app.employeePortal.Language.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetWordViewMapper{
	@JsonProperty("translatedText")
	private String translatedText;

	@JsonProperty("detectedSourceLanguage")
	private String detectedSourceLanguage;
}
