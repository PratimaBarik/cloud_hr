package com.app.employeePortal.Opportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, String> {

	List<Phone> findByOrderPhoneIdAndActive(String orderPhoneId, boolean b);

	List<Phone> findByUserIdAndActive(String userId, boolean b);

	List<Phone> findByOrderPhoneIdAndActiveAndPhTechInd(String orderPhoneId, boolean active, boolean phTechInd);

	List<Phone> findByOrderPhoneIdAndActiveAndPhRepairInd(String orderPhoneId, boolean active, boolean phRepairInd);

	List<Phone> findByOrderPhoneIdAndActiveAndQcStatus(String orderPhoneId, boolean active, String qcStatus);

	List<Phone> findByOrderPhoneIdAndActiveAndRepairStatus(String orderPhoneId, boolean active, String repairStatus);

	List<Phone> findByOrderPhoneIdAndActiveAndReceivePhoneInd(String orderPhoneId, boolean active,
			boolean receivePhoneInd);

	List<Phone> findByOrderPhoneIdAndActiveAndDispatchPhoneInd(String orderPhoneId, boolean active,
			boolean dispatchPhoneInd);

	List<Phone> findByOrderPhoneIdAndActiveAndPhRepairIndAndReceivePhoneInd(String orderPhoneId, boolean active,
			boolean phRepairInd, boolean receivePhoneInd);

	List<Phone> findByOrderPhoneIdAndActiveAndPhRepairIndAndReceivePhoneIndAndPhTechInd(String orderPhoneId,
			boolean active, boolean phRepairInd, boolean receivePhoneInd, boolean PhTechInd);

	List<Phone> findByOrderPhoneIdAndActiveAndPhRepairIndAndReceivePhoneIndAndPhRepairInd(String orderPhoneId,
			boolean active, boolean phRepairInd, boolean receivePhoneInd, boolean PhRepairInd);

	List<Phone> findByOrderPhoneIdAndActiveAndQcStatusAndReceivePhoneInd(String orderPhoneId, boolean b, String string,
			boolean c);

	List<Phone> findByOrderPhoneIdAndReceivePhoneIndAndActive(String orderPhoneId, boolean b, boolean c);

	List<Phone> findByOrderPhoneIdAndActiveAndRepairStatusAndReceivePhoneInd(String orderPhoneId, boolean b,
			String string, boolean c);

	Phone findByIdAndOrderPhoneIdAndQcStatusAndReceivePhoneInd(String phoneId, String orderPhoneId, String string,
			boolean b);

	Phone findByIdAndOrderPhoneIdAndRepairStatusAndReceivePhoneInd(String phoneId, String orderPhoneId, String string,
			boolean b);

	Phone findByIdAndActive(String phoneId, boolean active);

	List<Phone> findByOrderPhoneId(String orderPhoneId);

	List<Phone> findByOpportunityId(String opportunityId);

	List<Phone> findByOpportunityIdAndActive(String opportunityId, boolean b);
	
	Phone findByUserIdAndActiveStatus(String userId, String string);
}
