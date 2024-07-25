package com.app.employeePortal.mileage.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.mileage.mapper.MileageRateMapper;
import com.app.employeePortal.voucher.mapper.VoucherMapper;

public interface MileageService {
	public VoucherMapper saveToMileageProcess(List<MileageMapper> mileageList,String userId,String orgId);

    public MileageMapper getMileageRelatedDetails(String mileageId);
 	
	public List<MileageMapper> getMileageDetailsListByUserId(String userId);
	
	public List<MileageMapper> getMileageDetailsListByOrganizationId(String organizationId);
	
	public List<MileageMapper> getMileageListByVoucherId(String voucherId);
	
	public MileageMapper updateMileageDetails(MileageMapper mileageMapper) ;
	
	public List<MileageMapper> getMileageListByUserIdWithDateRange(String userId, String startDate, String endDate);
	
	public List<MileageMapper> getMileageListByOrgIdWithDateRange(String orgId, String startDate, String endDate);
	
	public List<MileageRateMapper> getMileageRate(String orgId);

	public String deleteMileage(String mileageId);

	public String deleteVoucher(String voucherId);

	MileageMapper getMileageRelatedDetailsByMileageIdAndVoucherId(String mileageId, String voucherId);

	public String saveMileageRate(List<MileageRateMapper> mileageRatelist, String userId, String orgId);

	public List<VoucherMapper> getMileageStatusListByUserId(String userId, String status, int pageNo, int pageSize);

	public List<VoucherMapper> getMileageListByOrgIdWithDateRangeAndStatus(String organizationId, String startDate,
			String endDate, String status);

	public ByteArrayInputStream exportMileage(List<VoucherMapper> mileageList);

	String saveMileageRateByCountry(String country, double rate, String userId, String orgId);

	public String saveMileageNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByMileageId(String mileageId);

//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper);

	public void deleteMileageNotesById(String notesId);



}
