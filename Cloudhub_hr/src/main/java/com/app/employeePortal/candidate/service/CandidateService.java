package com.app.employeePortal.candidate.service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.candidate.mapper.ActivityMapper;
import com.app.employeePortal.candidate.mapper.CandidateBankDetailsMapper;
import com.app.employeePortal.candidate.mapper.CandidateCertificationLinkMapper;
import com.app.employeePortal.candidate.mapper.CandidateDocumentMapper;
import com.app.employeePortal.candidate.mapper.CandidateDropDownMapper;
import com.app.employeePortal.candidate.mapper.CandidateEducationDetailsMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmailRequestMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmailResponseMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmploymentHistoryMapper;
import com.app.employeePortal.candidate.mapper.CandidateMapper;
import com.app.employeePortal.candidate.mapper.CandidateRoleCountMapper;
import com.app.employeePortal.candidate.mapper.CandidateTrainingMapper;
import com.app.employeePortal.candidate.mapper.CandidateTreeMapper;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.mapper.CandidateWebsiteMapper;
import com.app.employeePortal.candidate.mapper.DefinationMapper;
import com.app.employeePortal.candidate.mapper.FilterMapper;
import com.app.employeePortal.candidate.mapper.SkillCandidateNoMapper;
import com.app.employeePortal.candidate.mapper.SkillSetMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.videoClips.mapper.VideoClipsMapper;

public interface CandidateService {

	public CandidateViewMapper saveCandidate(CandidateMapper candidateMapper);

	public CandidateViewMapper getCandidateDetailsById(String candidateId);

	public CandidateViewMapper upateCandidateDetailsById(String candidateId, CandidateMapper candidateMapper);

	public String saveSkillSet(SkillSetMapper skillSetMapper);

	public List<SkillSetMapper> getSkillSetDetails(String candidateId);

	public SkillSetMapper getSkillSet(String id);

	public String saveCandidateTraining(CandidateTrainingMapper candidateTrainingMapper);

	public List<CandidateTrainingMapper> getCandidateTraining(String candidateId);

	public CandidateTrainingMapper getCandidateTrainings(String id);

	public List<CandidateViewMapper> getCandidateListPageWiseByUserId(String userId, int pageNo, int pageSize);

	public String saveCandidateNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByCandidateId(String candidateId);

	public NotesMapper getNotes(String id);

	public List<DocumentMapper> getCandidateDocumentListByCandidateId(String candidateId);

	public String saveCandidateEducationDetails(CandidateEducationDetailsMapper candidateEducationDetailsMapper);

	public CandidateEducationDetailsMapper getEducationDetails(String id);

	public List<CandidateEducationDetailsMapper> getCandidateEducationDetails(String candidateId);

	public List<CandidateEducationDetailsMapper> getCandidateEducationDetailsByUserId(String userId);

	public CandidateEducationDetailsMapper updateCandidateEducationalDetails(
			CandidateEducationDetailsMapper candidateEducationDetailsMapper);

	public String saveCandidateEmployment(CandidateEmploymentHistoryMapper candidateEmploymentHistoryMapper);

	public CandidateEmploymentHistoryMapper getEmploymentHisory(String id);

	public List<CandidateEmploymentHistoryMapper> getCandidateEmploymentHisory(String candidateId);

	public void deleteCandidateEducationDetailsById(String id);

	public List<CandidateViewMapper> getCandidateDetailsByNameAndOrgId(String firstName, String orgId);

	public List<String> getSkillSetOfCandidatesOfUser(String userId);

	public ByteArrayInputStream exportCandidateListToExcel(List<CandidateViewMapper> candidateList);

	public String saveCandidateBankDetails(CandidateBankDetailsMapper candidateBankDetailsMapper);

	public List<CandidateBankDetailsMapper> getCandidateBankDetails(String candidateId);

	CandidateBankDetailsMapper getBankDetails(String id);

	public CandidateBankDetailsMapper updateBankDetails(CandidateBankDetailsMapper bankDetailsMapper);

	// public List<CandidateViewMapper> getCandidateDetailsBySkill(String skill,
	// String orgId);

	public void deleteBankDetailsById(String id);

