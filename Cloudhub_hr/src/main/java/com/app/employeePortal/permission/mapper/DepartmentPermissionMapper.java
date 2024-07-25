package com.app.employeePortal.permission.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class DepartmentPermissionMapper {
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("roleTypeId")
	private String roleTypeId;

	@JsonProperty("talent")
	private List<String> talent;

	@JsonProperty("vendor")
	private List<String> vendor;

	@JsonProperty("customer")
	private List<String> customer;

	@JsonProperty("opportunity")
	private List<String> opportunity;

	@JsonProperty("requirement")
	private List<String> requirement;

	@JsonProperty("publish")
	private List<String> publish;

	@JsonProperty("pulse")
	private List<String> pulse;

	@JsonProperty("contact")
	private List<String> contact;

	@JsonProperty("assessment")
	private List<String> assessment;

	@JsonProperty("leads")
	private List<String> leads;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("test")
	private List<String> test;
	
	@JsonProperty("program")
	private List<String> program;
	
	@JsonProperty("course")
	private List<String> course;
	
	@JsonProperty("hours")
	private List<String> hours;

	@JsonProperty("task")
	private List<String> task;
	
	@JsonProperty("comercial")
	private List<String> comercial;
	
	@JsonProperty("location")
	private List<String> location;
	
	@JsonProperty("leave")
	private List<String> leave;
	
	@JsonProperty("expense")
	private List<String> expense;
	
	@JsonProperty("mileage")
	private List<String> mileage;
	
	@JsonProperty("user")
	private List<String> user;
	
	@JsonProperty("account")
	private List<String> account;
	
	@JsonProperty("order")
	private List<String> order;
	
	@JsonProperty("material")
	private List<String> material;
	
	@JsonProperty("supplier")
	private List<String> supplier;
	
	@JsonProperty("inventory")
	private List<String> inventory;
	
	@JsonProperty("refurbish")
	private List<String> refurbish;
	
	@JsonProperty("dashboard")
	private List<String> dashboard;
	
	@JsonProperty("settings")
	private List<String> settings;
	
	@JsonProperty("junk")
	private List<String> junk;
	
	@JsonProperty("investor")
	private List<String> investor;
	
	@JsonProperty("investorContact")
	private List<String> investorContact;
	
	@JsonProperty("deal")
	private List<String> deal;
	
	@JsonProperty("pitch")
	private List<String> pitch;
	
	@JsonProperty("repository")
	private List<String> repository;
	
	@JsonProperty("shipper")
	private List<String> shipper;
	
	@JsonProperty("plant")
	private List<String> plant;
	
	@JsonProperty("teams")
	private List<String> teams;
	
	@JsonProperty("basic")
	private List<String> basic;
	
	@JsonProperty("catalog")
	private List<String> catalog;
	
	@JsonProperty("payment")
	private List<String> payment;
	
	@JsonProperty("collection")
	private List<String> collection;
	
	@JsonProperty("holiday")
	private List<String> holiday;
	
	@JsonProperty("topic")
	private List<String> topic;
	
	@JsonProperty("procurement")
	private List<String> procurement;
	
	@JsonProperty("subscription")
	private List<String> subscription;
	
	@JsonProperty("production")
	private List<String> production;
	
	@JsonProperty("report")
	private List<String> report;
	
	@JsonProperty("dataRoom")
	private List<String> dataRoom;

	@JsonProperty("scanner")
	private List<String> scanner;
	
	@JsonProperty("quality")
	private List<String> quality;
	
	@JsonProperty("club")
	private List<String> club;
	
	@JsonProperty("calender")
	private List<String> calender;
}