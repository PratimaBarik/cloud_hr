package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.LobDetails;

@Repository
public interface LobDetailsRepository extends JpaRepository<LobDetails, String> {

	public List<LobDetails> findByOrgIdAndLiveInd(String orgId, boolean b);

	public LobDetails findByLobDetailsId(String lobDetailsId);

	public List<LobDetails> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

	public List<LobDetails> findByNameAndLiveIndAndOrgId(String name, boolean b, String orgId);

}