	public List<CandidateViewMapper> getAllCandidateList(int pageNo, int pageSize);

	public HashMap getCountListByuserId(String userId);

	public void deleteSkilsset(String id);

	public void deleteDocumentById(String documentId);

	public CandidateTrainingMapper updateCandidateTraining(CandidateTrainingMapper candidateTrainingMapper)
			throws Exception;

	public CandidateEmploymentHistoryMapper updateEmploymentHistory(
			CandidateEmploymentHistoryMapper candidateEmploymentHistoryMapper);

	public List<CandidateViewMapper> getCandidateListByName(String name);

	public List<CandidateViewMapper> filterListOfCandidateBasedOnRecruitment(String skillName, String orgId,
			String recruitmentId) throws Exception;

	public List<ActivityMapper> getCandidateActivityByCandidateId(String candidateId);

	public String candidateExistsByEmail(String userId, String emailId);

	public DefinationMapper saveCandidateDefination(DefinationMapper definationMapper);

	public List<DefinationMapper> getDefinationsOfAdmin(String orgId);

	public List<CandidateDropDownMapper> getCandidateByOrgId(String orgId);

	public List<CandidateViewMapper> filterListOfCandidateBasedOnRecruitmentByUserId(String skill, String userId);

	public List<SkillSetMapper> getWordCloudHistory(String orgId);

	public String blackListedCandidate(String candidateId);

	public String unBlockCandidate(String candidateId);

	// public List<CandidateMapper> getCandidateCategory(String category);

	// public List<CandidateViewMapper> getcandidateListByCategory(String
	// category,String userId);

	public List<CandidateViewMapper> getCandidateList(String fullName);

	public HashMap getCountListBycategory(String category, String userId);

	public List<CandidateViewMapper> getCandidateDetailsByIdNumber(String IdNumber, String orgId);

	public List<CandidateViewMapper> getCandidateListByRollAndCost(String role, String cost);

	//public List<CandidateViewMapper> getcandidateLisstByCategory(String category, String userId);

	public List<CandidateViewMapper> getCandidateSkillByOrgId(String organizationId, String skill);

	public SkillSetMapper updateCandidateSkill(String skillId, SkillSetMapper skillSetMapper);

	public HashMap getCandidateCountListBySkill(String skill, String orgId);

	List<CandidateViewMapper> getCandidatesBasedOnCategory(String recruitmentId, String orgId);

	// public CandidateMapper updateTransferOneUserToAnother(String userId,
	// CandidateMapper candidateMapper);

	List<String> getSkillSetOfSkillLibery(String orgId);

	public DefinationMapper updateDefinations(DefinationMapper definationMapper);

	public List<String> updateTransferOneUserToAnother(String userId, CandidateMapper candidateMapper);

	boolean checkSkillInSkillLibery(String skillName);

	boolean ipAddressExists(String url);

	public List<CandidateViewMapper> getCandidateFilterList(FilterMapper filterMapper, String userId, String orgId);

	boolean emailExistsByWebsite(String emailId);

	public List<CandidateEmailResponseMapper> getCandidateDetailsByEmail(
			CandidateEmailRequestMapper candidateEmailRequestMapper);

	// public List<CandidateViewMapper> getAllBlackListCandidate(String
	// orgIdFromToken);

	public List<CandidateViewMapper> getAllBlackListCandidateByUserId(String userId);

	public List<DefinationMapper> getDefinationsByUrl(String url);

	float getExprience(Date endDate);

	List<CandidateViewMapper> getCandidatesBasedOnOrgLevelAndUserLevel(String recriutmentId, String orgId,
			String userId);

	public CandidateDocumentMapper getCandidateDocumentDetailsById(String candidateId);

	public String getCandidateDetailsByEmailId(String emailId);

	public String saveCandidateThroughWebsite(CandidateWebsiteMapper candidateMapper);

	public CandidateTreeMapper getCandidateTreeByCandidateId(String candidateId);

	public List<FilterMapper> getAllFilterListByUserId(String userId);

	public List<DefinationMapper> getDefinationDetailsByName(String name);

	public SkillSetMapper updateCandidateSkillRoleBySkillSetDetailsId(String skillSetDetailsId,
			SkillSetMapper skillSetMapper);

