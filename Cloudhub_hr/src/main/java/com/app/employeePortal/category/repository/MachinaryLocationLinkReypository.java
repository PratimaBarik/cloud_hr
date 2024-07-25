package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.MachinaryLocationLink;

@Repository
public interface MachinaryLocationLinkReypository extends JpaRepository<MachinaryLocationLink, String> {

	List<MachinaryLocationLink> findByLocationIdAndLiveInd(String locationId, boolean b);

	MachinaryLocationLink findByMachinaryLocationLinkId(String machinaryLocationLinkId);
}
