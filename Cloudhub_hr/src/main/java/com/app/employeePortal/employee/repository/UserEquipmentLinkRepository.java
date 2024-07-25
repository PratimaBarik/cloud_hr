package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.UserEquipmentLink;
@Repository
public interface UserEquipmentLinkRepository extends JpaRepository<UserEquipmentLink, String>{

	List<UserEquipmentLink> findByUserIdAndLiveInd(String userId, boolean b);

	UserEquipmentLink findByUserEquipmentLinkId(String id);
}
