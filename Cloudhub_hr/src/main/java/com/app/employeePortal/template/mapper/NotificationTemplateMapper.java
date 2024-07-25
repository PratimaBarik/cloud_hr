package com.app.employeePortal.template.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationTemplateMapper {

	@JsonProperty("userId")
    private String userId;

    @JsonProperty("orgId")
    private String orgId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("notificationName")
    private String notificationName;

    @JsonProperty("type")
    private String type;


    @JsonProperty("notificationTemplateId")
    private String notificationTemplateId;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("description")
    private String description;
    
    //@JsonProperty("subject")
   // private String subject;

   
}
