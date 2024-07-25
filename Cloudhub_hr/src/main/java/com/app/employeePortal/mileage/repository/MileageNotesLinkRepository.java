package com.app.employeePortal.mileage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.mileage.entity.MileageNotesLink;
@Repository
public interface MileageNotesLinkRepository  extends JpaRepository<MileageNotesLink, String>{

	@Query(value = "select a  from MileageNotesLink a  where a.mileageId=:mileageId and a.liveInd = true" )
	public	List<MileageNotesLink> getNotesIdByMileageId(@Param(value="mileageId") String mileageId);

	public MileageNotesLink findByNoteId(String notesId);

}
