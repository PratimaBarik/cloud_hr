package com.app.employeePortal.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.room.entity.DataRoom;

@Repository
public interface DataRoomRepository extends JpaRepository<DataRoom, String> {

	List<DataRoom> findByOrgIdAndLiveInd(String orgId, boolean b);

//	@Query(value = "select a  from DataRoom a  where a.documentId=:documentId and a.liveInd=true ")
//	public DataRoom getByDocumentIdLiveInd(@Param(value = "documentId") String documentId);

//	List<DataRoom> findByShareInd(boolean b);

	@Query(value = "select a  from DataRoom a  where a.dataRoomId=:dataRoomId and a.liveInd=true ")
	DataRoom findByDataRoomIdAndLiveInd(String dataRoomId);
	
}
