package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.IdProofTypeDelete;

@Repository

public interface IdProofTypeDeleteRepository extends JpaRepository<IdProofTypeDelete, String> {

	public IdProofTypeDelete findByIdProofTypeId(String idProofTypeId);

	public List<IdProofTypeDelete> findByOrgId(String orgId);

	

}
