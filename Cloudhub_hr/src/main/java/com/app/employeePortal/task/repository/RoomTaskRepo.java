package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.task.entity.RoomTaskLink;

public interface RoomTaskRepo extends JpaRepository<RoomTaskLink,String> {
    @Query("select a from RoomTaskLink a where a.roomId=:roomId and a.liveInd=true")
    Page<RoomTaskLink> getTaskListByRoomId(@Param("roomId")String roomId, Pageable page);
    
    @Query("select a from RoomTaskLink a where a.roomId=:roomId and a.liveInd=true")
    List<RoomTaskLink> getTaskListByRoomIdAndLiveInd(@Param("roomId")String roomId);
    
    @Query("select a from RoomTaskLink a where a.roomId=:roomId and a.taskId=:taskId and a.liveInd=true")
    RoomTaskLink getTaskListByRoomIdAndTaskIdAndLiveInd(@Param("roomId")String roomId, @Param("taskId")String taskId);
}
