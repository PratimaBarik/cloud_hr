package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, String> {

	Club findByClubIdAndLiveInd(String clubId, boolean b);

	List<Club> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<Club> findByClubNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

	//List<Club> findAllByOrderByNoOfShareAscAndLiveInd(boolean b);
	
	List<Club> findAllByLiveIndTrueOrderByNoOfShareAsc();

	List<Club> findAllByLiveIndTrueOrderByNoOfShareDesc();

	List<Club> findByClubNameAndLiveIndAndOrgId(String clubName, boolean b, String orgId);
	
	Club findByClubNameAndOrgIdAndLiveInd(String clubName, String orgId, boolean b);
	
	Club findByClubIdAndLiveIndAndInvToCusInd(String clubId, boolean b, boolean c);

	List<Club> findByClubNameAndClubIdNotAndLiveIndAndOrgId(String clubName, String clubId, boolean b, String orgId);

}
