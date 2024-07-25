package com.app.employeePortal.voucher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.voucher.entity.VoucherMileageLink;

@Repository
public interface VoucherMileageRepository extends JpaRepository<VoucherMileageLink, String>{


	@Query(value = "select a  from VoucherMileageLink a  where a.voucher_id=:voucherId and a.live_ind=true" )
    public List<VoucherMileageLink> getMileageListByVoucherId(@Param(value="voucherId")String voucherId);

	@Query(value = "select a  from VoucherMileageLink a  where a.mileage_id=:mileageId and a.live_ind=true" )
	public VoucherMileageLink getByMileageId(@Param(value="mileageId")String mileageId);
}
