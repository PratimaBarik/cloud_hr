package com.app.employeePortal.customer.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInvoiceMapper {

	@JsonProperty("customerInvoiceId")
	private String customerInvoiceId;
	
	@JsonProperty("invoiceNumber")
	private String invoiceNumber;
	
	@JsonProperty("invoiceAmount")
	private String invoiceAmount;
		
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("customerId")
	private String customerId;
	
	//@JsonProperty("userId")
	//private String userId;
	
	@JsonProperty("address")
	private List<AddressMapper> address;
	
	@JsonProperty("documentId")
	private String documentId;
	
}
