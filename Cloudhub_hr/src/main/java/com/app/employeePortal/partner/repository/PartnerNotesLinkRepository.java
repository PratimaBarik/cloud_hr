package com.app.employeePortal.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.partner.entity.PartnerNotesLink;

public interface PartnerNotesLinkRepository extends JpaRepository<PartnerNotesLink, String> {

	//List<PartnerNotesLink> getNotesByPartnerId(String partnerId);
	
	@Query(value = "select a  from PartnerNotesLink a  where a.partnerId=:partnerId and a.liveInd = true" )
    public List<PartnerNotesLink>  getNotesByPartnerId(@Param(value="partnerId")String partnerId);

	public PartnerNotesLink findByNoteId(String notesId);

}
