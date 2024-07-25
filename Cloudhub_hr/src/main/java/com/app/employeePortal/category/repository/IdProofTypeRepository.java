package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.IdProofType;

@Repository

public interface IdProofTypeRepository extends JpaRepository<IdProofType, String> {

	public IdProofType findByIdProofTypeId(String idProofTypeId);

	public List<IdProofType> findByorgId(String orgId);

//	public List<IdProofType> findByIdProofTypeContainingAndLiveInd(String name, boolean liveInd);

	public List<IdProofType> findByOrgIdAndLiveInd(String orgId, boolean liveInd);

	public List<IdProofType> findByIdProofTypeAndLiveIndAndOrgId(String idProofType, boolean b, String orgId);

	public List<IdProofType> findByIdProofTypeContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);
}
