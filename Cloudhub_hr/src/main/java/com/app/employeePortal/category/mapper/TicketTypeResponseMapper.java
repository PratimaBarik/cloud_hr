package com.app.employeePortal.category.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketTypeResponseMapper {
	
	@JsonProperty("ticketType")
	private String ticketType;

	@JsonProperty("ticketTypeId")
	private String ticketTypeId;
	
//	@JsonProperty("userId")
//	private String userId;

//	@JsonProperty("organizationId")
//	private String organizationId;
	
	@JsonProperty("editInd")
	private boolean editInd;

	@JsonProperty("updationDate")
	private String updationDate;
	
//	@JsonProperty("creationDate")
//	private String creationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("liveInd")
	private boolean liveInd;

}
