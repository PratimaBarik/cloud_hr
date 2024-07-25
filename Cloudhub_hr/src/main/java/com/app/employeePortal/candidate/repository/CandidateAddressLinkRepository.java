package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateAddressLink;

@Repository
public interface CandidateAddressLinkRepository extends JpaRepository<CandidateAddressLink, String>{
	@Query(value = "select a  from CandidateAddressLink a  where a.candidateId=:candidateId " )
    public List<CandidateAddressLink> getAddressListByCandidateId(@Param(value="candidateId")String candidateId);

}
