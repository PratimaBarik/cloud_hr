package com.app.employeePortal.support.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TicketRequestMapper {

	@JsonProperty("ticketName")
	private String ticketName;
	
	@JsonProperty("emailId")
	private String emailId;
	
	@JsonProperty("mobileNumber")
	private String mobileNumber;
	
	@JsonProperty("OrgId")
	private String OrgId;

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("startTime")
	private long startTime;
	
	@JsonProperty("ticketTypeId")
	private String ticketTypeId;
	
	@JsonProperty("orderId")
	private String orderId;
	
	@JsonProperty("productIds")
	 List<String> productIds;
}
