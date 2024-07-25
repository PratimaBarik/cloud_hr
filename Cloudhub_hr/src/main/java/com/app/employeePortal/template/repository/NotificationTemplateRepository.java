package com.app.employeePortal.template.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.template.entity.NotificationTemplateDetails;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplateDetails, String>{

	@Query(value = "select a  from NotificationTemplateDetails a  where a.organizationId=:orgId and live_ind=true" )
	public List<NotificationTemplateDetails> getTempleteDetailsListByOrgId(@Param(value="orgId") String orgId);

	public List<NotificationTemplateDetails> findByOrganizationIdAndLiveInd(String orgId, boolean liveInd);

	public NotificationTemplateDetails findByNotificationTempleteIdAndLiveInd(String notificationTempleteId, boolean liveInd);
}
