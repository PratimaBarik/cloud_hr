package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.RecruitmentAddressLink;


@Repository
public interface RecruitmentAddressLinkRepository extends JpaRepository<RecruitmentAddressLink, String> {

	@Query(value = "select a  from RecruitmentAddressLink a  where a.recruitmentId=:recruitmentId " )
    public List<RecruitmentAddressLink> getAddressListByRecruitmentId(@Param(value="recruitmentId")String recruitmentId);

	@Query(value = "select a  from RecruitmentAddressLink a  where a.recruitmentId=:recruitmentId " )
	public RecruitmentAddressLink getAddressDetailsByRecruitmentId(@Param(value="recruitmentId")String recruitmentId);
	
}
