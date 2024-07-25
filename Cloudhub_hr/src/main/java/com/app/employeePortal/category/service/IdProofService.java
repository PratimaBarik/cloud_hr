package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.IdProofTypeMapper;

public interface IdProofService {

	IdProofTypeMapper saveIdProofType(IdProofTypeMapper idProofTypeMapper);

	IdProofTypeMapper getIdProofTypeById(String idProofTypeId);

	IdProofTypeMapper updateIdProofType(String idProofTypeId, IdProofTypeMapper idProofTypeMapper);

	List<IdProofTypeMapper> getIdProofTypesByOrgId(String orgIdFromToken);

	boolean ipAddressExists(String url);

	List<IdProofTypeMapper> getIdProofTypeByUrl(String url);

	List<IdProofTypeMapper> getIdProofDetailsByNameByOrgLevel(String name, String orgId);

//    boolean checkIdProofNameInIdProofType(String idProofType);

	public void deleteIdProofTypeById(String idProofTypeId);

	HashMap getIdProofTypeCountByOrgId(String orgId);

	ByteArrayInputStream exportIdProofTypeListToExcel(List<IdProofTypeMapper> list);

	boolean checkIdProofNameInIdProofTypebyOrgLevel(String idProofType, String orgId);


	

}