	public List<CandidateRoleCountMapper> getCandidaterRoleByOrgId(String orgId);

	public List<CandidateRoleCountMapper> getCandidaterRolesByUserId(String userId);

	public List<VideoClipsMapper> getCandidateVedioListByCandidateId(String candidateId);

	public HashMap getRecordOfToday(String userId);

	public HashMap getCandidateCountListByName(String skill, String orgId);

	public HashMap getCandidateCountListByIdNumber(String skill, String orgId);

	public HashMap getCandidateCountListBySkillAndUserId(String skill, String userId);

	public HashMap getCandidateCountListByNameAndUserId(String skill, String userId);

	public HashMap getCandidateCountListByIdNumberAndUserId(String skill, String userId);

	public List<CandidateViewMapper> getCandidateDetailsBySkill(String skill);

	public List<CandidateViewMapper> getCandidateDetailsBySkillAndOrgId(String skill, String orgId);

	public List<CandidateViewMapper> getCandidateDetailsByName(String name);

	public List<CandidateViewMapper> getCandidateDetailsBySkillAndUserId(String skill, String userId);

	public List<CandidateViewMapper> getCandidateDetailsByNameAndUserId(String skill, String userId);

	public List<CandidateViewMapper> getCandidateDetailsByIdNumberAndUserId(String skill, String userId);

	public String saveCandidateCertification(CandidateCertificationLinkMapper candidateCertificationLinkMapper);

	public List<CandidateCertificationLinkMapper> getCandidateCertificationDetails(String candidateId);

	public String deleteCandidateCertification(String candiCertiLinkId);

	public boolean checkSkillInCustomerSkillSet(String skillName, String candidateId);

	public List<CandidateViewMapper> getCandidateOnboardedListByRecruitmentId(String userId, int pageNo, int pageSize);

	public DocumentMapper resentResume(String candidateId);

	public String saveSkillCandidateNo(SkillCandidateNoMapper skillCandidateNoMapper);

	public List<SkillCandidateNoMapper> getskillCandidateNo(String orgId);

	public String deleteAndReinstateCandidateByCandidateId(String candidateId,
			CandidateMapper candidateMapper);

	public List<CandidateViewMapper> getDeletedCandidateDetailsByUserId(String userId, int pageNo, int pageSize);

	List<CandidateViewMapper> getCandidateListByUserId(String userId);

	List<CandidateViewMapper> getAllCandidateListCount();

	public List<CandidateViewMapper> getAllCandidateListByCategory(int pageNo, int pageSize, String category);

	public List<CandidateViewMapper> getcandidateLisstByCategory(String category, String userId, int pageNo,
			int pageSize);

	public List<CandidateViewMapper> getCandidateDetailsBySkillAndCategory(String skill, String category);

	public List<CandidateViewMapper> getCandidateDetailsByNameAndCategory(String skill, String category);

	public List<CandidateViewMapper> getCandidateDetailsByIdNumberAndCategory(String skill, String category);

	boolean checkSkillInSkillLiberyInUpdate(String skillName, String definationId);

	boolean emailExistsInCandidate(String emailId);

	public SkillSetMapper pauseAndUnpauseCandidateSkillExperience(String skillId, SkillSetMapper skillSetMapper);

	String getCandidateFullName(String candidateId);

	public List<CandidateViewMapper> getBillabeCandidateListByUserId(String userId, int pageNo, int pageSize,
			String month, String year);

	public CandidateViewMapper activeAndInActiveCandidateByCandidateId(String candidateId, CandidateMapper candidateMapper);

	public void deleteDefination(String definationId);

//	public NotesMapper updateNoteDetails(NotesMapper notesMapper);

	public void deleteCandidateNotesById(String notesId);

	List<CandidateViewMapper> getCandidateListPageWiseByUserIds(List<String> userId, int pageNo, int pageSize);

	List<CandidateViewMapper> getcandidateLisstByCategoryAndUserIds(String category, List<String> userId, int pageNo,
			int pageSize);

    String hardDeleteCandidate(String candidateId);

	public HashMap getDefinationsCountByOrgId(String orgId);
}
