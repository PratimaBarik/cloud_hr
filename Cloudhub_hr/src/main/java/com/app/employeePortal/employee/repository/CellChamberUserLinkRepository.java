package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.CellChamberUserLink;
@Repository
public interface CellChamberUserLinkRepository extends JpaRepository<CellChamberUserLink, String>{

	CellChamberUserLink findByCellChamberUserLinkIdAndActive(String cellChamberUserLinkId, boolean b);

	List<CellChamberUserLink> findByLocationIdAndActive(String locationDetailsId, boolean b);

	CellChamberUserLink findByUserAndActive(String user, boolean b);

	List<CellChamberUserLink> findByOrgIdAndActive(String orgId, boolean b);

	List<CellChamberUserLink> findByCellChamberLinkIdAndActive(String cellChamberLinkId, boolean b);

//	List<CellChamberLink> findByCellId(String cellId);
//
//	CellChamberLink findByIdAndActive(String cellChamberLinkId, boolean b);
//
//	List<CellChamberLink> findByActive(boolean b);
//
//	List<CellChamberLink> findByActiveAndUsedInd(boolean b, boolean c);

}
