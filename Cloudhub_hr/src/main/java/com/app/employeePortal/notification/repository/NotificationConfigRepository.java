package com.app.employeePortal.notification.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.notification.entity.NotificationConfigs;

@Repository
public interface NotificationConfigRepository extends JpaRepository<NotificationConfigs, String>{

//	NotificationConfigs findByOrgId(String orgId);

	NotificationConfigs findByNameAndOrgId(String name, String orgId);
	
	List<NotificationConfigs> findByOrgId(String orgId);

//	NotificationConfigs findByCreateIndAndOrgId(boolean type, String orgId);
//
//	NotificationConfigs findByUpdateIndAndOrgId(boolean type, String orgId);
//
//	NotificationConfigs findByDeleteIndAndOrgId(boolean type, String orgId);
		
	NotificationConfigs findByNameAndCreateIndAndOrgId(String processNmae, boolean type, String orgId);

	NotificationConfigs findByNameAndUpdateIndAndOrgId(String processNmae, boolean type, String orgId);

	NotificationConfigs findByNameAndDeleteIndAndOrgId(String processNmae, boolean type, String orgId);
}
