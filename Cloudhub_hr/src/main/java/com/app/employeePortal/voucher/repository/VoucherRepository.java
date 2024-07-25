package com.app.employeePortal.voucher.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.voucher.entity.VoucherDetails;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherDetails, String>{
	
	@Query(value = "select a  from VoucherDetails a  where a.user_id=:userId and a.voucher_type=:voucherType and a.live_ind=true" )
    public Page<VoucherDetails> getVoucherListByUserId(@Param(value="userId")String userId,@Param(value="voucherType")String voucherType,Pageable paging);
	
	@Query(value = "select a  from VoucherDetails a  where a.organization_id=:orgId and a.voucher_type=:voucherType and a.live_ind=true" )
    public List<VoucherDetails> getVoucherListByOrgId(@Param(value="orgId")String orgId,@Param(value="voucherType")String voucherType);
	
	@Query(value = "select a  from VoucherDetails a  where a.voucher_id=:voucherId and a.live_ind=true" )
    public VoucherDetails getVoucherDetailsById(@Param(value="voucherId")String voucherId);
	
	@Query(value = "select a  from VoucherDetails a  where a.task_id=:taskId and a.live_ind=true" )
    public VoucherDetails getVoucherDetailsByTaskId(@Param(value="taskId")String taskId);
	
	@Query(value = "select a  from VoucherDetails a  where a.user_id=:userId and a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
    public List<VoucherDetails> getVouchersByUserIdWithDateRange(@Param(value="userId")String voucherId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	
	@Query(value = "select a  from VoucherDetails a  where a.organization_id=:orgId and a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
    public List<VoucherDetails> getVouchersByOrgIdWithDateRange(@Param(value="orgId")String orgId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	@Query(value = "select a  from VoucherDetails a  where a.user_id=:userId and a.voucher_type=:voucherType and a.status=:status and a.live_ind=true" )
	public Page<VoucherDetails> getVoucherListByUserIdAndVoucherTypeAndStatus(@Param(value="userId")String userId, @Param(value="voucherType")String voucherType,
			@Param(value="status")String status,Pageable paging);

	@Query("SELECT e FROM VoucherDetails e WHERE e.voucher_type = :voucherType AND LOWER(e.voucherName) LIKE %:name%")
    List<VoucherDetails> findByVoucherTypeAndVoucherNameContaining(@Param("voucherType") String voucherType,@Param("name")String name);

	@Query(value = "select a  from VoucherDetails a  where a.organization_id=:organizationId and a.voucher_type=:voucherType and a.status=:status and a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
	public List<VoucherDetails> getVoucherListByOrganisationIdAndVoucherTypeAndStatusWithDateRange(@Param(value="organizationId")String organizationId, 
																				@Param(value="voucherType")String voucherType,
																				@Param(value="status")String status, 
																				@Param(value="startDate")Date startDate, 
																				@Param(value="endDate")Date endDate);

	

}
