package com.app.employeePortal.leads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leads.entity.LeadsNotesLink;
@Repository
public interface LeadsNotesLinkRepository  extends JpaRepository<LeadsNotesLink, String>{

	@Query(value = "select a  from LeadsNotesLink a  where a.leadsId=:leadsId and a.liveInd = true" )
	public	List<LeadsNotesLink> getNotesIdByLeadsId(@Param(value="leadsId") String leadsId);

	public LeadsNotesLink findByNotesId(String notesId);

}
