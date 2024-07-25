package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorSkillLink;

public interface InvestorSkillLinkRepo extends JpaRepository<InvestorSkillLink,String> {
    @Query(value = "select a  from InvestorSkillLink a  where a.investorId=:investorId" )
    List<InvestorSkillLink> getByInvestorId(@Param("investorId") String investorId);

    List<InvestorSkillLink> findBySkillNameAndInvestorId(String definationId, String investorId);
}
