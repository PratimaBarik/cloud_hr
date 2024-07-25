package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, String> {
	
	@Query(value = "select a  from Notes a  where a.id=:notesId and a.liveInd=true" )
	public	List<Notes> getNotesById(@Param(value="notesId") String notesId);

	@Query(value = "select a  from Notes a  where a.notes_id=:notesId and a.liveInd=true" )
	public Notes findByNoteId(@Param(value="notesId")String notesId);

	@Query(value = "select a  from Notes a  where a.notes_id=:notesId" )
	public Notes findByNoteIdWithOutLiveInd(@Param(value="notesId")String notesId);

}
