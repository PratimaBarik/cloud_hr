package com.app.employeePortal.support.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TicketResponseMapper {
	
	@JsonProperty("ticketId")
	private String ticketId;
	
	@JsonProperty("ticketName")
	private String ticketName;
	
	@JsonProperty("emailId")
	private String emailId;
	
	@JsonProperty("mobileNumber")
	private String mobileNumber;
	
	@JsonProperty("OrderId")
	private String OrderId;
//
//	@JsonProperty("userId")
//	private String userId;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("startTime")
	private long startTime;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("endTime")
	private long endTime;
	
	@JsonProperty("ticketTypeName")
	private String ticketTypeName;

	@JsonProperty("products")
	 List<ProductResponseMapper> products;
	
	@JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;
}
