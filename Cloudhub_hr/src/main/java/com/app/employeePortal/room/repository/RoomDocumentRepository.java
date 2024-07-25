package com.app.employeePortal.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.room.entity.RoomDocumentLink;

@Repository
public interface RoomDocumentRepository extends JpaRepository<RoomDocumentLink, String> {

	List<RoomDocumentLink> findByRoomId(String dataRoomId);
	
}
