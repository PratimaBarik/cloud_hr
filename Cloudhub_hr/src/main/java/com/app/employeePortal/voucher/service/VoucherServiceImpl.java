package com.app.employeePortal.voucher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.expense.service.ExpenseService;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.mileage.service.MileageService;
import com.app.employeePortal.util.Utility;
import com.app.employeePortal.voucher.entity.VoucherDetails;
import com.app.employeePortal.voucher.mapper.VoucherMapper;
import com.app.employeePortal.voucher.repository.VoucherRepository;

@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ExpenseService expenseService;
    @Autowired
    MileageService mileageService;

    @Override
    public VoucherMapper getVoucherDetailsById(String voucherId) {
        VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherId);
        VoucherMapper voucherMapper = new VoucherMapper();
        if (null != voucherDetails) {
            voucherMapper.setVoucherId(voucherId);
            voucherMapper.setVoucherType(voucherDetails.getVoucher_type());
            voucherMapper.setVoucherDate(Utility.getISOFromDate(voucherDetails.getCreation_date()));
            voucherMapper.setStatus(voucherDetails.getStatus());
            voucherMapper.setSubmittedBy(voucherDetails.getSubmitted_by());
            voucherMapper.setUserId(voucherDetails.getUser_id());
            voucherMapper.setOrgId(voucherDetails.getOrganization_id());
            voucherMapper.setAmount(voucherDetails.getAmount());
            voucherMapper.setCurrency(voucherDetails.getCurrency());
            voucherMapper.setRejectReason(voucherDetails.getRejectReason());
            voucherMapper.setTotalAmount(voucherDetails.getTotalAmount());
            voucherMapper.setVoucherName(voucherDetails.getVoucherName());
            EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetailsByEmployeeId(voucherMapper.getUserId());
            if (null != employeeMapper) {
                voucherMapper.setSubmittedBy(employeeMapper.getEmailId());
            }
        }

        return voucherMapper;
    }

    @Override
    public List<VoucherMapper> getVoucherListByUserId(String userId,String voucherType,int pageSize,int pageNo) {

    	Pageable paging = null;
		paging = PageRequest.of(pageNo, pageSize, Sort.by("creation_date").descending());

        Page<VoucherDetails> voucherList = voucherRepository.getVoucherListByUserId(userId, voucherType, paging);
        List<VoucherMapper> resultList = new ArrayList<VoucherMapper>();
        if (null != voucherList && !voucherList.isEmpty()) {
            voucherList.stream().map(voucherDetails -> {
                VoucherMapper voucherMapper = getVoucherDetailsById(voucherDetails.getVoucher_id());
                resultList.add(voucherMapper);

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<VoucherMapper> getVoucherListByOrganizationId(String orgId, String voucherType) {
        List<VoucherDetails> voucherList = voucherRepository.getVoucherListByOrgId(orgId, voucherType);
        List<VoucherMapper> resultList = new ArrayList<VoucherMapper>();
        if (null != voucherList && !voucherList.isEmpty()) {
            voucherList.stream().map(voucherDetails -> {
                VoucherMapper voucherMapper = getVoucherDetailsById(voucherDetails.getVoucher_id());
                EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetailsByEmployeeId(voucherMapper.getUserId());
                if (null != employeeMapper) {
                    voucherMapper.setSubmittedBy(employeeMapper.getEmailId());
                }
                resultList.add(voucherMapper);
                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public double calculateAmount(String voucherId) {

        double voucher_amount = 0;
        VoucherMapper voucherMapper = getVoucherDetailsById(voucherId);

        if (null != voucherMapper) {
            if (voucherMapper.getVoucherType().equalsIgnoreCase("Expense")) {
                List<ExpenseMapper> expenseList = expenseService.getExpenseListByVoucherId(voucherId);

                if (null != expenseList && !expenseList.isEmpty()) {
                    for (ExpenseMapper expenseMapper : expenseList) {
                        voucher_amount = voucher_amount + expenseMapper.getAmount();
                    }
                }
            } else if (voucherMapper.getVoucherType().equalsIgnoreCase("Mileage")) {

                List<MileageMapper> mileageList = mileageService.getMileageListByVoucherId(voucherId);
                if (null != mileageList && !mileageList.isEmpty()) {
                    for (MileageMapper mileageMapper : mileageList) {
                        voucher_amount = voucher_amount + (mileageMapper.getDistances() * mileageMapper.getMileageRate());
                    }
                }

            }
        }


        return voucher_amount;
    }

    @Override
    public List<VoucherMapper> getVoucherListByUserIdWithDateRange(String userId, String startDate, String endDate) {
        Date end_date = null;
        Date start_date = null;
        List<VoucherMapper> resultList = new ArrayList<VoucherMapper>();
        try {
            end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
            start_date = Utility.getDateFromISOString(startDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<VoucherDetails> voucherList = voucherRepository.getVouchersByUserIdWithDateRange(userId, start_date, end_date);

        if (null != voucherList && !voucherList.isEmpty()) {

            voucherList.stream().map(voucherDetails -> {
                VoucherMapper voucherMapper = new VoucherMapper();
                voucherMapper.setAmount(voucherDetails.getAmount());
                voucherMapper.setCurrency(voucherDetails.getCurrency());
                voucherMapper.setVoucherDate((Utility.getISOFromDate(voucherDetails.getVoucher_date())));
                voucherMapper.setStatus(voucherDetails.getStatus());
                voucherMapper.setVoucherId(voucherDetails.getVoucher_id());
                voucherMapper.setVoucherType(voucherDetails.getVoucher_type());
                EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(voucherDetails.getSubmitted_by());

                if (null != empMapper) {
                    voucherMapper.setSubmittedBy(empMapper.getEmailId());

                }
                resultList.add(voucherMapper);

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<VoucherMapper> getVoucherListByOrgIdWithDateRange(String orgId, String startDate, String endDate) {
        Date end_date = null;
        Date start_date = null;
        List<VoucherMapper> resultList = new ArrayList<VoucherMapper>();
        try {
            end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
            start_date = Utility.getDateFromISOString(startDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<VoucherDetails> voucherList = voucherRepository.getVouchersByOrgIdWithDateRange(orgId, start_date, end_date);

        if (null != voucherList && !voucherList.isEmpty()) {

            voucherList.stream().map(voucherDetails -> {
                VoucherMapper voucherMapper = new VoucherMapper();
                voucherMapper.setAmount(voucherDetails.getAmount());
                voucherMapper.setCurrency(voucherDetails.getCurrency());
                voucherMapper.setVoucherDate((Utility.getISOFromDate(voucherDetails.getVoucher_date())));
                voucherMapper.setStatus(voucherDetails.getStatus());
                voucherMapper.setVoucherId(voucherDetails.getVoucher_id());
                voucherMapper.setVoucherType(voucherDetails.getVoucher_type());
                EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(voucherDetails.getSubmitted_by());
                if (null != empMapper) {
                    voucherMapper.setSubmittedBy(empMapper.getEmailId());

                }
                resultList.add(voucherMapper);

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<VoucherMapper> getExpensesByVoucherName(String voucherType, String name) {
        return voucherRepository.findByVoucherTypeAndVoucherNameContaining(voucherType, name).stream().map(voucherDetails -> {
            VoucherMapper voucherMapper = new VoucherMapper();
            voucherMapper.setAmount(voucherDetails.getAmount());
            voucherMapper.setCurrency(voucherDetails.getCurrency());
            voucherMapper.setVoucherDate((Utility.getISOFromDate(voucherDetails.getVoucher_date())));
            voucherMapper.setStatus(voucherDetails.getStatus());
            voucherMapper.setVoucherId(voucherDetails.getVoucher_id());
            voucherMapper.setVoucherType(voucherDetails.getVoucher_type());
            voucherMapper.setVoucherName(voucherDetails.getVoucherName());
//            voucherMapper.setRejectReason(voucherDetails.getRejectReason());
//            voucherMapper.setTotalAmount(voucherDetails.getVoucherName());
            EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(voucherDetails.getSubmitted_by());
            if (null != empMapper) {
                voucherMapper.setSubmittedBy(empMapper.getEmailId());
            }
            return voucherMapper;
        }).collect(Collectors.toList());
    }
}
