package com.app.employeePortal.voucher.service;

import java.util.List;

import com.app.employeePortal.voucher.mapper.VoucherMapper;

public interface VoucherService {

	public List<VoucherMapper> getVoucherListByUserId(String userId,String voucherType,int pageSize,int pageNo);

	public List<VoucherMapper> getVoucherListByOrganizationId(String orgId,String voucherType);
	
	public VoucherMapper getVoucherDetailsById(String voucherId);
	
	public double calculateAmount(String voucherId);
	
	public List<VoucherMapper> getVoucherListByUserIdWithDateRange(String userId, String startDate, String endDate);
	
	public List<VoucherMapper> getVoucherListByOrgIdWithDateRange(String orgId, String startDate, String endDate);

    List<VoucherMapper> getExpensesByVoucherName(String voucherType,String name);
}
