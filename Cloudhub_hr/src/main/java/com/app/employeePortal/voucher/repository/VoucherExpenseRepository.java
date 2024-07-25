package com.app.employeePortal.voucher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.voucher.entity.VoucherExpenseLink;


@Repository
public interface VoucherExpenseRepository extends JpaRepository<VoucherExpenseLink, String>{

	@Query(value = "select a  from VoucherExpenseLink a  where a.voucher_id=:voucherId and a.live_ind=true" )
    public List<VoucherExpenseLink> getExpenseListByVoucherId(@Param(value="voucherId")String voucherId);

	@Query(value = "select a  from VoucherExpenseLink a  where a.expense_id=:expenseId and a.live_ind=true" )
	public VoucherExpenseLink getByExpenseId(@Param(value="expenseId")String expenseId);
}
