package com.app.employeePortal.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.room.entity.DataRoomIncludeLink;
@Repository
public interface DataRoomIncludeLinkRepository extends JpaRepository<DataRoomIncludeLink, String>{

	List<String> findByOrgId(String orgId);

	List<DataRoomIncludeLink> findByUserIdAndLiveInd(String userId , boolean b);

	List<DataRoomIncludeLink> findByDataRoomIdAndLiveInd(String dataRoomId, boolean b);
}
