package com.app.employeePortal.permission.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionUsersMapper {
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("user")
	private List<String> userId;

}
