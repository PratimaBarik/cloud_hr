package com.app.employeePortal.expense.service;


import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.expense.entity.ExpenseDetails;
import com.app.employeePortal.expense.entity.ExpenseType;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.voucher.mapper.VoucherMapper;

public interface ExpenseService {
	public VoucherMapper saveToExpenseProcess(List<ExpenseMapper> expenseList,String userId,String orgId);
	
	public ExpenseMapper getExpenseRelatedDetails(String expenseId);
	
	public List<ExpenseMapper> getExpenseDetailsListByUserId(String userId);
	
	public List<ExpenseMapper> getExpenseDetailsListByOrganizationId(String organizationId);

	public List<ExpenseMapper> getExpenseListByVoucherId(String voucherId);
	
	public ExpenseMapper updateExpenseDetails( ExpenseMapper expenseMapper);
	
	public List<ExpenseMapper> getExpenseListByUserIdWithDateRange(String userId, String startDate, String endDate);
	
	public List<ExpenseMapper> getExpenseListByOrgIdWithDateRange(String orgId, String startDate, String endDate);

	public String deleteExpense(String expenseId);

	public String saveExpenseType(ExpenseMapper expenseMapper);

	public List<ExpenseMapper> getExpenseTypeByOrgId(String orgIdFromToken);

	public ExpenseMapper updateExpenseType(String expenseTypeId, ExpenseMapper expenseMapper);

	public ExpenseMapper getExpenseById(String expenseTypeId);

//    public List<ExpenseMapper> getExpenseTypeByName(String name);

//    public boolean checkExpenseNameInExpenseType(String expenseType);

	public void deleteExpenseTypeById(String expenseTypeId);

	ExpenseMapper getExpenseRelatedDetailsMapper(ExpenseDetails expenseDetails);

	ExpenseMapper getExpenseBMapper(ExpenseType expenseType);

	public String deleteVoucher(String voucherId);

	public List<VoucherMapper> getExpenseStatusListByUserId(String userId, String status, int pageNo, int pageSize);

	public String saveExpenseNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByExpenseId(String expenseId);

//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper);

	public void deleteExpenseNotesById(String notesId);

	public HashMap getExpenseTypeCountByOrgId(String orgId);

	public ByteArrayInputStream exportExpenseListToExcel(List<ExpenseMapper> list);

	public boolean checkExpenseNameInExpenseTypeByOrgLevel(String expenseType, String orgIdFromToken);

	public List<ExpenseMapper> getExpenseTypeByNameByOrgLevel(String name, String orgId);


}
