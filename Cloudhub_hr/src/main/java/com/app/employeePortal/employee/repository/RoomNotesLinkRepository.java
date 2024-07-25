package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.employee.entity.RoomNotesLink;

public interface RoomNotesLinkRepository extends JpaRepository<RoomNotesLink, String>{

	List<RoomNotesLink> findByRoomIdAndLiveInd(String roomId, boolean b);

}
