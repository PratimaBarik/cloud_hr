package com.app.employeePortal.room.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.investor.mapper.DataRoomMapper;
import com.app.employeePortal.room.service.RoomService;

@RestController
@CrossOrigin(maxAge = 3600)
public class RoomController {
	
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private RoomService roomService;



	@PostMapping("/api/v1/data-room/save")
	public ResponseEntity<?> CreateDataRoom(@RequestHeader("Authorization") String authorization,
			@RequestBody DataRoomMapper dataRoomMapper) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			dataRoomMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			dataRoomMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			DataRoomMapper mapper = roomService.SaveDataRoom(dataRoomMapper);
			return new ResponseEntity<>(mapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/data-room/user/{userId}")
	public ResponseEntity<?> getDataRoomByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<DataRoomMapper> mapper = roomService.getDataRoomByUserId(userId);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/data-room/delete/{dataRoomId}")
	public ResponseEntity<?> deleteDataRoom(@PathVariable("dataRoomId") String dataRoomId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			roomService.deleteDataRoomById(dataRoomId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/data-room/update/{dataRoomId}")
	public ResponseEntity<?> upateDataRoom(@PathVariable("dataRoomId") String dataRoomId,
			@RequestBody DataRoomMapper dataRoomMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			dataRoomMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			dataRoomMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			DataRoomMapper mapper = roomService.upateDataRoom(dataRoomMapper, dataRoomId);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	 @GetMapping("/api/v1/data-room/notes/{dataRoomId}")
	 public ResponseEntity<?> getNotesByRoomId(@PathVariable String roomId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				List<NotesMapper> notesMapper = roomService.getNotesByRoomId(roomId);
				if (null != notesMapper && !notesMapper.isEmpty()) {
					notesMapper
							.sort((NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					return new ResponseEntity<>(notesMapper, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(notesMapper, HttpStatus.OK);

			}
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	 
	 @GetMapping("/api/v1/data-room/document/{dataRoomId}")
	public ResponseEntity<?> getDocumentListByInvestorLeadsId(@PathVariable String dataRoomId,
				HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				List<DocumentMapper> documentList = roomService.getDocumentListByDataRoomId(dataRoomId);
				if (null != documentList && !documentList.isEmpty()) {
					documentList.sort(
							(DocumentMapper m1, DocumentMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					return new ResponseEntity<>(documentList, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(documentList, HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}

}
