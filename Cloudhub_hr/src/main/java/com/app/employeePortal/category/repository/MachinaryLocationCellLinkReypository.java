package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.MachinaryLocationCellLink;

@Repository
public interface MachinaryLocationCellLinkReypository extends JpaRepository<MachinaryLocationCellLink, String> {

	List<MachinaryLocationCellLink> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<MachinaryLocationCellLink> findByMachinaryLocationLinkIdAndLiveInd(String machinaryLocationLinkId, boolean b);

}
