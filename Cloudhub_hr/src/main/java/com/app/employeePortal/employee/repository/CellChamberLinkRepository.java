package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.CellChamberLink;
@Repository
public interface CellChamberLinkRepository extends JpaRepository<CellChamberLink, String>{

	List<CellChamberLink> findByCellId(String cellId);

	CellChamberLink findByIdAndActive(String cellChamberLinkId, boolean b);

	List<CellChamberLink> findByActive(boolean b);

	List<CellChamberLink> findByActiveAndUsedInd(boolean b, boolean c);

	List<CellChamberLink> findByLocationDetailsIdAndActiveAndUsedInd(String locationDetailsId, boolean b, boolean c);

	List<CellChamberLink> findByLocationDetailsIdAndActive(String locationDetailsId, boolean b);

	List<CellChamberLink> findByLocationDetailsIdAndOrgIdAndActive(String locationDetailsId, String orgId, boolean b);
}
