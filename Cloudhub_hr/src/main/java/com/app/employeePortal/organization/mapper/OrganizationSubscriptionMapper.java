package com.app.employeePortal.organization.mapper;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationSubscriptionMapper {
	
	@JsonProperty("organizationId")
	private String organizationId;
		
	@JsonProperty("subscriptionType")
	private String subscriptionType;
	
	@JsonProperty("SubscriptionEndDate")
	private String SubscriptionEndDate;

	
}
