package com.app.employeePortal.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.room.entity.DataRoomExternalLink;
@Repository
public interface DataRoomExternalLinkRepository extends JpaRepository<DataRoomExternalLink, String>{

	List<String> findByOrgId(String orgId);

	List<DataRoomExternalLink> findByUserIdAndLiveInd(String userId , boolean b);

	List<DataRoomExternalLink> findByDataRoomIdAndLiveInd(String dataRoomId, boolean b);
}
