package com.app.employeePortal.contact.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.customer.entity.Customer;

@Repository
public interface ContactRepository extends JpaRepository<ContactDetails, String> {

	@Query(value = "select a  from ContactDetails a  where a.contactId=:contactId and a.liveInd=true")
	public ContactDetails getContactDetailsById(@Param(value = "contactId") String contactId);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.liveInd=true")
	public List<ContactDetails> getContactByUserId(@Param(value = "userId") String userId);

	// @Query(value = "select a from ContactDetails a where a.contactId=:contactId"
	// )
	// public ContactDetails findById(@Param(value="contactId") String contactId);

	@Query(value = "select a  from ContactDetails a  where a.first_name=:firstName")
	public List<ContactDetails> getContactListByName(@Param(value = "firstName") String firstName);

	@Query(value = "select a  from ContactDetails a  where a.contactType=partner")
	public List<ContactDetails> getAllPartnerContact();

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.liveInd=true")
	public List<ContactDetails> findByUserIdandLiveInd(@Param(value = "userId") String userId);

	@Query(value = "select a  from ContactDetails a  where a.fullName=:fullName and a.liveInd=true")
	public List<ContactDetails> getContactDetailsByFullName(@Param(value = "fullName") String fullName);

	@Query(value = "select a  from ContactDetails a  where a.fullName=:fullName and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getContactDetailsByFullNameAndContactType(@Param(value = "fullName") String fullName,
			@Param(value = "contactType") String contactType);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
	public Page<ContactDetails> findByUserIdAndContactTypeAndLiveInd(@Param(value = "userId") String userId,
			@Param(value = "contactType") String contactType, Pageable paging);
	
	@Query(value = "select a  from ContactDetails a  where a.user_id IN :userIds and a.contactType=:contactType and a.liveInd=true")
	public Page<ContactDetails> findByUserIdsAndContactTypeAndLiveInd(@Param(value = "userIds") List<String> userIds,
			@Param(value = "contactType") String contactType, Pageable paging);
	
	@Query(value = "select a  from ContactDetails a  where a.user_id IN :userIds and a.contactType=:contactType and a.liveInd=true")
	public Page<ContactDetails> getTeamContactListdAndContactTypeAndLiveInd(@Param(value = "userIds") List<String> userIds,
			@Param(value = "contactType") String contactType, Pageable paging);

	@Query(value = "select exp  from ContactDetails exp  where exp.contactId=:contactId and exp.liveInd=true")
	public ContactDetails getcontactDetailsById(@Param(value = "contactId") String contactId);


	@Query(value = "select a  from ContactDetails a  where a.contactId=:contactId")
	public List<ContactDetails> FindByContactRole(String contactId);

	@Query(value = "select a  from ContactDetails a  where a.email_id=:emailId and a.liveInd=true")
	public List<ContactDetails> getByEmailId(@Param(value = "emailId") String emailId);

	public List<ContactDetails> findByFullNameContainingAndLiveInd(String name, boolean b);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.liveInd=true")
	List<ContactDetails> getcontactListByUserId(@Param(value = "userId") String userId);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> findByUserIdAndContactType(@Param(value = "userId") String userId,
			@Param(value = "contactType") String contactType);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getCustomerContactByUserIdAndContactTypeAndLiveInd(@Param(value = "userId") String userId,
			@Param(value = "contactType") String contactType);
	
	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getPartnerContactByUserIdAndContactTypeAndLiveInd(@Param(value = "userId") String userId,
			@Param(value = "contactType") String contactType);
	
	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getInvesterContactByUserIdAndContactTypeAndLiveInd(@Param(value = "userId") String userId,
			@Param(value = "contactType") String contactType);
	
	@Query(value = "select a  from ContactDetails a  where a.organization_id=:orgId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getAllPartnerContactByOrgIdAndContactTypeAndLiveInd(@Param(value = "orgId") String orgId,
			@Param(value = "contactType") String contactType);
	
	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.creationDate BETWEEN :currDate AND :nextDate and a.liveInd=true")
	public List<ContactDetails> findByUserIdAndCreationDateBetweenAndContactType(@Param(value = "userId") String userId,
			@Param(value = "contactType") String contactType,
			@Param(value = "currDate")Date currDate, @Param(value = "nextDate")Date nextDate);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.fullName LIKE %:name% and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getInvestorContactByName(@Param(value = "userId")String userId,
			@Param(value = "name") String name,
			@Param(value = "contactType") String contactType);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.tag_with_company=:name and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getInvestorContactByCompany(@Param(value = "userId") String userId,
			@Param(value = "name") String name,
			@Param(value = "contactType") String contactType);
	
