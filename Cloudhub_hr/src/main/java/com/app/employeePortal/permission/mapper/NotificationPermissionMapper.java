package com.app.employeePortal.permission.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationPermissionMapper {

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("notificationId")
	private String notificationId;

	@JsonProperty("customer")
	private List<String> customer;

	@JsonProperty("contact")
	private List<String> contact;

	@JsonProperty("opportunity")
	private List<String> opportunity;

	@JsonProperty("requirementCreate")
	private List<String> requirementCreate;

	@JsonProperty("requirementClose")
	private List<String> requirementClose;

	@JsonProperty("vendor")
	private List<String> vendor;

	@JsonProperty("customerLogin")
	private List<String> customerLogin;

	@JsonProperty("vendorLogin")
	private List<String> vendorLogin;

	@JsonProperty("talentSelect")
	private List<String> talentSelect;

	@JsonProperty("talentOnboard")
	private List<String> talentOnboard;

	@JsonProperty("talentDrop")
	private List<String> talentDrop;

	@JsonProperty("task")
	private List<String> task;

	@JsonProperty("event")
	private List<String> event;

	@JsonProperty("call")
	private List<String> call;

	@JsonProperty("publishJob")
	private List<String> publishJob;

	@JsonProperty("publishJobOnWebsite")
	private List<String> publishJobOnWebsite;

	@JsonProperty("publishJobOnJobboard")
	private List<String> publishJobOnJobboard;

	@JsonProperty("unpublishJob")
	private List<String> unpublishJob;

	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;

	@JsonProperty("name")
	private String name;

}
