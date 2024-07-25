package com.app.employeePortal.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leave.entity.LeaveNotesLink;
@Repository
public interface LeaveNotesLinkRepository  extends JpaRepository<LeaveNotesLink, String>{

	@Query(value = "select a  from LeaveNotesLink a  where a.leaveId=:leaveId and a.liveInd = true" )
	public	List<LeaveNotesLink> getNotesIdByLeaveId(@Param(value="leaveId") String leaveId);

	public LeaveNotesLink findByNoteId(String notesId);

}