	@Query(value = "select a  from ContactDetails a  where a.organization_id=:orgId and a.contactType=:contactType and a.liveInd=true")
	public Page<ContactDetails> getContactListByOrgIdAndContactTypeAndLiveIndPageWise(@Param("orgId")String orgId,@Param("contactType")String contactType,
			Pageable paging);
	
	@Query(value = "select a  from ContactDetails a  where a.organization_id=:orgId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getContactListByOrgIdAndContactTypeAndLiveInd(@Param("orgId")String orgId,@Param("contactType")String contactType);
	
	@Query(value = "select a  from ContactDetails a  where a.organization_id=:orgId and a.liveInd=true")
	public Page<ContactDetails> getContactListByOrgIdAndPagging(@Param("orgId")String orgId, Pageable paging);

	@Query(value = "select a  from ContactDetails a  where a.user_id IN :userIds and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getTeamContactListByUserIds(@Param(value = "userIds") List<String> userIds,
			@Param(value = "contactType") String contactType);

	@Query(value = "select a  from ContactDetails a  where a.email_id=:emailId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> findByEmailIdAndContactTypeAndLiveInd(@Param(value = "emailId") String emailId, @Param(value = "contactType") String contactType);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.liveInd=true and a.creationDate BETWEEN :startDate AND :endDate")
	public List<ContactDetails> getCreatedContactListByUserIdAndDateRange(@Param(value="userId")String userId,@Param("contactType")String contactType,
			@Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);
	
	@Query(value = "select a  from ContactDetails a  where a.organization_id=:orgId and a.contactType=:contactType and a.liveInd=true and a.creationDate BETWEEN :startDate AND :endDate")
	public List<ContactDetails> getInvesterContactByOrgIdAndContactTypeAndLiveIndWithDateRange(@Param(value="orgId")String orgId,@Param("contactType")String contactType,
			@Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from ContactDetails a  where a.email_id=:emailId and a.liveInd=true")
	public ContactDetails getByEmailIdAndLiveInd(@Param(value = "emailId") String emailId);

	public List<ContactDetails> findByLiveInd(boolean b);

	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> getByUserIdAndContactTypeAndLiveInd(@Param(value = "userId")String userId,@Param(value = "contactType") String contactType);

	public ContactDetails findByContactId(String contactId);

	@Query(value = "select a  from ContactDetails a  where a.sector=:sectorId and  a.organization_id=:orgId and a.contactType=:contactType and a.liveInd=true ")
	public List<ContactDetails> getBySectorAndOrganizationIdAndContactType(@Param(value="sectorId")String sectorId,@Param(value="orgId")String orgId,@Param("contactType")String contactType);

	@Query(value = "select a  from ContactDetails a  where a.source=:sourceId and  a.organization_id=:orgId and a.contactType=:contactType and a.liveInd=true ")
	public List<ContactDetails> getBySourceAndOrganizationIdAndContactType(@Param(value="sourceId")String sourceId,@Param(value="orgId")String orgId,@Param("contactType")String contactType);

	@Query("SELECT a FROM ContactDetails a WHERE a.user_id IN :userIds AND a.sector=:sectorId AND a.contactType=:contactType AND a.liveInd = true ")
	public List<ContactDetails> getTeamContactListByUserIdsAndSectorAndContactType(@Param(value = "userIds") List<String> userIdss,
			@Param(value = "sectorId") String sectorId,@Param(value = "contactType")String contactType);

	@Query("SELECT a FROM ContactDetails a WHERE a.user_id IN :userIds AND a.source=:sourceId AND a.contactType=:contactType AND a.liveInd = true ")
	public List<ContactDetails> getTeamContactListByUserIdsAndSourceAndContactType(@Param(value = "userIds") List<String> userIdss,
			@Param(value = "sourceId") String sourceId,@Param(value = "contactType")String contactType);

	@Query(value = "select a  from ContactDetails a  where a.sector=:sectorId and a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> findBySectorAndLiveIndAndUserIdAndContactType(@Param(value = "sectorId") String sectorId,@Param(value = "userId") String userId,
			@Param(value = "contactType") String contactType);


	@Query(value = "select a  from ContactDetails a  where a.source=:sourceId and a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
	public List<ContactDetails> findBySourceAndLiveIndAndUserIdAndContactType(@Param(value = "sourceId") String sourceId,@Param(value = "userId") String userId,
			@Param(value = "contactType") String contactType);
	

	


//	@Query(value = "select a  from ContactDetails a  where a.user_id=:userId and a.contactType=:contactType and a.liveInd=true")
//	public Page<ContactDetails> findByUserIdAndContactTypeAndLiveInd(@Param(value = "userId") String userId,
//			@Param(value = "contactType") String contactType, Pageable paging);
	//public List<ContactDetails> getByUserIdAndContactTypeAndLiveInd(String userId, String string);

}
