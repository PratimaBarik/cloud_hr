package com.app.employeePortal.candidate.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "candidate")
public class CandidateDetails {
	
	@Id
	@GenericGenerator(name = "candidate_details_id", strategy = "com.app.employeePortal.candidate.generator.CandidateDetailsGenerator")
    @GeneratedValue(generator = "candidate_details_id")
	
	@Column(name="candidate_details_id")
	private String candidateDetailsId;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="last_name")
	private String lastName;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="mobile_number")
	private String mobileNumber;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="whatsapp_number")
	private String phoneNumber;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="email_id")
	private String emailId;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="linkedin")
	private String linkedin;
	
	@Lob
	@Column(name="notes")
	private String notes;
	

	@Column(name="user_id")
	private String userId;
	
	@Column(name="organization_id")
	private String organizationId;
	
	
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name = "salutation")
	private String salutation;
	
	@Column(name = "available_date")
	private Date availableDate;
	
	@Column(name = "billing")
	private String billing;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="linkedin_public_url")
	private String linkedinPublicUrl;
	
	@Column(name="tag_with_company")
	private String tagWithCompany;
	
	@Column(name="department")
	private String department;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="country_dialcode")
	private String countryDialcode;
	
	@Column(name="country_dialcode1")
	private String countryDialcode1;
	
	@Column(name="currency")
	private String currency;

	
	  @Column(name = "active")
	  private boolean active;
	 
	
	@Column(name="date_of_birth")
	private String dateOfBirth;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="nationality")
	private String nationality;
	
	@Column(name="id_proof")
	private String idProof;
	
	@Column(name="education")
	private String educatioin;
	
	@Column(name="experience")
	private float experience;
	
	@Column(name="skill")
	private String skill;

	@Column(name="language")
	private String language;
	
	@Column(name="work_location")
	private String workLocation;
	
	@Column(name="work_type")
	private String workType;
	
	@Column(name="id_number")
	private String idNumber;
	
	@Column(name="full_name")
	private String fullName;

	@Column(name="country")
	private String country;
	
	@Column(name="image_id")
	private String imageId;
	
	@Column(name="partnerId")
	private String partnerId;
	
	@Column(name="current_ctc")
	private String currentCtc;
	
	@Column(name="role_type")
	private String roleType;
	
	@Column(name="notice_period")
	private long noticePeriod;
	
	@Column(name = "modified_at")
	private Date modifiedAt;
	
	@Column(name="block_List_Ind", nullable =false)
	private boolean blockListInd =false;
	
	@Column(name="do_Not_Call_Ind", nullable = false)
	private boolean doNotCallInd =false;
	
	@Column(name="category")
	private String category;
	
	@Column(name="benifit")
	private String benifit;
	
	@Column(name="cost_type")
	private String costType;
	
	@Column(name="notice_detail")
	private String noticeDetail;
	
	@Column(name="whatsapp")
	private String whatsApp;
	
	@Column(name="work_preferance")
	private String workPreferance;

	@Column(name="channel")
	private String channel;
	
	@Column(name="allow_sharing")
	private String allowSharing;
	
	@Column(name="emp_Ind")
    private boolean empInd;
	
	@Column(name="current_ctc_curency")
	private String currentCtcCurency;
	
	@Column(name="partner_contact")
	private String partnerContact;
    
	@Column(name="candi_process_ind", nullable = false)
	private boolean candiProcessInd =false;
	
	@Column(name="re_in_state_ind", nullable = false)
	private boolean reInStateInd =false;
	
	@Column(name="t_and_c_ind", nullable = false)
	private boolean tAndCInd =false;

	@Column(name="sector")
	private String sector;

	@Column(name="preferred_distance")
	private int preferredDistance;
//	@Override
//	public String toString() {
//		return "CandidateDetails [candidateDetailsId=" + candidateDetailsId + ", candidateId=" + candidateId
//				+ ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
//				+ ", mobileNumber=" + mobileNumber + ", phoneNumber=" + phoneNumber + ", emailId=" + emailId
//				+ ", linkedin=" + linkedin + ", notes=" + notes + ", userId=" + userId + ", organizationId="
//				+ organizationId + ", creationDate=" + creationDate + ", liveInd=" + liveInd + ", salutation="
//				+ salutation + ", availableDate=" + availableDate + ", billing=" + billing + ", linkedinPublicUrl="
//				+ linkedinPublicUrl + ", tagWithCompany=" + tagWithCompany + ", department=" + department
//				+ ", designation=" + designation + ", countryDialcode=" + countryDialcode + ", countryDialcode1="
//				+ countryDialcode1 + ", currency=" + currency + ", active=" + active + ", dateOfBirth=" + dateOfBirth
//				+ ", gender=" + gender + ", nationality=" + nationality + ", idProof=" + idProof + ", educatioin="
//				+ educatioin + ", experience=" + experience + ", skill=" + skill + ", language=" + language
//				+ ", workLocation=" + workLocation + ", workType=" + workType + ", idNumber=" + idNumber + ", fullName="
//				+ fullName + ", country=" + country + ", imageId=" + imageId + ", partnerId=" + partnerId
//				+ ", currentCtc=" + currentCtc + ", roleType=" + roleType + ", noticePeriod=" + noticePeriod
//				+ ", modifiedAt=" + modifiedAt + ", blockListInd=" + blockListInd +",allowSharing="+allowSharing
//				+ ", doNotCallInd=\" + doNotCallInd "+ "+ \", whatsApp=" + whatsApp +"]";
//	}
	
	
	
}
