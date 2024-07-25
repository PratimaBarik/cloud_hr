package com.app.employeePortal.candidate.mapper;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.call.mapper.DonotCallMapper;
import com.app.employeePortal.employee.mapper.EmployeeMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CandidateViewMapper {
	@JsonProperty("candidateId")
	private String candidateId;
	
	//@NotEmpty(message="first name should not empty")
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("middleName")
	private String middleName;
	
	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("mobileNumber")
	private String mobileNumber;
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@JsonProperty("emailId")
	private String emailId;
	
	@JsonProperty("linkedin")
	private String linkedin;
	
	
	@JsonProperty("notes")
	private String notes;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("address")
	private List<AddressMapper> address;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty( "salutation")
	private String salutation;
	
	@JsonProperty( "availableDate")
	private String availableDate;
	
	@JsonProperty( "billing")
	private String billing;
	
	
	@JsonProperty("linkedin_public_url")
	private String linkedin_public_url;
	
	@JsonProperty("tag_with_company")
	private String tag_with_company;
	
	@JsonProperty("departmentName")
	private String departmentName;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("designation")
	private String designation;
	
	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("countryDialCode1")
	private String countryDialCode1;
	
	@JsonProperty("currency")
	private String currency;
	
	
	@JsonProperty( "active")
	private boolean active;

	@JsonProperty("dateOfBirth")
	private String dateOfBirth;
	
	@JsonProperty("idProof")
	private String idProof;
	
	@JsonProperty("idNumber")
	private String idNumber;
	
	@JsonProperty("skillList")
	private List<String> skillList;
	
	@JsonProperty("skill")
	private List<SkillSetMapper> skill;
	
	@JsonProperty("education")
	private String education;
	
	@JsonProperty("experience")
	private float experience;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("workLocation")
	private String workLocation;
	
	@JsonProperty("workType")
	private String workType;
	
	@JsonProperty("gender")
	private String gender;
	
	@JsonProperty("nationality")
	private String nationality;

	@JsonProperty("fullName")
	private String fullName;

	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("ownerName")
	private String ownerName;
	
	@JsonProperty("partnerId")
	private String partnerId;
	
	@JsonProperty("partnerName")
	private String partnerName;
	

	@JsonProperty("ownerImageId")
	private String ownerImageId;

	@JsonProperty("skill")
	private String  skillName;

	@JsonProperty("designationTypeId")
	private String designationTypeId;
	
	@JsonProperty("currentCtc")
	private String currentCtc;
	
	@JsonProperty("roleType")
	private String roleType;

	@JsonProperty("roleTypeId")
	private String roleTypeId;
	
	@JsonProperty("noticePeriod")
	private long noticePeriod;
	
	@JsonProperty( "modifiedAt")
	private String modifiedAt;
	
	@JsonProperty("match")
	private String match;
	
	@JsonProperty("recruiterList")
	private List<EmployeeMapper> recruiterList;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("benifit")
	private String benifit;
	
	@JsonProperty("costType")
	private String costType;
	
	@JsonProperty("noticeDetail")
	private String noticeDetail;

	@JsonProperty("whatsApp")
	private String whatsApp;

	@JsonProperty("candidateInd")
	private boolean candidateInd;
	
	@JsonProperty("blockListInd")
	private boolean blockListInd;
	
	@JsonProperty("doNotCallInd")
	private boolean doNotCallInd;
	
	@JsonProperty("allowSharing")
	private String allowSharing ;
	
	@JsonProperty("workPreference")
	private String workPreference;
	
	@JsonProperty("channel")
	private String channel;
	
	@JsonProperty("matchSkill")
	private List<String> matchSkill;
	
	@JsonProperty("msNo")
	private int msNo;
	
	@JsonProperty("doNotCall")
	private DonotCallMapper doNotCall;
	
	@JsonProperty("empInd")
    private boolean empInd;
	
	@JsonProperty("currentCtcCurency")
	private String currentCtcCurency;
	
	@JsonProperty("partnerContact")
	private String partnerContact;
	
	@JsonProperty("videoClipsId")
	private String videoClipsId;

	@JsonProperty("distance")
	private int distance;
	
	@JsonProperty("score")
	private int score;
	
	@JsonProperty( "candiProcessInd")
	private boolean candiProcessInd;
	
	@JsonProperty("actualEndDate")
	private String actualEndDate;
	
	@JsonProperty("finalBilling")
	private float finalBilling;
	
	@JsonProperty("billableHour")
	private float billableHour;
	
	@JsonProperty("onboardCurrency")
	private String onboardCurrency;
	
	@JsonProperty("projectName")
	private String projectName;
	
	@JsonProperty("onboardDate")
	private String onboardDate;
	
	@JsonProperty("requirementName")
	private String requirementName;
	
	@JsonProperty("jobOrder")
	private String jobOrder;

	@JsonProperty("liveInd")
    private boolean liveInd;
	
	@JsonProperty("reInStateInd")
	private boolean reInStateInd;
	
	@JsonProperty("tAndCInd")
	private boolean tAndCInd;
	
	@JsonProperty("projectId")
	private String projectId;
	
	@JsonProperty("customerName")
	private String customerName;
	
	@JsonProperty("orgImgId")
	private String orgImgId;
	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("sector")
	private String sector;
	
	@JsonProperty("preferredDistance")
	private int preferredDistance;

	@JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;
}
