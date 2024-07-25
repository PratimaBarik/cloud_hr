package com.app.employeePortal.partner.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.partner.entity.PartnerDetails;

@Repository

public interface PartnerDetailsRepository extends JpaRepository<PartnerDetails, String> {

	@Query(value = "select a  from PartnerDetails a  where a.userId=:userId and a.liveInd=true and a.reInStateInd=false")
	Page<PartnerDetails> getPartnerListPageWiseByUserId(@Param(value = "userId") String userId, Pageable pageable);
	
	@Query("SELECT e FROM PartnerDetails e WHERE e.userId IN :userIds AND e.liveInd = true AND e.reInStateInd = false")
	Page<PartnerDetails> getPartnerListPageWiseByUserIds(@Param(value = "userIds")  List<String> userIds, Pageable pageable);

	@Query(value = "select a  from PartnerDetails a  where a.partnerId=:partnerId and a.liveInd=true")
	public PartnerDetails getPartnerDetailsById(@Param(value = "partnerId") String partnerId);

	@Query(value = "select a  from PartnerDetails a  where a.partnerId=:partnerId and a.liveInd=true")
	public PartnerDetails getPartnerDetailsByIdAndLiveInd(@Param(value = "partnerId") String partnerId);

	public List<PartnerDetails> findByPartnerNameContainingAndLiveInd(String partnerName, boolean liveInd);

	@Query(value = "select a  from PartnerDetails a  where a.userId=:userId and a.liveInd=true")

	public List<PartnerDetails> findByUserIdandLiveInd(@Param(value = "userId") String userId);

	List<PartnerDetails> findByliveInd(boolean liveInd);

	PartnerDetails findByPartnerId(String partnerId);

	@Query(value = "select exp  from PartnerDetails exp  where exp.partnerId=:partnerId and exp.liveInd=true")
	public PartnerDetails getpartnerDetailsById(@Param(value = "partnerId") String partnerId);

	List<PartnerDetails> findByEmail(String email);

	List<PartnerDetails> findByUserIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate, Date nextDate,
			boolean b);

	List<PartnerDetails> findByPartnerNameContainingAndLiveIndAndUserId(String partnerName, boolean b, String userId);

	List<PartnerDetails> findByBusinessRegistrationNumberContainingAndLiveIndAndUserId(String partnerName, boolean b,
			String userId);

	List<PartnerDetails> findByTaxRegistrationNumberContainingAndLiveIndAndUserId(String partnerName, boolean b,
			String userId);

	List<PartnerDetails> findByOrgIdAndLiveInd(String orgId, boolean liveInd);

	@Query(value = "select a  from PartnerDetails a  where a.userId=:userId and a.liveInd=true")
	List<PartnerDetails> getPartnerListByUserId(@Param(value = "userId") String userId);
	
	@Query(value = "select a  from PartnerDetails a  where a.userId=:userId and a.liveInd=true and a.reInStateInd=true" )
	public List<PartnerDetails> getDeletedpartnerListPageByUserId(@Param(value="userId") String userId, Pageable paging);

}
