package com.app.employeePortal.investor.repository;

import com.app.employeePortal.investor.entity.InvestorDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorDocumentTypeRepo extends JpaRepository<InvestorDocumentType, String> {
    InvestorDocumentType findByDocumentTypeIdAndInvestorId(String documentTypeId, String investorId);

    InvestorDocumentType findByDocumentTypeIdAndInvestorIdAndLiveInd(String documentTypeId, String investorId, boolean b);
}
