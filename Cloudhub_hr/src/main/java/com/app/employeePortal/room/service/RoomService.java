package com.app.employeePortal.room.service;

import java.util.List;

import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.investor.mapper.DataRoomMapper;

public interface RoomService {

	DataRoomMapper SaveDataRoom(DataRoomMapper dataRoomMapper);

	DataRoomMapper getDataRoomById(String dataRoomId);

	List<DataRoomMapper> getDataRoomByUserId(String userId);

	void deleteDataRoomById(String dataRoomId);

	DataRoomMapper upateDataRoom(DataRoomMapper requestMapper, String dataRoomId);

	List<NotesMapper> getNotesByRoomId(String roomId);

	List<DocumentMapper> getDocumentListByDataRoomId(String dataRoomId);

}
