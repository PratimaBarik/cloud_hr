package com.app.employeePortal.room.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.employee.entity.RoomNotesLink;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.RoomNotesLinkRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.repository.EmployeeEventRepository;
import com.app.employeePortal.investor.mapper.DataRoomMapper;
import com.app.employeePortal.investor.service.InvestorOppService;
import com.app.employeePortal.room.entity.DataRoom;
import com.app.employeePortal.room.entity.DataRoomExternalLink;
import com.app.employeePortal.room.entity.DataRoomIncludeLink;
import com.app.employeePortal.room.entity.RoomDocumentLink;
import com.app.employeePortal.room.repository.DataRoomExternalLinkRepository;
import com.app.employeePortal.room.repository.DataRoomIncludeLinkRepository;
import com.app.employeePortal.room.repository.DataRoomRepository;
import com.app.employeePortal.room.repository.RoomDocumentRepository;
import com.app.employeePortal.task.repository.TaskDocumentLinkRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

	@Autowired
	EmployeeEventRepository employeeEventRepository;

	@Autowired
	DataRoomRepository dataRoomRepository;
	@Autowired
	DataRoomIncludeLinkRepository dataRoomIncludeLinkRepository;
	@Autowired
	DataRoomExternalLinkRepository dataRoomExternalLinkRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	@Autowired
	TaskDocumentLinkRepository taskDocumentLinkRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	InvestorOppService investorOppService;

	@Value("${companyName}")
	private String companyName;
	@Autowired
	DocumentService documentService;
	@Autowired
	RoomNotesLinkRepository roomNotesLinkRepository;
	@Autowired RoomDocumentRepository roomDocumentRepository;

	@Override
	public DataRoomMapper SaveDataRoom(DataRoomMapper dataRoomMapper) {
		DataRoomMapper mapper = new DataRoomMapper();
		if (null != dataRoomMapper) {

			DataRoom dataRoom = new DataRoom();
//				dataRoom.setCatagory(dataRoomMapper.getCatagory());
			dataRoom.setCreationDate(new Date());
//				dataRoom.setDepartment(dataRoomMapper.getDepartment());
//				dataRoom.setDescription(dataRoomMapper.getDescription());
//				dataRoom.setDocumentId(dataRoomMapper.getDocumentId());
			dataRoom.setLiveInd(true);
			dataRoom.setName(dataRoomMapper.getName());
			dataRoom.setOrgId(dataRoomMapper.getOrgId());
//				dataRoom.setShareInd(dataRoomMapper.isShareInd());
			dataRoom.setUserId(dataRoomMapper.getUserId());
//				dataRoom.setDocumentType(dataRoomMapper.getDocumentType());

			DataRoom dataRoom1 = dataRoomRepository.save(dataRoom);

			List<String> empList = dataRoomMapper.getIncluded();
			empList.add(dataRoomMapper.getUserId());
			if (null != empList && !empList.isEmpty()) {
				empList.forEach(employeeId -> {

					/* insert RepositoryIncludeLink link table */
					DataRoomIncludeLink dataRoomIncludeLink = new DataRoomIncludeLink();
					dataRoomIncludeLink.setDataRoomId(dataRoom1.getDataRoomId());
					dataRoomIncludeLink.setUserId(employeeId);
					dataRoomIncludeLink.setOrgId(dataRoomMapper.getOrgId());
					dataRoomIncludeLink.setCreationDate(new Date());
					dataRoomIncludeLink.setLiveInd(true);
					dataRoomIncludeLinkRepository.save(dataRoomIncludeLink);

				});

			}

			List<String> emailList = dataRoomMapper.getExternal();
			if (null != emailList && !emailList.isEmpty()) {
				emailList.forEach(emailId -> {

					/* insert RepositoryIncludeLink link table */
					DataRoomExternalLink dataRoomExternalLink = new DataRoomExternalLink();
					dataRoomExternalLink.setDataRoomId(dataRoom1.getDataRoomId());
					dataRoomExternalLink.setUserId(dataRoom1.getUserId());
					dataRoomExternalLink.setMailId(emailId);
					dataRoomExternalLink.setOrgId(dataRoomMapper.getOrgId());
					dataRoomExternalLink.setCreationDate(new Date());
					dataRoomExternalLink.setLiveInd(true);
					dataRoomExternalLinkRepository.save(dataRoomExternalLink);

				});

			}

			mapper = getDataRoomById(dataRoom1.getDataRoomId());

		}
		return mapper;
	}

	@Override
	public DataRoomMapper getDataRoomById(String dataRoomId) {
		DataRoomMapper resultMapper = new DataRoomMapper();
		DataRoom dataRoom = dataRoomRepository.getById(dataRoomId);
		if (null != dataRoom) {
//		resultMapper.setCatagory(dataRoom.getCatagory());
			resultMapper.setCreationDate(Utility.getISOFromDate(dataRoom.getCreationDate()));
//		resultMapper.setDescription(dataRoom.getDescription());
//		resultMapper.setDocumentId(dataRoom.getDocumentId());
//		resultMapper.setLiveInd(dataRoom.isLiveInd());
			resultMapper.setName(dataRoom.getName());
			resultMapper.setDataRoomId(dataRoom.getDataRoomId());
			resultMapper.setOrgId(dataRoom.getOrgId());
			resultMapper.setUserId(dataRoom.getUserId());
//		Department department = departmentRepository.getDepartmentDetailsById(dataRoom.getDepartment());
//		if (null != department) {
//			resultMapper.setDepartment(department.getDepartmentName());
//		}
//		if (!StringUtils.isEmpty(dataRoom.getDocumentType())) {
//			DocumentType documentType = documentTypeRepository.getTypeDetails(dataRoom.getDocumentType());
//			if (null != documentType) {
//				resultMapper.setDocumentType(documentType.getDocumentTypeName());
//			}
//		}
			List<DataRoomIncludeLink> dataRoomIncludeLink = dataRoomIncludeLinkRepository
					.findByDataRoomIdAndLiveInd(dataRoomId, true);
			List<String> empList = new ArrayList<>();
			if (null != dataRoomIncludeLink && !dataRoomIncludeLink.isEmpty()) {
				if (dataRoomIncludeLink.size() > 0) {
					for (DataRoomIncludeLink empId : dataRoomIncludeLink) {
						empList.add(employeeService.getEmployeeFullName(empId.getUserId()));
					}
					resultMapper.setIncluded(empList);
				}
			}

			List<DataRoomExternalLink> dataRoomExternalLink = dataRoomExternalLinkRepository
					.findByDataRoomIdAndLiveInd(dataRoomId, true);
			List<String> emailList = new ArrayList<>();
			if (null != dataRoomExternalLink && !dataRoomExternalLink.isEmpty()) {
				if (dataRoomExternalLink.size() > 0) {
					for (DataRoomExternalLink emailId : dataRoomExternalLink) {
						emailList.add(emailId.getMailId());
					}
					resultMapper.setIncluded(empList);
				}
			}

		}
		return resultMapper;
	}

	@Override
	public List<DataRoomMapper> getDataRoomByUserId(String userId) {
		List<DataRoomMapper> resultMapper = new ArrayList<>();
		List<DataRoomIncludeLink> dataRoomIncludeLink = dataRoomIncludeLinkRepository.findByUserIdAndLiveInd(userId,
				true);
		if (null != dataRoomIncludeLink && !dataRoomIncludeLink.isEmpty()) {
			dataRoomIncludeLink.stream().map(li -> {
				DataRoomMapper mapper = getDataRoomById(li.getDataRoomId());
				if (null != mapper) {
					resultMapper.add(mapper);
				}
				return mapper;
			}).collect(Collectors.toList());
		}

		return resultMapper;
	}

	@Override
	public void deleteDataRoomById(String dataRoomId) {

		DataRoom dataRoom = dataRoomRepository.findByDataRoomIdAndLiveInd(dataRoomId);
		if (null != dataRoom) {
			dataRoom.setLiveInd(false);
			dataRoomRepository.save(dataRoom);

		}
	}

	@Override
	public DataRoomMapper upateDataRoom(DataRoomMapper requestMapper, String dataRoomId) {
		DataRoomMapper resultMapper = new DataRoomMapper();
		DataRoom dataRoom = dataRoomRepository.findByDataRoomIdAndLiveInd(dataRoomId);
		if (null != dataRoom) {

//		if (null != requestMapper.getDepartment()) {
//			dataRoom.setDepartment(requestMapper.getDepartment());
//		}
//		if (null != requestMapper.getDescription()) {
//			dataRoom.setDescription(requestMapper.getDescription());
//		}
//		if (null != requestMapper.getDocumentType()) {
//			dataRoom.setDocumentType(requestMapper.getDocumentType());
//		}
//		if (null != requestMapper.getCatagory()) {
//			dataRoom.setCatagory(requestMapper.getCatagory());
//		}
			if (null != requestMapper.getName()) {
				dataRoom.setName(requestMapper.getName());
			}
//		if (null != requestMapper.getDocumentId()) {
//			dataRoom.setDocumentId(requestMapper.getDocumentId());
//		}
//
//		dataRoom.setShareInd(requestMapper.isShareInd());
			dataRoom.setUserId(requestMapper.getUserId());
			dataRoom.setOrgId(requestMapper.getOrgId());
			dataRoomRepository.save(dataRoom);

			List<DataRoomIncludeLink> dataRoomIncludeLink = dataRoomIncludeLinkRepository
					.findByDataRoomIdAndLiveInd(dataRoomId, true);
			if (null != dataRoomIncludeLink && !dataRoomIncludeLink.isEmpty()) {
				for (DataRoomIncludeLink includeLink2 : dataRoomIncludeLink) {
					includeLink2.setLiveInd(false);
					dataRoomIncludeLinkRepository.save(includeLink2);
				}
			}

			List<String> empList = requestMapper.getIncluded();
			empList.add(requestMapper.getUserId());
			if (null != empList && !empList.isEmpty()) {
				empList.forEach(employeeId -> {

					/* insert RepositoryIncludeLink link table */
					DataRoomIncludeLink dataRoomIncludeLink1 = new DataRoomIncludeLink();
					dataRoomIncludeLink1.setDataRoomId(dataRoomId);
					dataRoomIncludeLink1.setUserId(employeeId);
					dataRoomIncludeLink1.setOrgId(requestMapper.getOrgId());
					dataRoomIncludeLink1.setCreationDate(new Date());
					dataRoomIncludeLink1.setLiveInd(true);
					dataRoomIncludeLinkRepository.save(dataRoomIncludeLink1);

				});

			}

			List<DataRoomExternalLink> dataRoomExternalLink = dataRoomExternalLinkRepository
					.findByDataRoomIdAndLiveInd(dataRoomId, true);
			if (null != dataRoomExternalLink && !dataRoomExternalLink.isEmpty()) {

				for (DataRoomExternalLink emailId : dataRoomExternalLink) {
					emailId.setLiveInd(false);
					dataRoomExternalLinkRepository.save(emailId);
				}
			}

			List<String> emailList = requestMapper.getExternal();
			if (null != emailList && !emailList.isEmpty()) {
				emailList.forEach(emailId -> {

					/* insert RepositoryIncludeLink link table */
					DataRoomExternalLink dataRoomExternalLink1 = new DataRoomExternalLink();
					dataRoomExternalLink1.setDataRoomId(dataRoomId);
					dataRoomExternalLink1.setUserId(requestMapper.getUserId());
					dataRoomExternalLink1.setMailId(emailId);
					dataRoomExternalLink1.setOrgId(requestMapper.getOrgId());
					dataRoomExternalLink1.setCreationDate(new Date());
					dataRoomExternalLink1.setLiveInd(true);
					dataRoomExternalLinkRepository.save(dataRoomExternalLink1);

				});

			}

			resultMapper = getDataRoomById(dataRoomId);

		}
		return resultMapper;
	}

	@Override
	public List<NotesMapper> getNotesByRoomId(String roomId) {
		List<RoomNotesLink> link = roomNotesLinkRepository.findByRoomIdAndLiveInd(roomId, true);
		List<NotesMapper> resultList = new ArrayList<NotesMapper>();

		if (link != null && !link.isEmpty()) {
			link.stream().map(noteLink -> {

				NotesMapper notesMapper = investorOppService.getNotes(noteLink.getNotesId());
				resultList.add(notesMapper);
				return resultList;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public List<DocumentMapper> getDocumentListByDataRoomId(String dataRoomId) {
		List<DocumentMapper> documentMapperList = new ArrayList<>();
		List<RoomDocumentLink> link = roomDocumentRepository
				.findByRoomId(dataRoomId);
		Set<String> documentIds = link.stream().map(RoomDocumentLink::getDocumentId)
				.collect(Collectors.toSet());
		if (null != documentIds && !documentIds.isEmpty()) {
			return documentIds.stream().map(documentId -> {
				DocumentMapper documentMapper = documentService.getDocument(documentId);
				return documentMapper;
			}).collect(Collectors.toList());
		}

		return documentMapperList;
	}
}
