package com.app.employeePortal.category.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketTypeRequestMapper {
	
	@JsonProperty("ticketType")
	private String ticketType;

	@JsonProperty("ticketTypeId")
	private String ticketTypeId;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("EditInd")
	private boolean EditInd;

}
