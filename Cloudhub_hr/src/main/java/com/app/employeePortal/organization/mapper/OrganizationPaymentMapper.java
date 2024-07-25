package com.app.employeePortal.organization.mapper;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationPaymentMapper {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("organizationId")
	private String organizationId;
		
	@JsonProperty("paymentGateway")
	private String paymentGateway;
	
	@JsonProperty("accesskey")
	private String accesskey;

	@JsonProperty("secreateKey")
	private String secreateKey;
}
